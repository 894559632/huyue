package com.ynyes.huyue.controller.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdAd;
import com.ynyes.huyue.entity.TdAdType;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdAdService;
import com.ynyes.huyue.service.TdAdTypeService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdSettingService;

@Controller
@RequestMapping(value = "/touch")
public class TdTouchIndexController {

	@Autowired
	private TdAdTypeService tdAdTypeService;

	@Autowired
	private TdAdService tdAdService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@RequestMapping
	public String index(HttpServletRequest req, ModelMap map) {

		// 获取触屏顶部广告
		TdAdType touch_top_ad_type = tdAdTypeService.findByTitle("触屏首页顶部广告位");
		if (null != touch_top_ad_type) {
			List<TdAd> touch_top_ad = tdAdService.findByTypeIdAndEndtimeAfter(touch_top_ad_type.getId());
			map.addAttribute("touch_top_ad", touch_top_ad);
		}

		// 获取触屏页面中部广告
		TdAdType touch_middle_ad_type = tdAdTypeService.findByTitle("触屏首页中部广告位");
		if (null != touch_middle_ad_type) {
			List<TdAd> touch_middle_ad = tdAdService.findByTypeIdAndEndtimeAfter(touch_middle_ad_type.getId());
			if (null != touch_middle_ad && touch_middle_ad.size() > 0) {
				map.addAttribute("touch_middle_ad", touch_middle_ad.get(0));
			}
		}

		// 获取首页推荐商品
		Page<TdGoods> indexRecommend_goods_page = tdGoodsService
				.findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySortIdAsc(0, 3);
		map.addAttribute("indexRecommend_goods_page", indexRecommend_goods_page);

		// 获取首页推荐积分商品
		Page<TdGoods> indexRecommend_point_page = tdGoodsService
				.findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsTrueOrderBySortIdAsc(0, 10);
		map.addAttribute("indexRecommend_point_page", indexRecommend_point_page);

		// 获取首页热销商品
		Page<TdGoods> hot_goods_page = tdGoodsService
				.findByisHotTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySoldNumberDesc(0, 10);
		map.addAttribute("hot_goods_page", hot_goods_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/front/index";
	}
}
