package com.ynyes.huyue.controller.touch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdCartGoods;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdProductCategory;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdUserCollect;
import com.ynyes.huyue.entity.TdUserComment;
import com.ynyes.huyue.service.TdCartGoodsService;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdProductCategoryService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdUserCollectService;
import com.ynyes.huyue.service.TdUserCommentService;

/**
 * 触屏版跟商品相关的控制器s
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月27日下午9:45:23
 */

@Controller
@RequestMapping(value = "/touch/goods")
public class TdTouchGoodsController {

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	@Autowired
	private TdUserCommentService tdUserCommentService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserCollectService tdUserCollectService;

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@RequestMapping
	public String touchGoods(HttpServletRequest req, ModelMap map, Long categoryId, Long sort) {
		List<TdGoods> goods_list = null;

		if (null == categoryId) {
			if (null != sort && 0L == sort.longValue()) {
				goods_list = tdGoodsService.findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc();
			} else {
				goods_list = tdGoodsService.findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc();
			}
		} else {
			if (null != sort && 0L == sort.longValue()) {
				goods_list = tdGoodsService
						.findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc(categoryId);
			} else {
				goods_list = tdGoodsService
						.findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc(categoryId);
			}
		}

		tdCommonService.findLevelOneAndLevelTwoCategories(req, map);

		map.addAttribute("goods_list", goods_list);

		// 获取指定分类的父类
		TdProductCategory level_two = tdProductCategoryService.findOne(categoryId);
		if (null != level_two) {
			map.addAttribute("level_two_id", level_two.getId());
			TdProductCategory level_one = tdProductCategoryService.findOne(level_two.getParentId());
			map.addAttribute("level_one_id", level_one.getId());
		}

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		map.addAttribute("sort", sort);
		return "/touch/goods_list";
	}

	@RequestMapping(value = "/detail/{id}")
	public String touchGoodsDetail(HttpServletRequest req, ModelMap map, @PathVariable Long id) {
		String username = (String) req.getSession().getAttribute("username");

		TdGoods goods = tdGoodsService.findOne(id);
		map.addAttribute("goods", goods);

		// 查询该件商品的评论
		if (null != goods) {
			Page<TdUserComment> comment_page = tdUserCommentService.findByGoodsIdAndIsShowable(goods.getId(), 0, 20);
			map.addAttribute("comment_page", comment_page);
		}

		// 判断该件商品是否被收藏
		TdUserCollect collect = tdUserCollectService.findByGoodsIdAndUsername(id, username);
		if (null == collect) {
			map.addAttribute("isCollect", false);
		} else {
			map.addAttribute("isCollect", true);
		}

		map.addAttribute("goodsId", id);

		// 添加浏览记录的方法
		tdCommonService.addVisit(req, goods);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/goods_detail";
	}

	@RequestMapping(value = "/collect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> touchGoodsCollect(HttpServletRequest req, Long goodsId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登陆");
			return res;
		}

		if (null == goodsId) {
			res.put("message", "未能成功获取到商品信息");
			return res;
		}

		TdUserCollect collect = tdUserCollectService.findByGoodsIdAndUsername(goodsId, username);
		if (null == collect) {
			TdGoods goods = tdGoodsService.findOne(goodsId);
			if (null != goods) {
				collect = new TdUserCollect();
				collect.setCollectTime(new Date());
				collect.setGoodsCoverImageUri(goods.getCoverImageUri());
				collect.setGoodsId(goods.getId());
				collect.setGoodsTitle(goods.getTitle());
				collect.setPrice(goods.getSalePrice());
				collect.setSoldNumber(goods.getSoldNumber());
				collect.setUsername(username);
				tdUserCollectService.save(collect);
			}
		} else {
			tdUserCollectService.delete(collect.getId());
		}

		res.put("status", 0);
		return res;
	}

	/**
	 * 将指定商品加入购物车的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月4日下午2:56:36
	 */
	@RequestMapping(value = "/add/cart")
	@ResponseBody
	public Map<String, Object> touchGoodsAddCart(HttpServletRequest req, ModelMap map, Long goodsId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 判断参数是否合法
		if (null == goodsId) {
			res.put("message", "未能成功获取到商品信息");
			return res;
		}

		// 获取当前登录的用户
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdCartGoods cart = tdCartGoodsService.findByGoodsIdAndUsername(goodsId, username);
		TdGoods goods = tdGoodsService.findOne(goodsId);
		if (null == cart) {
			if (null != goods) {
				cart = new TdCartGoods();
				cart.setUsername(username);
				cart.setGoodsId(goods.getId());
				cart.setGoodsTitle(goods.getTitle());
				cart.setQuantity(1L);
				cart.setPrice(goods.getSalePrice());
			}
		} else {
			cart.setPrice(goods.getSalePrice());
			cart.setQuantity(cart.getQuantity() + 1L);
		}
		cart.setTotal(cart.getQuantity() * cart.getPrice());
		tdCartGoodsService.save(cart);

		res.put("status", 0);
		return res;
	}

}
