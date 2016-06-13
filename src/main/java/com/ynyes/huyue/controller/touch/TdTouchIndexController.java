package com.ynyes.huyue.controller.touch;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdAd;
import com.ynyes.huyue.entity.TdAdType;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdAdService;
import com.ynyes.huyue.service.TdAdTypeService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.util.MD5;

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

	@Autowired
	private TdUserService tdUserService;

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

		return "/touch/index";
	}

	/**
	 * 跳转到触屏版登录界面的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月25日下午9:43:51
	 */
	@RequestMapping(value = "/login")
	public String touchLogin(HttpServletRequest req, ModelMap map) {
		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/login";
	}

	@RequestMapping(value = "/login/confirm")
	@ResponseBody
	public Map<String, Object> touchLoginConfirm(HttpServletRequest req, String username, String password) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		if (null == username || null == password) {
			res.put("message", "获取登录信息失败");
			return res;
		}

		TdUser user = tdUserService.findByUsernameAndPassword(username, MD5.md5(password, 32));
		if (null == user) {
			res.put("message", "账号或密码错误");
			return res;
		}

		if (!(null != user.getIsEnable() && user.getIsEnable())) {
			res.put("message", "您的账号已经被冻结");
			return res;
		}

		// 设置session失效时间为1分钟
		req.getSession().setMaxInactiveInterval(3600);
		req.getSession().setAttribute("username", username);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest req) {
		req.getSession().removeAttribute("username");
		return "redirect:/touch/login";
	}

	@RequestMapping(value = "/reset/password")
	public String touchResetPassword() {
		return "/touch/reset_password";
	}

	@RequestMapping(value = "/resetpassword/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> touchResetPasswordCheck(String phone) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdUser user = tdUserService.findByUsername(phone);
		if (null == user) {
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/resetpassword/code/check")
	@ResponseBody
	public Map<String, Object> touchResetPasswordCodeCheck(HttpServletRequest req, String code) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String RESETCODE = (String) req.getSession().getAttribute("RESETCODE");

		if (!(null != code && code.equalsIgnoreCase(RESETCODE))) {
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/reset/password/save")
	public String touchResetPasswordSave(HttpServletRequest req, ModelMap map, String phone, String password) {
		TdUser user = tdUserService.findByUsername(phone);
		user.setPassword(MD5.md5(password, 32));
		tdUserService.save(user);
		return "redirect:/touch/login";
	}
}
