package com.ynyes.huyue.controller.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 触屏端注册相关的控制器
 * 
 * @author Administrator
 */

@Controller
@RequestMapping(value = "/touch/regist")
public class TdTouchRegistController {

	@RequestMapping
	public String touchRegist(HttpServletRequest req, ModelMap map) {
		
		
		return "/touch/regist";
	}
	
}
