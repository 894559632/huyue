package com.ynyes.huyue.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.util.SMSUtil;
import com.ynyes.huyue.util.VerifServlet;

@Controller
@RequestMapping(value = "/code")
public class TdCodeController {

	@RequestMapping
	public void verify(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		VerifServlet randomValidateCode = new VerifServlet();
		randomValidateCode.getRandcode(request, response);
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, Object> codeCheck(HttpServletRequest req, ModelMap map, String data) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String code = (String) req.getSession().getAttribute(VerifServlet.RANDOMCODEKEY);
		if (null != code && null != data && code.equalsIgnoreCase(data)) {

		} else {
			res.put("message", "请输入正确的图形验证码");
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/send")
	@ResponseBody
	public Map<String, Object> smsCode(String mobile, HttpServletResponse response, HttpServletRequest request) {
		Random random = new Random();

		String smscode = String.format("%04d", random.nextInt(9999));

		HttpSession session = request.getSession();

		session.setAttribute("SMSCODE", smscode);

		HashMap<String, Object> map = SMSUtil.send(mobile, "85628", new String[] { smscode, "5" });
		
		map.put("status", "0");
		map.put("code", smscode);
		return map;

	}
	
	@RequestMapping(value = "/change/password")
	@ResponseBody
	public Map<String, Object> codeChangePassword(String mobile, HttpServletResponse response, HttpServletRequest request) {
		Random random = new Random();

		String smscode = String.format("%04d", random.nextInt(9999));

		HttpSession session = request.getSession();

		session.setAttribute("PWDCODE", smscode);

		HashMap<String, Object> map = SMSUtil.send(mobile, "86846", new String[] { smscode, "5" });
		
		map.put("status", "0");
		map.put("code", smscode);
		return map;
	}
	
	@RequestMapping(value = "/reset/password")
	@ResponseBody
	public Map<String, Object> codeResetPassword(String mobile, HttpServletResponse response, HttpServletRequest request) {
		Random random = new Random();

		String smscode = String.format("%04d", random.nextInt(9999));

		HttpSession session = request.getSession();

		session.setAttribute("RESETCODE", smscode);

		HashMap<String, Object> map = SMSUtil.send(mobile, "91018", new String[] { smscode, "5" });
		
		map.put("status", "0");
		map.put("code", smscode);
		return map;
	}
}
