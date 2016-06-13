package com.ynyes.huyue.controller.touch;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdCommonService;
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

	@Autowired
	private TdCommonService tdCommonService;

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

	/**
	 * 支付宝支付的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:03:25
	 */
	@RequestMapping(value = "alipay")
	public String touchAlipay(HttpServletRequest req, ModelMap map, Long orderId) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		// 获取订单信息
		TdOrder order = tdOrderService.findOne(orderId);
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", order.getOrderNumber());
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("notify_url", "http://www.huyuetools.com/touch/pay/alipay/return/async");
		sParaTemp.put("return_url", "http://www.huyuetools.com/touch/pay/alipay/return");
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		sParaTemp.put("show_url", "http://www.huyuetools.com/touch/pay/alipay/failure");
		sParaTemp.put("subject", "虎跃工具订单线上支付");
		sParaTemp.put("total_fee", order.getTotalPrice() + "");
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		map.put("code", sHtmlText);

		return "/touch/waiting_pay";
	}

	@RequestMapping(value = "/alipay/return")
	@SuppressWarnings("rawtypes")
	public String touchPayAlipayReturn(HttpServletRequest req, ModelMap map) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)

		String out_trade_no = null;
		String trade_status = null;
		String total_fee = null;

		try {
			// 商户订单号
			out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			total_fee = new String(req.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		verify_result = true;
		if (verify_result) {// 验证成功
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				tdCommonService.alipayOperate(out_trade_no, total_fee);
				map.addAttribute("money", total_fee);
				if (Double.parseDouble(total_fee) > 100) {
					map.addAttribute("lottery", 1);
				}
			}
		}
		return "/touch/pay_success";
	}

	@RequestMapping(value = "/alipay/return/async")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Map<String, Object> payAlipayReturnAsync(HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		String out_trade_no = null;
		String trade_status = null;
		String total_fee = null;
		try {
			// 商户订单号
			out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			total_fee = new String(req.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (AlipayNotify.verify(params)) {
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				tdCommonService.alipayOperate(out_trade_no, total_fee);
			}
			res.put("status", 0);
		}
		return res;
	}
	
	@RequestMapping(value = "/alipay/failure")
	public String touchPayAlipayFailure(){
		return "/touch/pay_failure";
	}
}