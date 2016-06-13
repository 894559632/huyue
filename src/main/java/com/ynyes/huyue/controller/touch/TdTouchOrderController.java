package com.ynyes.huyue.controller.touch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.entity.TdDeliveryType;
import com.ynyes.huyue.entity.TdExchangeLog;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdOrderGoods;
import com.ynyes.huyue.entity.TdPayType;
import com.ynyes.huyue.entity.TdPointLog;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdShippingAddress;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdDeliveryTypeService;
import com.ynyes.huyue.service.TdExchangeLogService;
import com.ynyes.huyue.service.TdOrderService;
import com.ynyes.huyue.service.TdPayTypeService;
import com.ynyes.huyue.service.TdPointLogService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdShippingAddressService;
import com.ynyes.huyue.service.TdUserService;

/**
 * 触屏版与收获地址相关的控制器
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月23日上午11:44:39
 */
@Controller
@RequestMapping(value = "/touch/order")
public class TdTouchOrderController {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdPointLogService tdPointLogService;

	@Autowired
	private TdExchangeLogService tdExchangeLogService;

	@Autowired
	private TdDeliveryTypeService tdDeliveryTypeService;

	@RequestMapping(value = "/add/address")
	public String touchOrderAddAddress(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		// 获取所有的城市
		List<TdCity> city_list = tdCityService.findAll();
		map.addAttribute("city_list", city_list);

		map.addAttribute("orderId", orderId);
		map.addAttribute("username", username);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/order_add_address";
	}

	@RequestMapping(value = "/address/save")
	@ResponseBody
	public Map<String, Object> touchOrderAddressSave(HttpServletRequest req, ModelMap map, String name, String phone,
			Long cityId, String detail, Long orderId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("message", "请先登录");
			res.put("status", -2);
			return res;
		}

		// 设置订单的收获地址
		TdOrder order = tdOrderService.findOne(orderId);
		if (null == order) {
			res.put("message", "未查找到指定订单的信息");
			return res;
		}

		TdCity city = tdCityService.findOne(cityId);
		TdUser user = tdUserService.findByUsername(username);

		List<TdShippingAddress> address_list = tdShippingAddressService.findByUserId(user.getId());

		if (null != address_list && address_list.size() == 5) {
			res.put("message", "您已经拥有5个收货地址，不能够在添加了");
			return res;
		}

		if (null != user && null != city) {
			TdShippingAddress address = new TdShippingAddress();
			address.setCityId(city.getId());
			address.setCityTitle(city.getTitle());
			address.setDetail(detail);
			address.setReceiveName(name);
			address.setReceivePhone(phone);
			address.setUserId(user.getId());
			address.setUsername(username);

			if (null == address_list || address_list.size() == 0) {
				address.setIsDefault(true);
			}
			tdShippingAddressService.save(address);

			order.setShippingCity(city.getTitle());
			order.setShippingAddress(detail);
			order.setShippingName(name);
			order.setShippingPhone(phone);
			tdOrderService.save(order);
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/address/list")
	public String touchOrderAddressList(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		List<TdShippingAddress> address_list = tdShippingAddressService.findByUsername(username);
		map.addAttribute("address_list", address_list);

		map.addAttribute("orderId", orderId);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/order_address";
	}

	@RequestMapping(value = "/address/selected")
	@ResponseBody
	public Map<String, Object> touchOrderAddressSelected(HttpServletRequest req, ModelMap map, Long orderId, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("message", "请先登录");
			return res;
		}

		TdShippingAddress address = tdShippingAddressService.findOne(id);
		if (null == address) {
			res.put("message", "未查找到指定的地址信息");
			return res;
		}

		TdOrder order = tdOrderService.findOne(orderId);
		if (null == order) {
			res.put("message", "未查找到指定订单的信息");
			return res;
		}

		order.setShippingAddress(address.getDetail());
		order.setShippingCity(address.getCityTitle());
		order.setShippingName(address.getReceiveName());
		order.setShippingPhone(address.getReceivePhone());
		tdOrderService.save(order);

		res.put("status", 0);
		return res;
	}

	/**
	 * 跳转到去结算（选择支付方式）的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午2:50:32
	 */
	@RequestMapping(value = "/clear")
	public String touchOrderClear(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order) {
			map.addAttribute("price", order.getTotalPrice());
			map.addAttribute("orderId", order.getId());
		}

		List<TdPayType> pay_list = tdPayTypeService.findAll();
		map.addAttribute("pay_list", pay_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/order_clear";
	}

	@RequestMapping(value = "/point/clear")
	public String touchOrderPointClear(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/touch/login";
		}
		// 获取指定id的订单
		TdOrder order = tdOrderService.findOne(orderId);
		Double pointUse = order.getPointUse();
		Double point = user.getPoint();
		if (null == point) {
			point = 0.00;
		}

		if (pointUse > point) {
			return "/touch/pay_point_failure";
		} else {
			// 扣减用户积分
			user.setPoint(point - pointUse);
			tdUserService.save(user);

			// 修改订单状态
			order.setStatusId(3L);
			order.setPayTime(new Date());
			tdOrderService.save(order);

			// 扣减库存数量并修改商品销量
			tdCommonService.addSoldNumberAndDeleteInventory(order.getOrderGoodsList());

			// 记录积分变更明细
			TdPointLog log = new TdPointLog();
			log.setUsername(order.getUsername());
			log.setChangeTime(new Date());
			log.setFee(order.getPointUse() * (-1.00));
			log.setType(3L);
			log.setOrderNumber(order.getOrderNumber());
			log.setOrderId(order.getId());
			log.setChangedPoint(user.getPoint());

			tdPointLogService.save(log);

			// 获取兑换商品
			List<TdOrderGoods> goodsList = order.getOrderGoodsList();
			Long goodsId = goodsList.get(0).getGoodsId();
			String goodsTitle = goodsList.get(0).getGoodsTitle();
			String goodsCoverImageUri = goodsList.get(0).getGoodsCoverImageUri();

			// 生成兑换记录
			TdExchangeLog exchange = new TdExchangeLog();
			exchange.setUsername(user.getUsername());
			exchange.setUserId(user.getId());
			exchange.setGoodsId(goodsId);
			exchange.setGoodsTitle(goodsTitle);
			exchange.setGoodsCoverImageUri(goodsCoverImageUri);
			exchange.setOrderNumber(order.getOrderNumber());
			exchange.setQuantity(1L);
			exchange.setPointUsed(pointUse);
			exchange.setExchangeTime(new Date());
			tdExchangeLogService.save(exchange);

			map.addAttribute("point", pointUse);

			// 获取网站设置信息
			TdSetting setting = tdSettingService.findTopBy();
			map.addAttribute("setting", setting);
			return "/touch/pay_point_success";
		}
	}

	@RequestMapping(value = "/cancel")
	@ResponseBody
	public Map<String, Object> touchOrderCancel(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);
		if (null != order.getUsername() && order.getUsername().equalsIgnoreCase(username)) {
			order.setStatusId(7L);
			tdOrderService.save(order);
		} else {
			res.put("message", "未查询到指定订单的信息");
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/sign")
	@ResponseBody
	public Map<String, Object> touchOrderSign(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);
		if (null != order.getUsername() && order.getUsername().equalsIgnoreCase(username)) {
			order.setStatusId(5L);
			tdOrderService.save(order);
		} else {
			res.put("message", "未查询到指定订单的信息");
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/detail/{id}")
	public String touchOrderDetail(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		TdOrder order = tdOrderService.findOne(id);
		map.addAttribute("order", order);

		Long deliverTypeId = order.getDeliverTypeId();
		TdDeliveryType deliveryType = tdDeliveryTypeService.findOne(deliverTypeId);
		map.addAttribute("delivery", deliveryType);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/order_detail";
	}

	@RequestMapping(value = "/award/clear")
	public String touchOrderAwardClear(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != order.getStatusId() && order.getStatusId().longValue() == 1L) {
			order.setStatusId(3L);
			order.setPayTime(new Date());
			order.setCheckTime(new Date());
			order.setPayTypeId(0L);
			order.setPayTypeTitle("幸运抽奖");

			tdOrderService.save(order);
		}
		return "redirect:/touch/user/order/list";
	}
}
