package com.ynyes.huyue.controller.touch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.entity.TdExchangeLog;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdOrderGoods;
import com.ynyes.huyue.entity.TdPointLog;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdShippingAddress;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.entity.TdUserAdvice;
import com.ynyes.huyue.entity.TdUserCollect;
import com.ynyes.huyue.entity.TdUserComment;
import com.ynyes.huyue.entity.TdUserVisited;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdExchangeLogService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdOrderService;
import com.ynyes.huyue.service.TdPointLogService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdShippingAddressService;
import com.ynyes.huyue.service.TdUserAdviceService;
import com.ynyes.huyue.service.TdUserCollectService;
import com.ynyes.huyue.service.TdUserCommentService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.service.TdUserVisitedService;
import com.ynyes.huyue.util.ClientConstant;
import com.ynyes.huyue.util.MD5;
import com.ynyes.huyue.util.SiteMagConstant;

/**
 * 触屏版个人中心相关的控制器
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月25日下午9:40:22
 */

@Controller
@RequestMapping(value = "/touch/user")
public class TdTouchUserController {

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdUserCollectService tdUserCollectService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdUserVisitedService tdUserVisitedService;

	@Autowired
	private TdCityService tdCityService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdUserAdviceService tdUserAdviceService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdPointLogService tdPointLogService;

	@Autowired
	private TdExchangeLogService tdExchangeLogService;

	@Autowired
	private TdUserCommentService tdUserCommentService;

	@RequestMapping
	public String touchUser(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);

		// 获取用户待付款的订单数量
		Long unpay_number = tdOrderService.findCountByUsernameAndStatusId(username, 2L);
		map.addAttribute("unpay_number", unpay_number);

		// 获取用户带评价订单的数量
		Long uncomment_number = tdOrderService.findCountByUsernameAndStatusId(username, 3L);
		map.addAttribute("uncomment_number", uncomment_number);

		// 获取用户待收货订单的数量
		Long unsign_number = tdOrderService.findCountByUsernameAndStatusId(username, 4L);
		map.addAttribute("unsign_number", unsign_number);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_center";
	}

	@RequestMapping(value = "/info")
	public String touchUserInfo(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);

		// 获取所有的城市
		List<TdCity> city_list = tdCityService.findAll();
		map.addAttribute("city_list", city_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/user_info";
	}

	@RequestMapping(value = "/info/save", method = RequestMethod.POST)
	public String touchUserInfoSave(HttpServletRequest req, ModelMap map, String sex, Long cityId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/touch/login";
		}
		user.setSex(sex);
		TdCity city = tdCityService.findOne(cityId);
		if (null != city) {
			user.setCity(city.getTitle());
			user.setCityId(city.getId());
		}
		tdUserService.save(user);

		return "redirect:/touch/user";
	}

	@RequestMapping(value = "/collect")
	public String touchUserCollect(HttpServletRequest req, ModelMap map, String param) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		if (null == param) {
			param = "0-0";
		}

		// 拆分排序参数param
		String[] single = param.split("-");

		Page<TdUserCollect> user_collect_page = null;

		/*
		 * @param single[0] : 1. 默认排序；2. 销量排序； 3. 价格排序
		 * 
		 * @param single[1] : 1. 正序排序； 2. 倒序排序
		 */
		if (null != single && single.length == 2) {
			if ("0".equalsIgnoreCase(single[0])) {
				if ("0".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderByCollectTimeAsc(username, 0,
							ClientConstant.pageSize);
				} else if ("1".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderByCollectTimeDesc(username, 0,
							ClientConstant.pageSize);
				}

			} else if ("1".equalsIgnoreCase(single[0])) {
				if ("0".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderBySoldNumberAsc(username, 0,
							ClientConstant.pageSize);
				} else if ("1".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderBySoldNumberDesc(username, 0,
							ClientConstant.pageSize);
				}

			} else if ("2".equalsIgnoreCase(single[0])) {
				if ("0".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderByPriceAsc(username, 0,
							ClientConstant.pageSize);
				} else if ("1".equalsIgnoreCase(single[1])) {
					user_collect_page = tdUserCollectService.findByUsernameOrderByPriceDesc(username, 0,
							ClientConstant.pageSize);
				}
			}
		}

		map.addAttribute("user_collect_page", user_collect_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		map.addAttribute("param", param);
		return "/touch/user_collect";
	}

	/**
	 * 删除指定用户指定收藏的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：2016年5月13日上午11:14:28
	 */
	@RequestMapping(value = "/collect/delete")
	@ResponseBody
	public Map<String, Object> touchUserCollectDelete(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		if (null == id) {
			res.put("message", "未获取到收藏商品的信息");
			return res;
		}

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登陆");
			return res;
		}

		tdUserCollectService.delete(id);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/address")
	public String touchUserAddress(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "/touch/login";
		}

		List<TdShippingAddress> address_list = tdShippingAddressService.findByUsername(username);
		map.addAttribute("address_list", address_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/user_address";
	}

	@RequestMapping(value = "/visited")
	public String touchUserVisited(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		List<TdUserVisited> visit_list = tdUserVisitedService.findByUsernameOrderByVisitTimeDesc(username);
		map.addAttribute("visit_list", visit_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_visited";
	}

	/**
	 * 触屏版个人中心清空我的历史纪录
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月25日下午9:40:22
	 */
	@RequestMapping(value = "/visited/clear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> touchUserVisitedClear(HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登陆");
			return res;
		}

		tdUserVisitedService.deleteByUsername(username);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/add/address")
	public String touchUserAddAddress(HttpServletRequest req, ModelMap map, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		if (null != id) {
			TdShippingAddress address = tdShippingAddressService.findOne(id);
			map.addAttribute("address", address);
		}

		// 获取所有的城市信息
		List<TdCity> city_list = tdCityService.findAll();
		map.addAttribute("city_list", city_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_add_address";
	}

	@RequestMapping(value = "/address/save")
	@ResponseBody
	public Map<String, Object> touchUserAddressSave(HttpServletRequest req, ModelMap map, String name, String phone,
			Long cityId, String detail, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("message", "请先登录");
			res.put("status", -2);
			return res;
		}
		TdCity city = tdCityService.findOne(cityId);

		if (null == id || 0L == id.longValue()) {
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
			}
		} else {
			TdShippingAddress address = tdShippingAddressService.findOne(id);
			if (null != address) {
				address.setCityId(city.getId());
				address.setCityTitle(city.getTitle());
				address.setDetail(detail);
				address.setReceiveName(name);
				address.setReceivePhone(phone);
				tdShippingAddressService.save(address);
			}
		}
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/address/default")
	@ResponseBody
	public Map<String, Object> touchUserAddressDefault(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		if (null == id) {
			res.put("message", "未获取到指定收货地址的信息");
			return res;
		}

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			res.put("status", -2);
			res.put("message", "请先登陆");
			return res;
		}

		// 获取用户所有的收货地址
		TdShippingAddress address = tdShippingAddressService.findByUsernameAndIsDefaultTrue(username);
		address.setIsDefault(false);
		tdShippingAddressService.save(address);

		// 设置新的默认地址
		TdShippingAddress shippingAddress = tdShippingAddressService.findOne(id);
		shippingAddress.setIsDefault(true);
		tdShippingAddressService.save(shippingAddress);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/address/remove")
	@ResponseBody
	public Map<String, Object> touchUserAddressRemove(HttpServletRequest req, ModelMap map, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		if (null == id) {
			res.put("message", "未获取到指定的收获地址信息");
			return res;
		}

		tdShippingAddressService.delete(id);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/center/headImage", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, String username, HttpServletRequest req,
			ModelMap map) {
		if (null == username) {
			username = (String) req.getSession().getAttribute("username");
			if (null == username) {
				return "redirect:/touch/login";
			}
		}

		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/touch/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		try {
			byte[] bytes = Filedata.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = SiteMagConstant.imagePath + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
			user.setHeadImgUri("/images/" + fileName);
			tdUserService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/touch/user";
	}

	@RequestMapping(value = "/order/list")
	public String touchUserOrderList(HttpServletRequest req, ModelMap map, Long type) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		if (0L == type.longValue()) {
			Page<TdOrder> order_page = tdOrderService.findByUsernameOrderByOrderTimeDesc(username, 0, 5);
			map.addAttribute("order_page", order_page);
		} else {
			Page<TdOrder> order_page = tdOrderService.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, type, 0,
					5);
			map.addAttribute("order_page", order_page);
		}

		map.addAttribute("type", type);
		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_order";
	}

	/**
	 * 瀑布楼获取订单数据的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午4:42:38
	 */
	@RequestMapping(value = "/order/get")
	public String touchUserOrderGet(HttpServletRequest req, ModelMap map, Long type, Integer page) {
		String username = (String) req.getSession().getAttribute("username");
		if (0L == type.longValue()) {
			Page<TdOrder> order_page = tdOrderService.findByUsernameOrderByOrderTimeDesc(username, (page + 1), 5);
			map.addAttribute("order_page", order_page);
		} else {
			Page<TdOrder> order_page = tdOrderService.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, type,
					(page + 1), 5);
			map.addAttribute("order_page", order_page);
		}
		return "/touch/user_order_info";
	}

	/**
	 * 跳转到意见反馈的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午4:43:47
	 */
	@RequestMapping(value = "/advice")
	public String touchUserAdvice(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		map.addAttribute("username", username);
		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_advice";
	}

	@RequestMapping(value = "/advice/save")
	@ResponseBody
	public Map<String, Object> touchUserAdviceSave(HttpServletRequest req, ModelMap map, String content, String phone) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdUserAdvice advice = new TdUserAdvice();
		advice.setAdviceTime(new Date());
		advice.setContent(content);
		advice.setPhone(phone);
		advice.setIsReply(false);
		tdUserAdviceService.save(advice);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/point")
	public String touchUserPoint(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);

		// 获取所有的积分商品
		List<TdGoods> goods_list = tdGoodsService.findByIsOnSaleTrueAndIsPointGoodsTrueOrderBySortIdAsc();
		map.addAttribute("goods_list", goods_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_point";
	}

	@RequestMapping(value = "/point/detail")
	public String touchUserPointDetail(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		Page<TdPointLog> point_page = tdPointLogService.findByUsernameOrderByChangeTimeDesc(username, 0, 20);
		map.addAttribute("point_page", point_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/point_detail";
	}

	@RequestMapping(value = "/point/detail/get", method = RequestMethod.POST)
	public String touchUserPointDetailGet(HttpServletRequest req, ModelMap map, Integer page) {
		String username = (String) req.getSession().getAttribute("username");

		Page<TdPointLog> point_page = tdPointLogService.findByUsernameOrderByChangeTimeDesc(username, (page + 1), 20);
		map.addAttribute("point_page", point_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/point_detail_data";
	}

	@RequestMapping(value = "/point/exchange")
	public String touchUserPointExchange(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		// 获取该用户的兑换记录
		Page<TdExchangeLog> exchange_page = tdExchangeLogService.findByUsernameOrderByExchangeTimeDesc(username, 0, 20);
		map.addAttribute("exchange_page", exchange_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/exchange_detail";
	}

	@RequestMapping(value = "/point/exchange/detail/get", method = RequestMethod.POST)
	public String touchUserPointExchangeDetailGet(HttpServletRequest req, ModelMap map, Integer page) {
		String username = (String) req.getSession().getAttribute("username");

		// 获取该用户的兑换记录
		Page<TdExchangeLog> exchange_page = tdExchangeLogService.findByUsernameOrderByExchangeTimeDesc(username,
				(page + 1), 20);
		map.addAttribute("exchange_page", exchange_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/point_detail_data";
	}

	@RequestMapping(value = "/password")
	public String touchUserPassword(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		map.addAttribute("username", username);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_password";
	}

	@RequestMapping(value = "/password/change")
	@ResponseBody
	public Map<String, Object> touchUserPasswordChange(HttpServletRequest req, ModelMap map, String phone,
			String smscode, String password) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String PWDCODE = (String) req.getSession().getAttribute("PWDCODE");
		if (!(null != PWDCODE && PWDCODE.equalsIgnoreCase(smscode))) {
			res.put("message", "您输入的验证码不正确");
			return res;
		}

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);

		if (null == user) {
			res.put("message", "未查询到相关用户的信息");
			return res;
		}

		user.setPassword(MD5.md5(password, 32));
		tdUserService.save(user);

		res.put("status", 0);
		return res;
	}

	/**
	 * 查看订单评价或去评价的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年6月6日上午11:30:34
	 */
	@RequestMapping(value = "/order/comment")
	public String touchUserOrderComment(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		// 查询到指定的订单
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != order.getOrderGoodsList() && order.getOrderGoodsList().size() > 0) {
			map.addAttribute("orderGoodsList", order.getOrderGoodsList());
			map.addAttribute("orderNumber", order.getOrderNumber());
			for (TdOrderGoods orderGoods : order.getOrderGoodsList()) {
				if (null != orderGoods) {
					// 查询到指定的商品评价
					TdUserComment comment = tdUserCommentService.findByOrderNumberAndUsernameAndGoodsId(
							order.getOrderNumber(), username, orderGoods.getGoodsId());

					map.addAttribute("comment" + orderGoods.getGoodsId(), comment);
				}
			}
		}

		if (null == order.getCommentTime()) {
			map.addAttribute("readOnly", false);
		} else {
			map.addAttribute("readOnly", true);
		}

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/user_order_comment";
	}

	@RequestMapping(value = "/order/comment/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> touchUserOrderCommentSave(HttpServletRequest req, ModelMap map, String orderNumber,
			String[] comments, Long[] stars) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdUser user = tdUserService.findByUsername(username);

		// 获取指定的订单
		TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
		if (null != order && null != order.getOrderGoodsList() && order.getOrderGoodsList().size() > 0) {

			for (int i = 0; i < order.getOrderGoodsList().size(); i++) {
				TdOrderGoods orderGoods = order.getOrderGoodsList().get(i);
				TdUserComment comment = new TdUserComment();
				comment.setContent(comments[i]);
				comment.setStars(stars[i]);
				comment.setCommentTime(new Date());
				comment.setGoodsId(orderGoods.getGoodsId());
				comment.setGoodsTitle(orderGoods.getGoodsTitle());
				comment.setGoodsCoverImageUri(orderGoods.getGoodsCoverImageUri());
				comment.setOrderNumber(orderNumber);
				comment.setUsername(username);
				comment.setUserHeadUri(user.getHeadImgUri());
				comment.setStatusId(1L);
				tdUserCommentService.save(comment);
			}

			order.setStatusId(6L);
			order.setCommentTime(new Date());
			tdOrderService.save(order);
		}

		res.put("status", 0);
		return res;
	}
}
