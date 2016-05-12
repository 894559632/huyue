package com.ynyes.huyue.controller.touch;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;
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
}
