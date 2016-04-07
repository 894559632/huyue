package com.ynyes.huyue.controller.manager;

import java.util.Date;
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
@RequestMapping(value = "/Verwalter/login")
public class TdManageLoginController {

	@Autowired
	private TdManagerService managerService;

	@RequestMapping
	public String login(HttpServletRequest req, ModelMap map) {
		return "/site_mag/login";
	}

	@RequestMapping(value = "/check")
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

		if (password.equals(manager.getPassword()))
        {
            manager.setLastLoginIp(manager.getIp());
            manager.setLastLoginTime(manager.getLoginTime());
            
            String ip = req.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
            
            req.getSession().setMaxInactiveInterval(60 * 60 * 6); // 设置时长  Max
            manager.setIp(ip);
            manager.setLoginTime(new Date());
            
            managerService.save(manager);
            
            req.getSession().setAttribute("manager", username);
        }
		
		res.put("status", "y");
		return res;
	}
}
