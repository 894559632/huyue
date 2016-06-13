package com.ynyes.huyue.controller.touch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.ibm.icu.util.Calendar;
import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdOrderService;
import com.ynyes.huyue.service.TdSettingService;

import net.sf.json.JSONObject;

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
	@RequestMapping(value = "/alipay")
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
	
	@RequestMapping(value = "/openId")
	public String login(Long orderId, HttpServletRequest req, ModelMap map) throws UnsupportedEncodingException
	{
		if (null == orderId)
		{
			return null;
		}
		return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Configure.getAppid()
				+"&redirect_uri="  + URLEncoder.encode("http://www.huyuetools.com/touch/pay/wechat", "utf-8")
				+ "&response_type=code&scope=snsapi_userinfo&state="+ orderId +"#wechat_redirect";
	}
	
	@RequestMapping(value = "/wechat")
	public String wechat(String code, Long state, ModelMap map,HttpServletRequest req,HttpServletResponse response)
	{
		String openId = (String)req.getSession().getAttribute("openId");
		Map<String, String> accessToken = getAccessToken(code);
		
		openId = accessToken.get("openid");
		Long orderId = state;
		if (orderId == null)
		{
			return null;
		}
		TdOrder order = tdOrderService.findOne(orderId);
		map.addAttribute("order_number", order.getOrderNumber());
		map.addAttribute("total_price", order.getTotalPrice());

		Calendar calExpire = Calendar.getInstance();
		calExpire.setTime(order.getOrderTime());

		//统一支付接口
		String noncestr = RandomStringGenerator.getRandomStringByLength(32);
		ModelMap signMap = new ModelMap();
		signMap.addAttribute("appid", Configure.getAppid());
		signMap.addAttribute("attach", "订单支付");
		signMap.addAttribute("body", "支付订单" + order.getOrderNumber());
		signMap.addAttribute("mch_id", Configure.getMchid());
		signMap.addAttribute("nonce_str",noncestr);
		signMap.addAttribute("out_trade_no", order.getOrderNumber());
		signMap.addAttribute("total_fee", Math.round(order.getTotalPrice() * 100));
		signMap.addAttribute("spbill_create_ip", "116.55.233.157");
		signMap.addAttribute("notify_url", "http://www.huyuetools.com/touch/pay/wx_notify");
		signMap.addAttribute("trade_type", "JSAPI");
		signMap.addAttribute("openid", openId);

		String mysign = Signature.getSign(signMap);

		String content = "<xml>\n"
				+ "<appid>" + Configure.getAppid() + "</appid>\n"
				+ "<attach>订单支付</attach>\n"
				+ "<body>支付订单" + order.getOrderNumber() + "</body>\n"
				+ "<mch_id>" + Configure.getMchid() + "</mch_id>\n"
				+ "<nonce_str>" + noncestr + "</nonce_str>\n"
				+ "<notify_url>http://www.huyuetools.com/touch/pay/wx_notify</notify_url>\n"
				+ "<out_trade_no>" + order.getOrderNumber() + "</out_trade_no>\n"
				+ "<spbill_create_ip>116.55.233.157</spbill_create_ip>\n"
				+ "<total_fee>" + Math.round(order.getTotalPrice() * 100) + "</total_fee>\n"
				+ "<trade_type>JSAPI</trade_type>\n"
				+ "<sign>" + mysign + "</sign>\n"
				+ "<openid>" + openId + "</openid>\n"
				+ "</xml>\n";
		
		System.out.print("MDJ: xml=" + content + "\n");
		String return_code = null;
		String prepay_id = null;
		String result_code = null;
		String line = null;
		HttpsURLConnection urlCon = null;
		try
		{
			urlCon = (HttpsURLConnection) (new URL("https://api.mch.weixin.qq.com/pay/unifiedorder")).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",String.valueOf(content.getBytes().length));
			urlCon.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			urlCon.getOutputStream().write(content.getBytes("utf-8"));
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

			while ((line = in.readLine()) != null)
			{
				System.out.println(": rline: " + line);
				if (line.contains("<return_code>"))
				{
					return_code = line.replaceAll(
							"<xml><return_code><\\!\\[CDATA\\[", "")
							.replaceAll("\\]\\]></return_code>", "");
				} 
				else if (line.contains("<prepay_id>")) 
				{
					prepay_id = line.replaceAll("<prepay_id><\\!\\[CDATA\\[",
							"").replaceAll("\\]\\]></prepay_id>", "");
				}
				else if (line.contains("<result_code>"))
				{
					result_code = line.replaceAll(
							"<result_code><\\!\\[CDATA\\[", "").replaceAll(
									"\\]\\]></result_code>", "");
				}
			}

			System.out.println("MDJ: return_code: " + return_code + "\n");
			System.out.println("MDJ: prepay_id: " + prepay_id + "\n");
			System.out.println("MDJ: result_code: " + result_code + "\n");

			if ("SUCCESS".equalsIgnoreCase(return_code)
					&& "SUCCESS".equalsIgnoreCase(result_code)
					&& null != prepay_id)
			{
				noncestr = RandomStringGenerator.getRandomStringByLength(32);

				String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
				String packageString = "prepay_id=" + prepay_id;
				String signType = "MD5";
				ModelMap returnsignmap = new ModelMap();
				returnsignmap.addAttribute("appId", Configure.getAppid());
				returnsignmap.addAttribute("timeStamp", timeStamp);
				returnsignmap.addAttribute("nonceStr", noncestr);
				returnsignmap.addAttribute("package", packageString);
				returnsignmap.addAttribute("signType", signType);

				
				String returnsign = Signature.getSign(returnsignmap);
				content = "<xml>\n" + 
				"<appid>" + Configure.getAppid() + "</appid>\n" + 
				"<timeStamp>" + timeStamp + "</timeStamp>\n" +
				"<nonceStr>" + noncestr + "</nonceStr>\n" + 
				"<package>" + packageString + "</package>\n" + 
				"<signType>" + signType + "</signType>\n" + 
				"<signType>" + returnsign + "</signType>\n" + 
				"</xml>\n";

				System.out.print(": returnPayData xml=" + content);
				map.addAttribute("appId", Configure.getAppid());
				map.addAttribute("timeStamp", timeStamp);
				map.addAttribute("nonceStr", noncestr);
				map.addAttribute("package", packageString);
				map.addAttribute("signType", signType);
				map.addAttribute("paySign", returnsign);
				map.addAttribute("orderId", orderId);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "/touch/pay";
	}
	
	@RequestMapping(value = "/alipay/failure")
	public String touchPayAlipayFailure(){
		return "/touch/pay_failure";
	}
	
	public Map<String, String> getAccessToken(String code)
	{
		Map<String, String> res = new HashMap<String, String>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Configure.getAppid()+ "&secret=" + Configure.getAppSecret() + "&code=" +code + "&grant_type=authorization_code";
//		String accessToken = null;
//		String expiresIn = null;
//		String refresh_token = null;
		String openid = null;
		try
		{  
			URL urlGet = new URL(url);  

			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  

			http.setRequestMethod("GET"); // 必须是get方式请求  

			http.setRequestProperty("Content-Type",  

					"application/x-www-form-urlencoded");  

			http.setDoOutput(true);  

			http.setDoInput(true);  

			http.connect();  

			InputStream is = http.getInputStream();  

			int size = is.available();  

			byte[] jsonBytes = new byte[size];  

			is.read(jsonBytes);  

			String message = new String(jsonBytes, "UTF-8");  

			JSONObject demoJson = JSONObject.fromObject(message);  

			//            accessToken = demoJson.getString("access_token");  
			//            expiresIn = demoJson.getString("expires_in");  
			//            refresh_token = demoJson.getString("refresh_token");
			openid = demoJson.getString("openid");

			//            res.put("access_token", accessToken);
			//            res.put("expires_in", expiresIn);
			//            res.put("refresh_token", refresh_token);
			res.put("openid", openid);
			//           System.out.println("accessToken===="+accessToken);  
			//            System.out.println("expiresIn==="+expiresIn);  

			// System.out.println("====================获取token结束==============================");  

			is.close();  

		} catch (Exception e) {  

			e.printStackTrace(); 
		}  
		return res;
	}

}