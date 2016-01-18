package com.ynyes.lyz.controller.delivery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.lyz.entity.TdDeliveryInfo;
import com.ynyes.lyz.entity.TdGeoInfo;
import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.TdOwnMoneyRecord;
import com.ynyes.lyz.entity.TdUser;
import com.ynyes.lyz.service.TdDeliveryInfoService;
import com.ynyes.lyz.service.TdGeoInfoService;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdOwnMoneyRecordService;
import com.ynyes.lyz.service.TdUserService;
import com.ynyes.lyz.util.SiteMagConstant;

@Controller
@RequestMapping(value = "/delivery")
public class TdDeliveryIndexController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdOwnMoneyRecordService tdOwnMoneyRecordService;

	@Autowired
	private TdGeoInfoService tdGeoInfoService;

	@Autowired
	private TdDeliveryInfoService tdDeliveryInfoService;

	/**
	 * 获取配送列表
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param days
	 *            几天内
	 * @param type
	 *            类型
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping
	public String deliveryIndex(String start, String end, Integer days, Integer type, HttpServletRequest req,
			ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null == type) {
			type = 1;
		}

		map.addAttribute("type", type);

		if (null == start && null == end && null == days) {
			days = 3;
		}

		Date startDate = null, endDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		if (null != start) {
			try {
				cal.setTime(sdf.parse(start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			startDate = cal.getTime();
			map.addAttribute("startDate", startDate);
		}

		if (null != end) {
			try {
				cal.setTime(sdf.parse(end));
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			endDate = cal.getTime();
			map.addAttribute("endDate", endDate);
		}

		if (null == startDate && null == endDate) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);

			// 7天内
			if (days.equals(7)) {
				cal.add(Calendar.DATE, -7);
				startDate = cal.getTime();
			} else {
				cal.add(Calendar.DATE, -3);
				startDate = cal.getTime();
			}

			map.addAttribute("days", days);
		}

		// 查看本配送员所有
		List<String> orderNumberList = new ArrayList<String>();

		List<TdDeliveryInfo> dlList = tdDeliveryInfoService.findByOpUser(user.getOpUser());

		if (null != dlList && dlList.size() > 0) {
			for (TdDeliveryInfo info : dlList) {
				if (null != info.getOrderNumber()) {
					orderNumberList.add(info.getOrderNumber());
				}
			}
		}

		List<TdOrder> orderList = null;

		if (null != startDate && orderNumberList.size() > 0) {
			if (null != endDate) {
				if (type.equals(1)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeBetweenOrStatusIdAndOrderTimeBetween(5L, 6L,
							orderNumberList, startDate, endDate);
				} else if (type.equals(2)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeBetween(4L, orderNumberList, startDate,
							endDate);
				} else if (type.equals(3)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeBetween(3L, orderNumberList, startDate,
							endDate);
				}

				map.addAttribute("count_type_1",
						tdOrderService.countByStatusIdAndOrderTimeBetweenOrStatusIdAndOrderTimeBetween(5L, 6L,
								orderNumberList, startDate, endDate));
				map.addAttribute("count_type_2",
						tdOrderService.countByStatusIdAndOrderTimeBetween(4L, orderNumberList, startDate, endDate));
				map.addAttribute("count_type_3",
						tdOrderService.countByStatusIdAndOrderTimeBetween(3L, orderNumberList, startDate, endDate));
			} else {
				if (type.equals(1)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeAfterOrStatusIdAndOrderTimeAfter(5L, 6L,
							orderNumberList, startDate);
				} else if (type.equals(2)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeAfter(4L, startDate, orderNumberList);
				} else if (type.equals(3)) {
					orderList = tdOrderService.findByStatusIdAndOrderTimeAfter(3L, startDate, orderNumberList);
				}

				map.addAttribute("count_type_1",
						tdOrderService.countByStatusIdAndOrderTimeAfterOrStatusIdAndOrderTimeAfter(5L, 6L,
								orderNumberList, startDate));
				map.addAttribute("count_type_2",
						tdOrderService.countByStatusIdAndOrderTimeAfter(4L, startDate, orderNumberList));
				map.addAttribute("count_type_3",
						tdOrderService.countByStatusIdAndOrderTimeAfter(3L, startDate, orderNumberList));
			}
		}

		map.addAttribute("order_list", orderList);

		return "/client/delivery_list";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable Long id, HttpServletRequest req, ModelMap map, Long msg) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		TdUser user = tdUserService.findByUsernameAndIsEnableTrue(username);

		if (null == user) {
			return "redirect:/login";
		}

		if (null != id) {
			map.addAttribute("td_order", tdOrderService.findOne(id));
		}

		map.addAttribute("id", id);
		map.addAttribute("msg", msg);
		return "/client/delivery_detail";
	}

	@RequestMapping(value = "/submitDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submit(Long id, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == id) {
			res.put("message", "ID不能为空");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);

		if (null == order) {
			res.put("message", "订单不存在");
			return res;
		}

		if (null != order.getStatusId() && !order.getStatusId().equals(4L)) {
			res.put("message", "订单未出库");
			return res;
		}

		order.setStatusId(5L);
		order.setDeliveryTime(new Date());

		tdOrderService.save(order);

		res.put("code", 0);

		return res;
	}

	@RequestMapping(value = "/submitOwnMoney/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitMoney(@PathVariable Long id, Double payed, Double owned, HttpServletRequest req,
			ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		if (null == payed || null == owned) {
			res.put("message", "金额不能为空");
			return res;
		}

		TdOrder order = tdOrderService.findOne(id);

		if (null == order) {
			res.put("message", "订单不存在");
			return res;
		}

		List<TdOwnMoneyRecord> recList = tdOwnMoneyRecordService.findByOrderNumberIgnoreCase(order.getOrderNumber());

		if (null != recList && recList.size() > 0) {
			res.put("message", "该订单已申请了欠款");
			return res;
		}

		order.setActualPay(payed);
		order = tdOrderService.save(order);

		TdOwnMoneyRecord rec = new TdOwnMoneyRecord();
		rec.setCreateTime(new Date());
		rec.setOrderNumber(order.getOrderNumber());
		rec.setOwned(owned);
		rec.setPayed(payed);
		rec.setUsername(order.getUsername());
		rec.setIsEnable(false);
		rec.setIsPayed(false);
		rec.setSortId(99L);

		tdOwnMoneyRecordService.save(rec);

		res.put("code", 0);

		return res;
	}

	@RequestMapping(value = "/geo/submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitMoney(TdGeoInfo geoInfo, HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null == geoInfo.getLongitude() || null == geoInfo.getLatitude()) {
			res.put("message", "定位信息有误");
			return res;
		}

		geoInfo.setUsername(username);
		geoInfo.setTime(new Date());

		tdGeoInfoService.save(geoInfo);

		res.put("code", 0);

		return res;
	}

	@RequestMapping(value = "/img", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, HttpServletRequest req, String orderNumber, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		if (!".jpg".equals(ext) && !".png".equals(ext)) {
			return "redirect:/delivery/detail/" + id + "?msg=1";
		}

		try {
			byte[] bytes = Filedata.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = SiteMagConstant.imagePath + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();

			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			if (null != order) {
				order.setPhoto(uri);
				tdOrderService.save(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/delivery/detail/" + id + "?msg=0";
	}

}