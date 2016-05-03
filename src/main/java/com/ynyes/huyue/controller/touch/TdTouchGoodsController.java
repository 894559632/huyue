package com.ynyes.huyue.controller.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdProductCategory;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdUserComment;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdProductCategoryService;
import com.ynyes.huyue.service.TdSettingService;
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
		TdGoods goods = tdGoodsService.findOne(id);
		map.addAttribute("goods", goods);

		// 查询该件商品的评论
		if (null != goods) {
			Page<TdUserComment> comment_page = tdUserCommentService.findByGoodsIdAndIsShowable(goods.getId(), 0, 20);
			map.addAttribute("comment_page", comment_page);
		}

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/goods_detail";
	}
}
