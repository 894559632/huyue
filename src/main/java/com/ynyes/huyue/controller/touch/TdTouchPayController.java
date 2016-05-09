package com.ynyes.huyue.controller.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdOrderService;
import com.ynyes.huyue.service.TdSettingService;

/**
 * 支付相关控制器
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月5日下午4:45:26
 */

@Controller
@RequestMapping(value = "/touch/pay")
public class TdTouchPayController {

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdSettingService tdSettingService;

	@RequestMapping
	public String touchPay(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		map.addAttribute("order", order);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);

		return "/touch/order_pay";
	}
}
