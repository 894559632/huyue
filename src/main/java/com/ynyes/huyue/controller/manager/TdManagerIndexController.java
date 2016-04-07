package com.ynyes.huyue.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.service.TdManagerService;

@Controller
@RequestMapping(value = "/Verwalter")
public class TdManagerIndexController {

	@Autowired
	private TdManagerService managerService;

	@RequestMapping
	public String index(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		
		
		
		return "/site_mag/index";
	}
}
