package com.ynyes.huyue.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdManager;
import com.ynyes.huyue.service.TdManagerService;

@Controller
@RequestMapping(value = "/Verwalter")
public class TdManageLoginController {

	@Autowired
	private TdManagerService managerService;

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest req, ModelMap map) {
		return "/site_mag/login";
	}

	@RequestMapping(value = "/login/check")
	@ResponseBody
	public Map<String, Object> loginCheck(HttpServletRequest req, ModelMap map, String username, String password) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "n");

		TdManager manager = managerService.findByUsernameAndPassword(username, password);
		if (null == manager) {
			res.put("message", "账号或密码错误");
			return res;
		}

		if (null != manager.getIsEnable() && manager.getIsEnable()) {
			res.put("message", "您的账户已经被冻结");
			return res;
		}

		res.put("status", "y");
		return res;
	}
}
