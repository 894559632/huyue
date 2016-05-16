package com.ynyes.huyue.controller.touch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.util.MD5;
import com.ynyes.huyue.util.VerifServlet;

/**
 * 触屏端注册相关的控制器
 * 
 * @author Administrator
 */

@Controller
@RequestMapping(value = "/touch/regist")
public class TdTouchRegistController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCityService tdCityService;

	@RequestMapping
	public String touchRegist(HttpServletRequest req, ModelMap map) {
		List<TdCity> city_list = tdCityService.findAll();
		map.addAttribute("city_list", city_list);
		return "/touch/regist";
	}

	@RequestMapping(value = "/confirm")
	@ResponseBody
	public Map<String, Object> touchRegistConfirm(HttpServletRequest req, ModelMap map, Long cityId, String phone,
			String password, String realName, String imgSms, String smsCode) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		// 验证用户是否注册
		TdUser user = tdUserService.findByUsername(phone);
		if (null != user) {
			res.put("message", "您输入的手机号码已经注册");
			return res;
		}

		// 验证是否选择城市
		if (null == cityId || 0 == cityId.longValue()) {
			res.put("message", "请选择您所在的城市");
			return res;
		}

		// 是否填写真实姓名
		if (null == realName || "".equalsIgnoreCase(realName)) {
			res.put("message", "请输入您的真实姓名");
			return res;
		}

		// 获取图形验证码
		String server_imgSms = (String) req.getSession().getAttribute(VerifServlet.RANDOMCODEKEY);
		if (null == imgSms || !imgSms.equalsIgnoreCase(server_imgSms)) {
			res.put("message", "您输入的图形验证码不正确");
			return res;
		}
		// 获取短信验证码
		String server_phoneSms = (String) req.getSession().getAttribute("SMSCODE");
		if (null == smsCode || !smsCode.equalsIgnoreCase(server_phoneSms)) {
			res.put("message", "您输入的短信验证码不正确");
			return res;
		}

		// 查找制定的城市
		TdCity city = tdCityService.findOne(cityId);

		user = new TdUser();
		user.setUsername(phone);
		user.setPassword(MD5.md5(password, 32));
		user.setCity(city.getTitle());
		user.setCityId(city.getId());
		user.setIsEnable(true);
		user.setRegistDate(new Date());
		user.setLastLoginTime(new Date());
		user.setMobile(phone);
		user.setPoint(0.00);
		user.setHeadImgUri("/touch/images/head.png");
		user.setRealName(realName);
		user.setTotalConsume(0.00);
		user.setSortId(99.0);
		user = tdUserService.save(user);

		req.getSession().setAttribute("username", user.getUsername());

		res.put("status", 0);
		return res;
	}

}
