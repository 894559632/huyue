package com.ynyes.huyue.controller.touch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdCartGoods;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdCartGoodsService;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdUserService;

/**
 * 购物车相关的控制器
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月4日下午3:31:25
 */

@Controller
@RequestMapping(value = "/touch/cart")
public class TdTouchCartGoodsController {

	@Autowired
	private TdCartGoodsService tdCartGoodsService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCommonService tdCommonService;

	@RequestMapping
	public String touchCart(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		List<TdCartGoods> cart_list = tdCartGoodsService.findByUsername(username);
		if (null == cart_list || cart_list.size() == 0) {
			return "/touch/cart_goods_empty";
		}

		map.addAttribute("cart_list", cart_list);

		// 获取网站设置信息
		TdSetting setting = tdSettingService.findTopBy();
		map.addAttribute("setting", setting);
		return "/touch/cart_goods";
	}

	@RequestMapping(value = "change")
	@ResponseBody
	public Map<String, Object> touchCartChange(HttpServletRequest req, ModelMap map, Long id, Long operate) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdCartGoods cartGoods = tdCartGoodsService.findOne(id);
		if (null != cartGoods) {
			if (0L == operate.longValue()) {
				cartGoods.setQuantity(cartGoods.getQuantity() - 1L);
			}
			if (1L == operate.longValue()) {
				cartGoods.setQuantity(cartGoods.getQuantity() + 1L);
			}
			cartGoods.setTotal(cartGoods.getQuantity() * cartGoods.getPrice());
		}

		tdCartGoodsService.save(cartGoods);

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/pay")
	@ResponseBody
	public Map<String, Object> touchCartPay(HttpServletRequest req, ModelMap map, Long[] data) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		if (null == data || data.length <= 0) {
			res.put("message", "未能成功获取到购物车信息");
			return res;
		}

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}
		TdUser user = tdUserService.findByUsername(username);

		Long orderId = tdCommonService.createOrder(data, user);

		res.put("id", orderId);
		res.put("status", 0);
		return res;
	}
}
