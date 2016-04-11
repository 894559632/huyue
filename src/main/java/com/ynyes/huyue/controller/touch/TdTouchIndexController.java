package com.ynyes.huyue.controller.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdAd;
import com.ynyes.huyue.entity.TdAdType;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdAdService;
import com.ynyes.huyue.service.TdAdTypeService;
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
			map.addAttribute("touch_middle_ad", touch_middle_ad);
		}

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		
		
		return "/front/index";
	}
}
