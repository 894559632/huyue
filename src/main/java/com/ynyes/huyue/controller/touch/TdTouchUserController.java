package com.ynyes.huyue.controller.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdShippingAddress;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.entity.TdUserCollect;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdShippingAddressService;
import com.ynyes.huyue.service.TdUserCollectService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.util.ClientConstant;

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

	@RequestMapping
	public String touchUser(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);

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
		return "/touch/user_info";
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

		user_collect_page = tdUserCollectService.findByUsernameOrderByCollectTimeDesc(username, 0,
				ClientConstant.pageSize);
		map.addAttribute("user_collect_page", user_collect_page);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		map.addAttribute("param", param);
		return "/touch/user_collect";
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
}
