package com.ynyes.huyue.controller.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdProductCategoryService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.util.ClientConstant;

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

	@RequestMapping
	public String touchGoods(HttpServletRequest req, ModelMap map, Long categoryId) {
		Page<TdGoods> goods_page = null;

		if (null == categoryId) {
			// 查询全部商品
			goods_page = tdGoodsService.findAll(0, ClientConstant.pageSize);
		} else {
			// 查询指定分类下的商品
			goods_page = tdGoodsService.findbycategoryId
		}

		map.addAttribute("goods_page", goods_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/goods_list";
	}
}
