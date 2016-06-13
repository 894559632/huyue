package com.ynyes.huyue.controller.touch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdLotteryLog;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdLotteryLogService;
import com.ynyes.huyue.service.TdUserService;

/**
 * 与幸运大转盘相关的控制器
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年6月2日上午9:29:46
 */
@Controller
@RequestMapping(value = "/touch/lottery")
public class TdTouchLotteryController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdLotteryLogService tdLotteryLogService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdCommonService tdCommonService;

	@RequestMapping
	public String touchLottery(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}

		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);

		Page<TdLotteryLog> lottery_page = tdLotteryLogService.findAll(0, 10);
		map.addAttribute("lottery_page", lottery_page);

		// JAVA生成1-100随机数
		int i = (int) (Math.random() * 100 + 1);
		if (i >= 1 && i <= 3) {
			map.addAttribute("num", 5);
		} else if (i >= 97 && i <= 99) {
			map.addAttribute("num", 7);
		} else {
			map.addAttribute("num", 1);
		}
		return "/touch/user_lottery";
	}

	@RequestMapping(value = "/check")
	@ResponseBody
	public Map<String, Object> touchUserLottery(HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdUser user = tdUserService.findByUsername(username);
		if (null != user) {
			Long lottery = user.getLottery();
			if (null == lottery) {
				lottery = 0L;
			}
			if (0L == lottery.longValue()) {
				res.put("message", "您的抽奖次数为0");
				return res;
			}

			user.setLottery(lottery - 1);
			tdUserService.save(user);
		} else {
			res.put("message", "未查找到登录用户的信息");
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/get/award", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> touchLotteryGetAward(HttpServletRequest req, ModelMap map, Long award) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			res.put("status", -2);
			res.put("message", "请先登录");
			return res;
		}

		TdUser user = tdUserService.findByUsername(username);

		String code = "";
		if (null != award && award.longValue() == 5L) {
			code = "HY1022";
		} else if (null != award && award.longValue() == 7L) {
			code = "HY1010";
		}
		TdGoods tdGoods = tdGoodsService.findByCode(code);
		tdCommonService.createLotteryLog(tdGoods, user);
		Long orderId = tdCommonService.createAwardOrder(tdGoods, user);

		res.put("orderId", orderId);
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/log")
	public String touchLotteryLog(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/touch/login";
		}
		List<TdLotteryLog> log_list = tdLotteryLogService.findByUsernameOrderByLotteryTimeDesc(username);
		map.addAttribute("log_list", log_list);
		return "/touch/lottery_log";
	}
}
