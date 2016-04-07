package com.ynyes.huyue.controller.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/touch")
public class TdTouchIndexController {

	@RequestMapping
	public String index(HttpServletRequest req,ModelMap map){
		return "/front/index";
	}
}
