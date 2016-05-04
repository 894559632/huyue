package com.ynyes.huyue.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PC基础控制器
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月4日下午2:13:50
 */

@Controller
@RequestMapping(value = "/")
public class TdIndexController {

	@RequestMapping
	public String index() {
		return "redirect:/touch";
	}

}
