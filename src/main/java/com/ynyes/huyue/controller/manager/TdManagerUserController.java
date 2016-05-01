package com.ynyes.huyue.controller.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdManagerLogService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.util.MD5;
import com.ynyes.huyue.util.SiteMagConstant;

/**
 * 后台用户管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/user")
public class TdManagerUserController {

	@Autowired
	TdUserService tdUserService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	private TdCityService tdCityService;

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(String param, Long id) {
		Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");

		if (null == param || param.isEmpty()) {
			res.put("info", "该字段不能为空");
			return res;
		}

		if (null == id) {
			if (null != tdUserService.findByUsername(param)) {
				res.put("info", "已存在同名用户");
				return res;
			}
		} else {
			if (null != tdUserService.findByUsernameAndIdNot(param, id)) {
				res.put("info", "已存在同名用户");
				return res;
			}
		}

		res.put("status", "y");

		return res;
	}

	@RequestMapping(value = "/list")
	public String setting(Integer page, Integer size, String keywords, Long roleId, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete("user", listId, listChkId);
				tdManagerLogService.addLog("delete", "删除用户", req);
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		if (null != keywords) {
			keywords = keywords.trim();
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("roleId", roleId);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		// 等级list
		// map.addAttribute("userType_list",
		// tdUserLevelService.findIsEnableTrue());

		Page<TdUser> userPage = null;

		if (null == roleId) {
			if (null == keywords || "".equalsIgnoreCase(keywords)) {
				userPage = tdUserService.findAllOrderBySortIdDesc(page, size);
			} else {
				userPage = tdUserService.findByUsernameContainingOrderBySortIdAsc(keywords, page, size);
			}
		}

		map.addAttribute("user_page", userPage);

		return "/site_mag/user_list";
	}

	@RequestMapping(value = "/edit")
	public String userEdit(Long id, Long roleId, String action, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("roleId", roleId);

		TdUser user = tdUserService.findOne(id);

		if (null != user) {
			map.addAttribute("user", user);
		}

		// 获取城市列表
		List<TdCity> city_list = tdCityService.findAll();
		map.addAttribute("city_list", city_list);
		return "/site_mag/user_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdUser tdUser, String oldPassword, String __VIEWSTATE, ModelMap map, String birthdate,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != birthdate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (StringUtils.isNotBlank(birthdate)) {
					Date brithday = sdf.parse(birthdate);
					tdUser.setBirthday(brithday);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null != tdUser.getId()) {
			if (StringUtils.isNotBlank(oldPassword)) {
				tdUser.setPassword(MD5.md5(oldPassword, 32));
			}

		}
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null == tdUser.getId()) {
			tdManagerLogService.addLog("add", "修改用户", req);
		} else {
			tdManagerLogService.addLog("edit", "修改用户", req);
		}

		TdUser user = tdUserService.findOne(tdUser.getId());

		// 获取用户的城市
		TdCity city = tdCityService.findOne(tdUser.getCityId());
		if (null != city) {
			tdUser.setCity(city.getTitle());
		}
		// 设置密码
		if (null == oldPassword || "".equals(oldPassword)) {
			if (null == tdUser.getId()) {
				tdUser.setPassword(MD5.md5("123456", 32));
			} else {
				tdUser.setPassword(user.getPassword());
			}
		}

		// 设置注册时间
		if (null == tdUser.getId()) {
			tdUser.setRegistDate(new Date());
		}

		// 设置用户名
		if (null != tdUser.getId()) {
			tdUser.setUsername(user.getUsername());
		}
		tdUser.setMobile(tdUser.getUsername());
		tdUserService.save(tdUser);

		return "redirect:/Verwalter/user/list/";
	}

	private void btnDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1 || null == type || "".equals(type)) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				if (type.equalsIgnoreCase("user")) // 用户
				{
					tdUserService.delete(id);
				}
			}
		}
	}
}
