package com.ynyes.huyue.controller.touch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到触屏版各种规则页面的控制器
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年6月8日上午9:31:30
 */
@Controller
@RequestMapping(value = "/touch/message")
public class TdTouchMessageController {

	@RequestMapping(value = "/lottery")
	public String touchMessageLottery() {
		return "/touch/message_lottery";
	}
}
