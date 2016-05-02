package com.ynyes.huyue.controller.manager;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdPayType;
import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdManagerLogService;
import com.ynyes.huyue.service.TdOrderService;
import com.ynyes.huyue.service.TdPayTypeService;
import com.ynyes.huyue.service.TdProductCategoryService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.service.TdUserService;
import com.ynyes.huyue.util.SiteMagConstant;

/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/order")
public class TdManagerOrderController {

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdGoodsService tdGoodsService;

	@Autowired
	TdPayTypeService tdPayTypeService;

	@Autowired
	TdOrderService tdOrderService;

	@Autowired
	TdUserService tdUserService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdSettingService tdSettingService;

	// 订单设置
	@RequestMapping(value = "/setting/{type}/list")
	public String setting(@PathVariable String type, Integer page, Integer size, String keywords, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Long[] listSortId,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(type, listId, listChkId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("delete", "删除支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("delete", "删除配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("delete", "删除门店", req);
				} else if (type.equalsIgnoreCase("codDistrict")) {
					tdManagerLogService.addLog("delete", "删除货到付款地区", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(type, listId, listSortId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("edit", "修改支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("edit", "修改配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("edit", "修改门店", req);
				} else if (type.equalsIgnoreCase("codDistrict")) {
					tdManagerLogService.addLog("edit", "修改货到付款地区", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null == keywords) {
					map.addAttribute("pay_type_page", tdPayTypeService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("pay_type_page", tdPayTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/pay_type_list";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				return "/site_mag/delivery_type_list";
			} else if (type.equalsIgnoreCase("diysite")) // 门店
			{
				return "/site_mag/diy_site_list";
			} else if (type.equalsIgnoreCase("codDistrict")) // 货到付款地区
			{
				return "/site_mag/cod_district_list";
			}
		}

		return "/site_mag/pay_type_list";
	}

	// 订单设置编辑
	@RequestMapping(value = "/setting/{type}/edit")
	public String edit(@PathVariable String type, Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null != id) {
					map.addAttribute("pay_type", tdPayTypeService.findOne(id));
				}

				return "/site_mag/pay_type_edit";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				if (null != id) {
				}

				return "/site_mag/delivery_type_edit";
			} else if (type.equalsIgnoreCase("diysite")) // 门店
			{
				if (null != id) {
				}

				return "/site_mag/diy_site_edit";
			} else if (type.equalsIgnoreCase("codDistrict")) // 货到付款地区
			{
				if (null != id) {
				}

				return "/site_mag/cod_district_edit";
			}
		}

		return "/site_mag/pay_type_edit";
	}

	@RequestMapping(value = "/edit")
	public String orderEdit(Long id, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);
		if (null != id) {
			TdOrder order = tdOrderService.findOne(id);
			map.addAttribute("order", order);
			Long payTypeId = order.getPayTypeId();
			TdPayType payType = tdPayTypeService.findOne(payTypeId);
			map.addAttribute("payType", payType);
		}

		// 配送方式

		return "/site_mag/order_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdOrder tdOrder, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);

		tdOrderService.save(tdOrder);

		tdManagerLogService.addLog("edit", "修改订单", req);

		return "redirect:/Verwalter/order/list/" + statusId;
	}

	// 订单列表
	@RequestMapping(value = "/list/{statusId}")
	public String goodsListDialog(String keywords, @PathVariable Long statusId, Integer page, Integer size,
			String start, String end, Long categoryId, String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
			Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnCancel")) {
				btnCancel(listId, listChkId);
				tdManagerLogService.addLog("cancel", "取消订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnConfirm")) {
				btnConfirm(listId, listChkId);
				tdManagerLogService.addLog("confirm", "确认订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除订单", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		Date startTime = null; // 起始时间
		Date endTime = null; // 截止时间
		if (null != start && !"".equals(start.trim())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				startTime = sdf.parse(start);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (null != end && !"".equals(end.trim())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				endTime = sdf.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (null == startTime) {
			if (null == endTime) {
				if (null != statusId) {
					if (statusId.equals(0L)) {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.searchByOrderNumber(keywords, page, size));
							}

						} // 关键字订单筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService.findAllOrderByIdDesc(page, size));
							}

						} // 所有订单END
					} // 所有状态END
					else {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.searchByOrderNumberAndStatusOrderByIdDesc(keywords, statusId, page, size));
							}

						} // 各状态关键字筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.findByStatusIdOrderByIdDesc(statusId, page, size));
							}

						} // 各状态所有订单
					}
				}
			} // 未选结尾时间END
			else {
				if (null != statusId) {
					if (statusId.equals(0L)) {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.searchByOrderNumberAndOrderTimeBefore(keywords, endTime, page, size));
							}

						} // 关键字+时间截止日期订单筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.findByOrderTimeBeforeOrderByIdDesc(endTime, page, size));
							}

						} // 所有订单+时间截止筛选END
					} // 所有状态+时间截止 END
					else {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.searchByOrderNumberAndStatusAndOrderTimeBeforeOrderByIdDesc(
												keywords, statusId, endTime, page, size));
							}

						} // 各状态+关键字+截止时间筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.findByStatusAndOrderTimeBeforeOrderByIdDesc(statusId, endTime, page, size));
							}

						} // 各状态所有订单+ 截止时间筛选END
					}
				}
			} // 截止时间END
		} // 未选开始时间筛选END
		else {
			if (null == endTime) {
				if (null != statusId) {
					if (statusId.equals(0L)) {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.searchByOrderNumberAndOrderTimeAfter(keywords, startTime, page, size));
							}

						} // 关键字订单+ 起始时间筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.findByOrderTimeAfterOrderByIdDesc(startTime, page, size));
							}

						} // 所有订单+起始时间END
					} // 所有状态END
					else {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.searchByOrderNumberAndStatusAndOrderTimeAfterOrderByIdDesc(
												keywords, statusId, startTime, page, size));
							}

						} // 各状态关键字+起始时间筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.findByStatusAndOrderTimeAfterOrderByIdDesc(statusId, startTime, page, size));
							}

						} // 各状态所有订单+起始时间
					}
				}
			} // 起始时间+未选结尾时间END
			else {
				if (null != statusId) {
					if (statusId.equals(0L)) {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService.searchByOrderNumberAndOrderTimeDetween(
										keywords, startTime, endTime, page, size));
							}

						} // 起始时间+关键字+时间截止日期订单筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page", tdOrderService
										.findByOrderTimeBetweenOrderByIdDesc(startTime, endTime, page, size));
							}

						} // 所有订单+起始时间+时间截止筛选END
					} // 所有状态+起始时间+时间截止 END
					else {
						if (null != keywords) {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.searchByOrderNumberAndStatusAndOrderTimeBetweenOrderByIdDesc(
												keywords, statusId, startTime, endTime, page, size));
							}

						} // 各状态+关键字+起始时间+截止时间筛选END
						else {
							if (null != categoryId) {
								map.addAttribute("order_page", tdOrderService.findByCategoryId(categoryId, page, size));
							} else {
								map.addAttribute("order_page",
										tdOrderService.findByStatusAndOrderTimeBetweenOrderByIdDesc(statusId, startTime,
												endTime, page, size));
							}

						} // 各状态所有订单+ 截止时间筛选END
					}
				}
			} // 截止时间END
		} // 起始时间+。。。END

		// 参数注回
		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("statusId", statusId);
		map.addAttribute("startime", startTime);
		map.addAttribute("endTime", endTime);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("category_list", tdProductCategoryService.findAll());

		return "/site_mag/order_list";
	}

	@RequestMapping(value = "/setting/pay/save", method = RequestMethod.POST)
	public String save(TdPayType tdPayType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdPayType.getId()) {
			tdManagerLogService.addLog("add", "新增支付方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改支付方式", req);
		}
		tdPayTypeService.save(tdPayType);

		return "redirect:/Verwalter/order/setting/pay/list";
	}

	@RequestMapping(value = "/setting/codDistrict/save", method = RequestMethod.POST)
	public String codDistrictsave(String province, String city, String disctrict, Long codDistrictId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == codDistrictId) {
			tdManagerLogService.addLog("add", "新增货到付款地区", req);
			// TdShippingAddress tdShippingAddress = new TdShippingAddress();
			// if (null != province) {
			// tdShippingAddress.setProvince(province);
			// }
			// if (null != city) {
			// tdShippingAddress.setCity(city);
			// }
			// if (null != disctrict) {
			// tdShippingAddress.setDisctrict(disctrict);
			// }
			// tdShippingAddress.setIsCod(true);
			// tdShippingAddressService.save(tdShippingAddress);
		} else {
			tdManagerLogService.addLog("edit", "修改货到付款地区", req);

			// TdShippingAddress tdShippingAddress =
			// tdShippingAddressService.findOne(codDistrictId);
			// if (null != province) {
			// tdShippingAddress.setProvince(province);
			// }
			// if (null != city) {
			// tdShippingAddress.setCity(city);
			// }
			// if (null != disctrict) {
			// tdShippingAddress.setDisctrict(disctrict);
			// }
			//
			// tdShippingAddressService.save(tdShippingAddress);
		}

		return "redirect:/Verwalter/order/setting/codDistrict/list";
	}

	// @RequestMapping(value = "/setting/delivery/save", method =
	// RequestMethod.POST)
	// public String save(TdDeliveryType tdDeliveryType, ModelMap map,
	// HttpServletRequest req) {
	// String username = (String) req.getSession().getAttribute("manager");
	// if (null == username) {
	// return "redirect:/Verwalter/login";
	// }
	//
	// if (null == tdDeliveryType.getId()) {
	// tdManagerLogService.addLog("add", "新增配送方式", req);
	// } else {
	// tdManagerLogService.addLog("edit", "修改配送方式", req);
	// }
	//
	// tdDeliveryTypeService.save(tdDeliveryType);
	//
	// return "redirect:/Verwalter/order/setting/delivery/list";
	// }

	// @RequestMapping(value = "/setting/diysite/save", method =
	// RequestMethod.POST)
	// public String save(TdDiySite tdDiySite, String[] hid_photo_name_show360,
	// ModelMap map, HttpServletRequest req) {
	// String username = (String) req.getSession().getAttribute("manager");
	// if (null == username) {
	// return "redirect:/Verwalter/login";
	// }
	//
	// String uris = parsePicUris(hid_photo_name_show360);
	//
	// tdDiySite.setShowPictures(uris);
	//
	// if (null == tdDiySite.getId()) {
	// tdManagerLogService.addLog("add", "新增门店", req);
	// } else {
	// tdManagerLogService.addLog("edit", "修改门店", req);
	// }
	//
	// tdDiySiteService.save(tdDiySite);
	//
	// return "redirect:/Verwalter/order/setting/diysite/list";
	// }

	@RequestMapping(value = "/dialog/contact")
	public String addressDialog(ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		return "/site_mag/dialog_contact";
	}

	@RequestMapping(value = "/dialog/delivery")
	public String sendDialog(String orderNumber, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderNumber && !orderNumber.isEmpty()) {
			map.addAttribute("order", tdOrderService.findByOrderNumber(orderNumber));
		}

		// map.addAttribute("delivery_type_list",
		// tdDeliveryTypeService.findByIsEnableTrue());

		return "/site_mag/dialog_delivery";
	}

	@RequestMapping(value = "/dialog/print")
	public String printDialog(String orderNumber, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderNumber && !orderNumber.isEmpty()) {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			map.addAttribute("order", order);
			map.addAttribute("now", new Date());
			map.addAttribute("manager", req.getSession().getAttribute("manager"));

			if (null != order) {
				map.addAttribute("user", tdUserService.findByUsernameAndIsEnableTrue(order.getUsername()));
			}
		}

		return "/site_mag/dialog_order_print";
	}

	// @RequestMapping(value = "/param/edit", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> paramEdit(String orderNumber, String type,
	// String data, String name, String address,
	// String postal, String mobile, String expressNumber, Long deliverTypeId,
	// ModelMap map,
	// HttpServletRequest req) {
	//
	// Map<String, Object> res = new HashMap<String, Object>();
	//
	// res.put("code", 1);
	//
	// String username = (String) req.getSession().getAttribute("manager");
	// if (null == username) {
	// res.put("message", "请重新登录");
	// return res;
	// }
	//
	// if (null != orderNumber && !orderNumber.isEmpty() && null != type &&
	// !type.isEmpty()) {
	// TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
	//
	// // 修改备注
	// if (type.equalsIgnoreCase("editMark")) {
	// order.setRemarkInfo(data);
	// }
	// // 修改商品总金额
	// else if (type.equalsIgnoreCase("editTotalGoodsPrice")) {
	// double goodsPrice = Double.parseDouble(data);
	// order.setTotalGoodsPrice(goodsPrice);
	// order.setTotalPrice(goodsPrice + order.getPayTypeFee() +
	// order.getDeliverTypeFee());
	// }
	// // 修改配送费用
	// else if (type.equalsIgnoreCase("editDeliveryPrice")) {
	// double deliveryPrice = Double.parseDouble(data);
	// order.setDeliverTypeFee(deliveryPrice);
	// order.setTotalPrice(deliveryPrice + order.getPayTypeFee() +
	// order.getTotalGoodsPrice());
	// }
	//
	// // 修改支付手续费
	// else if (type.equalsIgnoreCase("editPayPrice")) {
	// double payPrice = Double.parseDouble(data);
	// order.setPayTypeFee(payPrice);
	// order.setTotalPrice(payPrice + order.getTotalGoodsPrice() +
	// order.getDeliverTypeFee());
	// }
	// // 修改联系方式
	// else if (type.equalsIgnoreCase("editContact")) {
	// order.setShippingName(name);
	// order.setShippingAddress(address);
	// order.setShippingPhone(mobile);
	// order.setPostalCode(postal);
	// }
	// // 确认订单
	// else if (type.equalsIgnoreCase("orderConfirm")) {
	// if (order.getStatusId().equals(1L)) {
	// order.setStatusId(2L);
	// order.setCheckTime(new Date());
	// }
	// }
	// // 确认付款
	// else if (type.equalsIgnoreCase("orderPay")) {
	// if (order.getStatusId().equals(2L)) {
	// order.setStatusId(3L);
	// order.setPayTime(new Date());
	//
	// // 返现
	//
	// TdUser tdUser = tdUserService.findByUsername(order.getUsername());
	// //
	// // // 设置消费总额
	// // if (null != tdUser.getTotalSpendCash()) {
	// // tdUser.setTotalSpendCash(tdUser.getTotalSpendCash() +
	// // order.getTotalPrice());
	// // } else {
	// // tdUser.setTotalSpendCash(order.getTotalPrice());
	// // }
	// //
	// // if (null != tdUser && null != tdUser.getUpperUsername())
	// // {
	// //
	// // TdUser tdUser2 =
	// // tdUserService.findByUsername(tdUser.getUpperUsername());
	// // TdSetting tdSetting = tdSettingService.findTopBy();
	// //
	// // if (null != tdSetting && null !=
	// // tdSetting.getReturnRation() && null != tdUser2
	// // && null != tdUser2.getRoleId() &&
	// // tdUser2.getRoleId().equals(1L)) {
	// //
	// // TdUserCashReward tdUserCashReward = new
	// // TdUserCashReward();
	// //
	// // tdUserCashReward.setLowerUsername(order.getUsername());
	// // tdUserCashReward.setUsername(tdUser.getUpperUsername());
	// // tdUserCashReward.setRewardTime(new Date());
	// //
	// // // 返现金额分情况计算
	// // if (null != tdUser2.getReturnRation()) {
	// // tdUserCashReward.setCashReward(order.getTotalPrice() *
	// // tdUser2.getReturnRation());
	// // } else {
	// // tdUserCashReward.setCashReward(order.getTotalPrice() *
	// // tdSetting.getReturnRation());
	// // }
	// //
	// // if (null != tdUser.getTotalCashRewards()) {
	// // if (null != tdUser2.getReturnRation()) {
	// // tdUserCashReward.setTotalCashReward((long)
	// // (tdUser.getTotalCashRewards()
	// // + order.getTotalPrice() * tdUser2.getReturnRation()));
	// // } else {
	// // tdUserCashReward.setTotalCashReward((long)
	// // (tdUser.getTotalCashRewards()
	// // + order.getTotalPrice() * tdSetting.getReturnRation()));
	// // }
	// //
	// // } else {
	// // if (null != tdUser2.getReturnRation()) {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (order.getTotalPrice() *
	// // tdUser2.getReturnRation()));
	// // } else {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (order.getTotalPrice() *
	// // tdSetting.getReturnRation()));
	// // }
	// //
	// // }
	// // tdUserCashReward.setOrderNumber(order.getOrderNumber());
	// //
	// // if (null != tdUser.getBankTitle()) {
	// // tdUserCashReward.setBankName(tdUser.getBankTitle());
	// // }
	// // if (null != tdUser.getBankCardCode()) {
	// // tdUserCashReward.setBankCardNumber(tdUser.getBankCardCode());
	// // }
	// //
	// // tdUserCashReward.setOrderPrice(order.getTotalPrice());
	// // tdUserCashReward.setSortId(99L);
	// //
	// // tdUserCashRewardService.save(tdUserCashReward);
	// //
	// // Double totalReturn = 0.0;
	// // if (null != tdUser2.getReturnRation()) {
	// // totalReturn = order.getTotalPrice() *
	// // tdUser2.getReturnRation();
	// // } else {
	// // totalReturn = order.getTotalPrice() *
	// // tdSetting.getReturnRation();
	// // }
	// //
	// // if (totalReturn < 0) {
	// // totalReturn = 0.0;
	// // }
	// //
	// // TdUser tdUpuser =
	// // tdUserService.findByUsername(tdUser.getUpperUsername());
	// // if (null != tdUpuser.getTotalCashRewardsNumber()) {
	// //
	// tdUpuser.setTotalCashRewardsNumber(tdUpuser.getTotalCashRewardsNumber()
	// // + 1);
	// // } else {
	// // tdUpuser.setTotalCashRewardsNumber(1L);
	// // }
	// // if (null != tdUpuser.getTotalCashRewards()) {
	// // tdUpuser.setTotalCashRewards((long)
	// // (tdUpuser.getTotalCashRewards() + totalReturn));
	// // } else {
	// // tdUpuser.setTotalCashRewards((long) (totalReturn + 0L));
	// // }
	// // tdUserService.save(tdUpuser);
	// //
	// // if (null != tdUser.getTotalCashRewardsToUpuser()) {
	// // tdUser.setTotalCashRewardsToUpuser(
	// // (long) (totalReturn +
	// // tdUser.getTotalCashRewardsToUpuser()));
	// // } else {
	// // tdUser.setTotalCashRewardsToUpuser((long) (totalReturn +
	// // 0L));
	// // }
	// //
	// // tdUserService.save(tdUser);
	// // }
	// // }
	// //
	// // // 分销用户自己返利
	// // if (null != tdUser.getRoleId() &&
	// // tdUser.getRoleId().equals(1L)) {
	// // TdSetting tdSetting = tdSettingService.findTopBy();
	// // if (null != tdSetting && null !=
	// // tdSetting.getReturnRation()) {
	// // // 返现记录
	// // TdUserCashReward tdUserCashReward = new
	// // TdUserCashReward();
	// //
	// // tdUserCashReward.setLowerUsername(order.getUsername());
	// // tdUserCashReward.setUsername(tdUser.getUsername());
	// // tdUserCashReward.setRewardTime(new Date());
	// //
	// // // 返现金额分情况计算
	// // if (null != tdUser.getReturnRation()) {
	// // tdUserCashReward.setCashReward(order.getTotalPrice() *
	// // tdUser.getReturnRation());
	// // } else {
	// // tdUserCashReward.setCashReward(order.getTotalPrice() *
	// // tdSetting.getReturnRation());
	// // }
	// //
	// // if (null != tdUser.getTotalCashRewards()) {
	// // if (null != tdUser.getReturnRation()) {
	// // tdUserCashReward.setTotalCashReward((long)
	// // (tdUser.getTotalCashRewards()
	// // + order.getTotalPrice() * tdUser.getReturnRation()));
	// // } else {
	// // tdUserCashReward.setTotalCashReward((long)
	// // (tdUser.getTotalCashRewards()
	// // + order.getTotalPrice() * tdSetting.getReturnRation()));
	// // }
	// //
	// // } else {
	// // if (null != tdUser.getReturnRation()) {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (order.getTotalPrice() *
	// // tdUser.getReturnRation()));
	// // } else {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (order.getTotalPrice() *
	// // tdSetting.getReturnRation()));
	// // }
	// //
	// // }
	// // tdUserCashReward.setOrderNumber(order.getOrderNumber());
	// //
	// // if (null != tdUser.getBankTitle()) {
	// // tdUserCashReward.setBankName(tdUser.getBankTitle());
	// // }
	// // if (null != tdUser.getBankCardCode()) {
	// // tdUserCashReward.setBankCardNumber(tdUser.getBankCardCode());
	// // }
	// //
	// // tdUserCashReward.setOrderPrice(order.getTotalPrice());
	// // tdUserCashReward.setSortId(99L);
	// //
	// // tdUserCashRewardService.save(tdUserCashReward);
	// //
	// // Double totalReturn = 0.0;
	// // if (null != tdUser.getReturnRation()) {
	// // totalReturn = order.getTotalPrice() *
	// // tdUser.getReturnRation();
	// // } else {
	// // totalReturn = order.getTotalPrice() *
	// // tdSetting.getReturnRation();
	// // }
	// //
	// // if (totalReturn < 0) {
	// // totalReturn = 0.0;
	// // }
	// //
	// // // TdUser upperuser =
	// // //
	// // tdUserService.findByUsername(tdUser.getUpperUsername());
	// //
	// // // 返现总笔数
	// // if (null != tdUser.getTotalCashRewardsNumber()) {
	// // tdUser.setTotalCashRewardsNumber(tdUser.getTotalCashRewardsNumber()
	// // + 1);
	// // } else {
	// // tdUser.setTotalCashRewardsNumber(1L);
	// // }
	// //
	// // // 返现金额
	// // if (null != tdUser.getTotalCashRewards()) {
	// // tdUser.setTotalCashRewards((long)
	// // (tdUser.getTotalCashRewards() + totalReturn));
	// // } else {
	// // tdUser.setTotalCashRewards((long) (totalReturn + 0L));
	// // }
	// //
	// // tdUserService.save(tdUser);
	// //
	// // // 返现给上级用户总数
	// // // if (null != tdUser.getTotalCashRewardsToUpuser())
	// // // {
	// // // tdUser.setTotalCashRewardsToUpuser((long)
	// // // (tdUser.getTotalCashRewardsToUpuser() +
	// // // totalReturn));
	// // // }else {
	// // // tdUser.setTotalCashRewardsToUpuser((long)
	// // // (totalReturn + 0L));
	// // // }
	// // //
	// // // tdUserService.save(tdUser);
	// // }
	// // }
	// //
	// // // 直营会员给自己返利
	// // if (null != tdUser.getRoleId() &&
	// // tdUser.getRoleId().equals(3L)) {
	// // TdSetting tdSetting = tdSettingService.findTopBy();
	// // if (null != tdSetting && null !=
	// // tdSetting.getReturnRation()) {
	// // // 返现记录
	// // TdUserCashReward tdUserCashReward = new
	// // TdUserCashReward();
	// //
	// // tdUserCashReward.setLowerUsername(order.getUsername());
	// // tdUserCashReward.setUsername(tdUser.getUsername());
	// // tdUserCashReward.setRewardTime(new Date());
	// //
	// // // 返现金额分情况计算
	// // Double totalReturn = 0.0;
	// // Boolean isAllCategoryReturnRationNotNull = true;
	// // if (null != order.getOrderGoodsList()) {
	// // for (TdOrderGoods tdOrderGoods :
	// // order.getOrderGoodsList()) {
	// // TdGoods tdGoods =
	// // tdGoodsService.findOne(tdOrderGoods.getGoodsId());
	// // // 取第一级别类别的返利比例 后续可扩展至全类别
	// // TdProductCategory tdProductCategory = null; // 第一级别类别
	// // TdProductCategory tdProductCategorytemp = null;
	// // tdProductCategorytemp =
	// // tdProductCategoryService.findOne(tdGoods.getCategoryId());
	// // if (null == tdProductCategorytemp.getParentId()) {
	// // tdProductCategory = tdProductCategorytemp;
	// // } else {
	// // tdProductCategorytemp = tdProductCategoryService
	// // .findOne(tdProductCategorytemp.getParentId());
	// // if (null == tdProductCategorytemp.getParentId()) {
	// // tdProductCategory = tdProductCategorytemp;
	// // } else {
	// // tdProductCategorytemp = tdProductCategoryService
	// // .findOne(tdProductCategorytemp.getParentId());
	// // if (null == tdProductCategorytemp.getParentId()) {
	// // tdProductCategory = tdProductCategorytemp;
	// // }
	// // }
	// // }
	// //
	// // if (null != tdProductCategory.getReturnRation()) {
	// // if (null != tdGoods.getIsFeeNot()) {
	// // if (tdGoods.getIsFeeNot()) {
	// // totalReturn += tdGoods.getSalePrice()
	// // * tdProductCategory.getReturnRation();
	// // } else {
	// // totalReturn += (tdGoods.getSalePrice() +
	// // tdGoods.getPostage())
	// // * tdProductCategory.getReturnRation();
	// // }
	// // } else {
	// // totalReturn += tdGoods.getSalePrice() *
	// // tdProductCategory.getReturnRation();
	// // }
	// //
	// // } else {
	// // isAllCategoryReturnRationNotNull = false;
	// // }
	// // }
	// // }
	// //
	// // if (totalReturn < 0) {
	// // totalReturn = 0.0;
	// // }
	// //
	// // // 如果所有订单商品的类别返利比例都不为空 就用商品类别返利比例计算 返利总额
	// // // 否则则用平台统一返利比例
	// // if (isAllCategoryReturnRationNotNull) {
	// // tdUserCashReward.setCashReward(totalReturn);
	// // } else {
	// // tdUserCashReward.setCashReward(order.getTotalPrice() *
	// // tdSetting.getReturnRation());
	// // }
	// //
	// // if (null != tdUser.getDirectSaleCashRewards()) {
	// // if (isAllCategoryReturnRationNotNull) {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (totalReturn +
	// // tdUser.getDirectSaleCashRewards()));
	// // } else {
	// // tdUserCashReward.setTotalCashReward((long)
	// // (tdUser.getTotalCashRewards()
	// // + order.getTotalPrice() * tdSetting.getReturnRation()));
	// // }
	// //
	// // } else {
	// // if (isAllCategoryReturnRationNotNull) {
	// // tdUserCashReward.setTotalCashReward((long) (totalReturn +
	// // 0));
	// // } else {
	// // tdUserCashReward.setTotalCashReward(
	// // (long) (order.getTotalPrice() *
	// // tdSetting.getReturnRation()));
	// // }
	// //
	// // }
	// //
	// // tdUserCashReward.setOrderNumber(order.getOrderNumber());
	// //
	// // if (null != tdUser.getBankTitle()) {
	// // tdUserCashReward.setBankName(tdUser.getBankTitle());
	// // }
	// // if (null != tdUser.getBankCardCode()) {
	// // tdUserCashReward.setBankCardNumber(tdUser.getBankCardCode());
	// // }
	// //
	// // tdUserCashReward.setOrderPrice(order.getTotalPrice());
	// // tdUserCashReward.setSortId(99L);
	// //
	// // tdUserCashRewardService.save(tdUserCashReward);
	// //
	// // // 返现总笔数
	// // if (null != tdUser.getTotalCashRewardsNumber()) {
	// // tdUser.setTotalCashRewardsNumber(tdUser.getTotalCashRewardsNumber()
	// // + 1);
	// // } else {
	// // tdUser.setTotalCashRewardsNumber(1L);
	// // }
	// //
	// // // 返现金额
	// // if (null != tdUser.getDirectSaleCashRewards()) {
	// // if (isAllCategoryReturnRationNotNull) {
	// // tdUser.setDirectSaleCashRewards(tdUser.getDirectSaleCashRewards()
	// // + totalReturn);
	// // } else {
	// // tdUser.setDirectSaleCashRewards(tdUser.getDirectSaleCashRewards()
	// // + order.getTotalPrice() * tdSetting.getReturnRation());
	// // }
	// //
	// // } else {
	// // if (isAllCategoryReturnRationNotNull) {
	// // tdUser.setDirectSaleCashRewards(totalReturn);
	// // } else {
	// // tdUser.setDirectSaleCashRewards(
	// // order.getTotalPrice() * tdSetting.getReturnRation());
	// // }
	// // }
	// //
	// // tdUserService.save(tdUser);
	// // }
	// // }
	// //
	// // 虚拟货币扣除
	// if (null != order.getVirtualCurrencyUse()) {
	// if (null != tdUser.getRoleId()) {
	// if (tdUser.getRoleId().equals(1L) && null !=
	// tdUser.getTotalCashRewards()) {
	// if (tdUser.getTotalCashRewards() > order.getVirtualCurrencyUse()) {
	// tdUser.setTotalCashRewards(
	// (tdUser.getTotalCashRewards() - order.getVirtualCurrencyUse()));
	// } else {
	// tdUser.setTotalCashRewards(0.00);
	// }
	// } else if (tdUser.getRoleId().equals(2L) && null !=
	// tdUser.getVirtualCurrency()) {
	// if (tdUser.getVirtualCurrency() > order.getVirtualCurrencyUse()) {
	// tdUser.setVirtualCurrency(
	// tdUser.getVirtualCurrency() - order.getVirtualCurrencyUse());
	// } else {
	// tdUser.setVirtualCurrency(0.0);
	// }
	// } else if (tdUser.getRoleId().equals(3L) && null !=
	// tdUser.getDirectSaleCashRewards()) {
	// if (tdUser.getDirectSaleCashRewards() > order.getVirtualCurrencyUse()) {
	// tdUser.setDirectSaleCashRewards(
	// tdUser.getDirectSaleCashRewards() - order.getVirtualCurrencyUse());
	// } else {
	// tdUser.setDirectSaleCashRewards(0.0);
	// }
	// }
	// tdUserService.save(tdUser);
	// }
	// }
	// //
	// // // 购买商品积分奖励
	// // Long totalPoints = 0L; // 总用户返利
	// // // 返利总额
	// // List<TdOrderGoods> tdOrderGoodsList =
	// // order.getOrderGoodsList();
	// // if (null != tdOrderGoodsList) {
	// // for (TdOrderGoods tog : tdOrderGoodsList) {
	// // TdGoods tdGoods =
	// // tdGoodsService.findOne(tog.getGoodsId());
	// //
	// // if (null != tdGoods && null != tdGoods.getReturnPoints())
	// // {
	// // totalPoints += tdGoods.getReturnPoints() *
	// // tog.getQuantity();
	// //
	// // }
	// // }
	// //
	// // final Long totalPointsDely = totalPoints;
	// // final TdUser tdUserDely = tdUser;
	// // final TdOrder tdOrderDely = order;
	// // // 用户返利
	// // if (null != tdUser) {
	// // Timer timer = new Timer();
	// // timer.schedule(new TimerTask() {
	// // public void run() {
	// // // System.out.println("-------设定要指定任务--------");
	// // TdUserPoint userPoint = new TdUserPoint();
	// // TdOrder tdOrder1 =
	// // tdOrderService.findByOrderNumber(tdOrderDely.getOrderNumber());
	// //
	// // userPoint.setDetail("购买商品赠送积分");
	// // userPoint.setOrderNumber(tdOrderDely.getOrderNumber());
	// // userPoint.setPoint(totalPointsDely);
	// // userPoint.setPointTime(new Date());
	// // userPoint.setTotalPoint(tdUserDely.getTotalPoints() +
	// // totalPointsDely);
	// // userPoint.setUsername(tdUserDely.getUsername());
	// //
	// // userPoint = tdUserPointService.save(userPoint);
	// //
	// // tdUserDely.setTotalPoints(userPoint.getTotalPoint());
	// //
	// // // tdOrder1.setIsReturnPoints(true);
	// // tdOrderService.save(tdOrder1);
	// // tdUserService.save(tdUserDely);
	// // }
	// // }, 0);// 设定指定的时间time,
	// //
	// // }
	// // }
	// // if (null != order.getTypeId() &&
	// // order.getTypeId().equals(5L)) {
	// // Long pointUse = 0L;
	// //
	// // if (null !=
	// // order.getOrderGoodsList().get(0).getPointUse()) {
	// // pointUse =
	// // order.getOrderGoodsList().get(0).getPointUse();
	// // }
	// //
	// // // 积分扣除
	// // if (null != tdUser) {
	// // if (pointUse.compareTo(0L) >= 0 &&
	// // tdUser.getTotalPoints().compareTo(pointUse) >= 0) {
	// // TdUserPoint userPoint = new TdUserPoint();
	// // userPoint.setDetail("积分兑换商品");
	// // userPoint.setOrderNumber(order.getOrderNumber());
	// // userPoint.setPoint(0 - pointUse);
	// // userPoint.setPointTime(new Date());
	// // userPoint.setUsername(tdUser.getUsername());
	// // userPoint.setTotalPoint(tdUser.getTotalPoints() -
	// // pointUse);
	// // tdUserPointService.save(userPoint);
	// //
	// // tdUser.setTotalPoints(tdUser.getTotalPoints() -
	// // pointUse);
	// // tdUserService.save(tdUser);
	// // }
	// // }
	// // }
	// }
	// }
	// // 货到付款确认付款
	// else if (type.equalsIgnoreCase("orderPayOffline")) {
	// if (order.getStatusId().equals(2L)) {
	// order.setStatusId(5L);
	// order.setPayTime(new Date());
	//
	// // if (!order.getIsOnlinePay()) {
	// // if (order.getTotalPrice() == 0) {
	// // if (null != order.getTypeId() &&
	// // order.getTypeId().equals(5L)) {
	// //// pointOrderafterPaySuccess(order);
	// // } else {
	// // afterPaySuccess(order);
	// // }
	// // }
	// // }
	// }
	// }
	// // 确认发货
	// else if (type.equalsIgnoreCase("orderDelivery")) {
	// if (order.getStatusId().equals(3L)) {
	// order.setDeliverTypeId(deliverTypeId);
	// order.setExpressNumber(expressNumber);
	//
	// TdDeliveryType tdDeliveryType =
	// tdDeliveryTypeService.findOne(deliverTypeId);
	// if (null != tdDeliveryType && null != tdDeliveryType.getTitle()) {
	// order.setDeliverTypeTitle(tdDeliveryType.getTitle());
	//
	// // 快递100 物流查询 返回链接
	// String content = "";
	// try {
	// URL url = new URL("http://www.kuaidi100.com/applyurl?key=" +
	// "d6d6c0bf0672af64" + "&com="
	// + tdDeliveryType.getCode() + "&nu=" + expressNumber);
	// URLConnection con = url.openConnection();
	// con.setAllowUserInteraction(false);
	// InputStream urlStream = url.openStream();
	// byte b[] = new byte[10000];
	// int numRead = urlStream.read(b);
	// content = new String(b, 0, numRead);
	// while (numRead != -1) {
	// numRead = urlStream.read(b);
	// if (numRead != -1) {
	// // String newContent = new String(b, 0,
	// // numRead);
	// String newContent = new String(b, 0, numRead, "UTF-8");
	// content += newContent;
	// }
	// }
	// urlStream.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// // log.error("快递查询错误");
	// }
	//
	// order.setExpressUri(content);
	//
	// }
	// order.setStatusId(4L);
	// order.setSendTime(new Date());
	// }
	// }
	// // 确认收货
	// else if (type.equalsIgnoreCase("orderReceive")) {
	// if (order.getStatusId().equals(4L)) {
	// order.setStatusId(5L);
	// order.setReceiveTime(new Date());
	// if (!order.getIsOnlinePay()) {
	// order.setPayTime(new Date());
	// }
	// }
	//
	// tdReturnCashService.afterPaySuccess(order);
	// // if (!order.getIsOnlinePay()) {
	// // if (order.getTotalPrice() == 0) {
	// // if (null != order.getTypeId() &&
	// // order.getTypeId().equals(5L)) {
	// //// pointOrderafterPaySuccess(order);
	// // } else {
	// // afterPaySuccess(order);
	// // }
	// // // }
	// // }
	// }
	// // 确认完成
	// else if (type.equalsIgnoreCase("orderFinish")) {
	// if (order.getStatusId().equals(5L)) {
	// order.setStatusId(6L);
	// order.setFinishTime(new Date());
	//
	// tdUserService.addTotalSpend(order.getUsername(), order.getTotalPrice());
	// }
	// }
	// // 确认取消
	// else if (type.equalsIgnoreCase("orderCancel")) {
	// if (order.getStatusId().equals(1L) || order.getStatusId().equals(2L) ||
	// order.getStatusId().equals(3L)) // zhangji
	// {
	// order.setStatusId(7L);
	// order.setCancelTime(new Date());
	// }
	// }
	//
	// tdOrderService.save(order);
	// tdManagerLogService.addLog("edit", "修改订单", req);
	//
	// res.put("code", 0);
	// res.put("message", "修改成功!");
	// return res;
	// }
	//
	// res.put("message", "参数错误!");
	// return res;
	// }

	@ModelAttribute
	public void getModel(@RequestParam(value = "payTypeId", required = false) Long payTypeId,
			@RequestParam(value = "deliveryTypeId", required = false) Long deliveryTypeId,
			@RequestParam(value = "diySiteId", required = false) Long diySiteId, Model model) {
		if (null != payTypeId) {
			model.addAttribute("tdPayType", tdPayTypeService.findOne(payTypeId));
		}

		if (null != deliveryTypeId) {
		}

		if (null != diySiteId) {
		}
	}

	private void btnSave(String type, Long[] ids, Long[] sortIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			if (type.equalsIgnoreCase("pay")) {
				TdPayType e = tdPayTypeService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(sortIds[i]);
						tdPayTypeService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("delivery")) {

			} else if (type.equalsIgnoreCase("diysite")) {
			} else if (type.equalsIgnoreCase("codDistrict")) {
			}
		}
	}

	private void btnDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				if (type.equalsIgnoreCase("pay")) {
					tdPayTypeService.delete(id);
				}
			}
		}
	}

	private void btnConfirm(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)订单能进行确认，确认后状态为待发货(3L)
				if (tdOrder.getStatusId().equals(1L)) {
					tdOrder.setStatusId(3L);
					tdOrder.setCheckTime(new Date()); // 确认时间
					tdOrderService.save(tdOrder);
				}
			}
		}
	}

	private void btnCancel(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)、待付款(2L)订单能进行删除，确认后状态为已取消(7L)
				if (tdOrder.getStatusId().equals(1L) || tdOrder.getStatusId().equals(2L)) {
					tdOrder.setStatusId(7L);
					tdOrder.setCancelTime(new Date()); // 取消时间
					tdOrderService.save(tdOrder);
				}
			}
		}
	}

	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有已取消(7L)订单能进行删除
				if (tdOrder.getStatusId().equals(7L) || tdOrder.getStatusId().equals(8L)) {
					tdOrderService.delete(tdOrder);
				}
			}
		}
	}

	/**
	 * 图片地址字符串整理，多张图片用,隔开
	 * 
	 * @param params
	 * @return
	 */
	private String parsePicUris(String[] uris) {
		if (null == uris || 0 == uris.length) {
			return null;
		}

		String res = "";

		for (String item : uris) {
			String uri = item.substring(item.indexOf("|") + 1, item.indexOf("|", 2));

			if (null != uri) {
				res += uri;
				res += ",";
			}
		}

		return res;
	}
}
