package com.ynyes.huyue.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ynyes.huyue.entity.TdCartGoods;
import com.ynyes.huyue.entity.TdExchangeLog;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdLotteryLog;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdOrderGoods;
import com.ynyes.huyue.entity.TdPointLog;
import com.ynyes.huyue.entity.TdProductCategory;
import com.ynyes.huyue.entity.TdShippingAddress;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.entity.TdUserVisited;

/**
 * 公共方法
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月3日下午3:12:06
 */

@Service
@Transactional
public class TdCommonService {

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdUserVisitedService tdUserVisitedService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@Autowired
	private TdShippingAddressService tdShippingAddress;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdPointLogService tdPointLogService;

	@Autowired
	private TdExchangeLogService tdExchangeLogService;

	@Autowired
	private TdLotteryLogService tdLotteryLogService;

	public void findLevelOneAndLevelTwoCategories(HttpServletRequest req, ModelMap map) {
		// 查询出所有的一级分类
		List<TdProductCategory> level_one_cateogories = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();

		if (null != level_one_cateogories && level_one_cateogories.size() > 0) {
			map.addAttribute("level_one", level_one_cateogories);
			for (int i = 0; i < level_one_cateogories.size(); i++) {
				TdProductCategory level_one = level_one_cateogories.get(i);
				// 根据指定的一级分类查询其下的二级分类
				if (null != level_one) {
					List<TdProductCategory> level_two_cateogories = tdProductCategoryService
							.findByParentIdOrderBySortIdAsc(level_one.getId());
					map.addAttribute("level_two" + i, level_two_cateogories);
				}
			}
		}
	}

	/**
	 * 用户添加浏览记录的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月5日上午9:56:48
	 */
	public void addVisit(HttpServletRequest req, TdGoods goods) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);

		// 如果该件商品已经存在于浏览记录中，则修改浏览时间即可
		TdUserVisited visit = tdUserVisitedService.findByUsernameAndGoodsId(username, goods.getId());
		if (null != user) {
			if (null == visit) {
				visit = new TdUserVisited();
				visit.setUserId(user.getId());
				visit.setUsername(user.getUsername());
				visit.setGoodsCoverImageUri(goods.getCoverImageUri());
				visit.setGoodsId(goods.getId());
				visit.setGoodsSalePrice(goods.getSalePrice());
				visit.setGoodsTitle(goods.getTitle());
				visit.setVisitTime(new Date());
				tdUserVisitedService.save(visit);
				// 获取用户所有的浏览记录
				List<TdUserVisited> visit_list = tdUserVisitedService.findByUsernameOrderByVisitTimeDesc(username);
				if (null != visit_list && visit_list.size() > 20) {
					tdUserVisitedService.delete(visit_list.get(visit_list.size() - 1).getId());
				}
			} else {
				visit.setVisitTime(new Date());
				tdUserVisitedService.save(visit);
			}
		}
	}

	/**
	 * 生成消费订单的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午2:33:59
	 */
	public Long createOrder(Long[] data, TdUser user) {
		TdOrder order = new TdOrder();
		if (null != data && data.length > 0) {
			// 获取指定用户信息
			TdShippingAddress address = tdShippingAddress.findByUserIdAndIsDefaultTrue(user.getId());

			StringBuffer number = this.ramdomNumber(new StringBuffer("WXDD"));
			order.setOrderNumber(number.toString());
			order.setOrderTime(new Date());
			order.setTotalPrice(0.00);
			order.setTotalGoodsPrice(0.00);
			order.setStatusId(2L);
			order.setUsername(user.getUsername());
			if (null != address) {
				order.setShippingAddress(address.getDetail());
				order.setShippingName(address.getReceiveName());
				order.setShippingPhone(address.getReceivePhone());
				order.setShippingCity(address.getCityTitle());
			}

			List<TdOrderGoods> orderGoodsList = new ArrayList<>();

			for (Long id : data) {
				if (null != id) {
					TdCartGoods cart = tdCartGoodsService.findOne(id);
					if (null != cart) {
						TdOrderGoods orderGoods = new TdOrderGoods();
						orderGoods.setGoodsId(cart.getGoodsId());
						orderGoods.setGoodsTitle(cart.getGoodsTitle());
						orderGoods.setGoodsCoverImageUri(cart.getGoodsCoverImageUri());
						orderGoods.setPrice(cart.getPrice());
						orderGoods.setQuantity(cart.getQuantity());
						orderGoodsList.add(orderGoods);
						order.setTotalGoodsPrice(
								order.getTotalGoodsPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
						order.setTotalPrice(order.getTotalPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
						tdOrderGoodsService.save(orderGoods);

						tdCartGoodsService.delete(cart.getId());
					}
				}
			}
			order.setOrderGoodsList(orderGoodsList);
			order = tdOrderService.save(order);
		}
		return order.getId();
	}

	/**
	 * 生成随机数字符串的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月5日上午11:29:04
	 */
	public StringBuffer ramdomNumber(StringBuffer title) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		int i = random.nextInt(900) + 100;
		title.append(sdf.format(new Date()));
		title.append(i);
		return title;
	}

	/**
	 * 支付宝支付成功之后的处理方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:30:50
	 */
	public void alipayOperate(String out_trade_no, String total_fee) {
		// 获取指定的订单
		TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);
		if (null != order && null != order.getStatusId() && 2L == order.getStatusId().longValue()) {
			this.afterPay(order);
		}
	}

	/**
	 * 增加商品销量的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午2:02:55
	 */
	public void addSoldNumberAndDeleteInventory(List<TdOrderGoods> orderGoodsList) {
		if (null != orderGoodsList && orderGoodsList.size() > 0) {
			for (TdOrderGoods orderGoods : orderGoodsList) {
				if (null != orderGoods) {
					Long goodsId = orderGoods.getGoodsId();
					Long quantity = orderGoods.getQuantity();

					// 根据商品编号查找指定的商品
					TdGoods goods = tdGoodsService.findOne(goodsId);
					if (null != goods) {
						Long soldNumber = goods.getSoldNumber();
						if (null == soldNumber) {
							soldNumber = 0L;
						}
						Long leftNumber = goods.getLeftNumber();
						if (null == leftNumber) {
							leftNumber = 0L;
						}
						goods.setSoldNumber(soldNumber + quantity);
						goods.setLeftNumber(leftNumber - quantity);
						tdGoodsService.save(goods);
					}
				}
			}
		}
	}

	/**
	 * 生成积分订单的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午2:34:23
	 */
	public Long createPointOrder(TdGoods goods, TdUser user) {
		TdOrder order = new TdOrder();
		TdShippingAddress address = tdShippingAddress.findByUserIdAndIsDefaultTrue(user.getId());

		StringBuffer number = this.ramdomNumber(new StringBuffer("JFDH"));
		order.setOrderNumber(number.toString());
		order.setOrderTime(new Date());
		order.setTotalPrice(0.00);
		order.setTotalGoodsPrice(0.00);
		order.setStatusId(2L);
		order.setUsername(user.getUsername());
		if (null != address) {
			order.setShippingAddress(address.getDetail());
			order.setShippingName(address.getReceiveName());
			order.setShippingPhone(address.getReceivePhone());
			order.setShippingCity(address.getCityTitle());
		}

		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		TdOrderGoods orderGoods = new TdOrderGoods();
		orderGoods.setGoodsId(goods.getId());
		orderGoods.setGoodsTitle(goods.getTitle());
		orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
		orderGoods.setPrice(0.00);
		orderGoods.setQuantity(1L);
		orderGoods.setPointUse(goods.getPointLimited());
		orderGoodsList.add(orderGoods);
		order.setTotalGoodsPrice(order.getTotalGoodsPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
		order.setTotalPrice(order.getTotalPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
		tdOrderGoodsService.save(orderGoods);

		order.setOrderGoodsList(orderGoodsList);
		order.setPointUse(goods.getPointLimited());
		order = tdOrderService.save(order);

		return order.getId();
	}

	/**
	 * 生成抽奖订单的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午2:34:23
	 */
	public Long createAwardOrder(TdGoods goods, TdUser user) {
		TdOrder order = new TdOrder();
		TdShippingAddress address = tdShippingAddress.findByUserIdAndIsDefaultTrue(user.getId());

		StringBuffer number = this.ramdomNumber(new StringBuffer("CJDD"));
		order.setOrderNumber(number.toString());
		order.setOrderTime(new Date());
		order.setTotalPrice(0.00);
		order.setTotalGoodsPrice(0.00);
		order.setStatusId(1L);
		order.setUsername(user.getUsername());
		if (null != address) {
			order.setShippingAddress(address.getDetail());
			order.setShippingName(address.getReceiveName());
			order.setShippingPhone(address.getReceivePhone());
			order.setShippingCity(address.getCityTitle());
		}

		List<TdOrderGoods> orderGoodsList = new ArrayList<>();

		TdOrderGoods orderGoods = new TdOrderGoods();
		orderGoods.setGoodsId(goods.getId());
		orderGoods.setGoodsTitle(goods.getTitle());
		orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
		orderGoods.setPrice(0.00);
		orderGoods.setQuantity(1L);
		orderGoods.setPointUse(goods.getPointLimited());
		orderGoodsList.add(orderGoods);
		order.setTotalGoodsPrice(order.getTotalGoodsPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
		order.setTotalPrice(order.getTotalPrice() + (orderGoods.getPrice() * orderGoods.getQuantity()));
		tdOrderGoodsService.save(orderGoods);

		order.setOrderGoodsList(orderGoodsList);
		order.setPointUse(goods.getPointLimited());
		order = tdOrderService.save(order);

		return order.getId();
	}

	/**
	 * 用户付款之后的一系列操作
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年6月2日下午2:12:05
	 */
	public void afterPay(TdOrder order) {
		// 改变订单状态
		order.setStatusId(3L);
		order.setPayTime(new Date());
		order.setPoints(order.getTotalPrice());
		tdOrderService.save(order);

		if (!order.getOrderNumber().contains("JFDH")) {
			// 更改用户积分与抽奖次数
			TdUser user = tdUserService.findByUsername(order.getUsername());
			Double totalConsume = user.getTotalConsume();
			Double point = user.getPoint();
			Long lottery = user.getLottery();

			if (null == totalConsume) {
				totalConsume = 0.00;
			}
			if (null == point) {
				point = 0.00;
			}
			if (null == lottery) {
				lottery = 0L;
			}

			user.setTotalConsume(totalConsume + order.getTotalPrice());
			user.setPoint(point + order.getTotalPrice());
			if (order.getTotalPrice() > 100) {
				user.setLottery(lottery + 1L);
			}
			tdUserService.save(user);

			// 记录积分变更明细
			TdPointLog log = new TdPointLog();
			log.setUsername(order.getUsername());
			log.setChangeTime(new Date());
			log.setFee(order.getTotalPrice());
			log.setType(1L);
			log.setOrderNumber(order.getOrderNumber());
			log.setOrderId(order.getId());
			log.setChangedPoint(user.getPoint());

			tdPointLogService.save(log);

			// 改变商品销量
			this.addSoldNumberAndDeleteInventory(order.getOrderGoodsList());
		} else {
			TdUser user = tdUserService.findByUsername(order.getUsername());
			Double pointUse = order.getPointUse();
			Double point = user.getPoint();
			if (null == point) {
				point = 0.00;
			}

			// 扣减用户积分
			user.setPoint(point - pointUse);
			tdUserService.save(user);

			// 修改订单状态
			order.setStatusId(3L);
			order.setPayTime(new Date());
			tdOrderService.save(order);

			// 扣减库存数量并修改商品销量
			this.addSoldNumberAndDeleteInventory(order.getOrderGoodsList());

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
		}
	}

	public void createLotteryLog(TdGoods goods, TdUser user) {
		TdLotteryLog log = new TdLotteryLog();
		log.setUsername(user.getUsername());
		log.setUserId(user.getId());
		log.setAwardId(goods.getId());
		log.setAwardTitle(goods.getTitle());
		log.setLotteryTime(new Date());
		tdLotteryLogService.save(log);
	}
}
