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
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdOrderGoods;
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
			if (null != address) {
				order.setShippingAddress(address.getDetail());
				order.setShippingName(address.getReceiveName());
				order.setShippingPhone(address.getReceivePhone());
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

}
