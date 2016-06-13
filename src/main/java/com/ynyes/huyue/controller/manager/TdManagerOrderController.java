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

import com.ynyes.huyue.entity.TdDeliveryType;
import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdPayType;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdCommonService;
import com.ynyes.huyue.service.TdDeliveryTypeService;
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

	@Autowired
	TdDeliveryTypeService tdDeliveryTypeService;

	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdCityService tdCityService;

	@RequestMapping(value = "/param/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber, String type, String data, String name, String address,
			String postal, String mobile, String expressNumber, Long deliverTypeId, ModelMap map,
			HttpServletRequest req) {

		Map<String, Object> res = new HashMap<String, Object>();

		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty()) {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);

			// 修改备注
			if (type.equalsIgnoreCase("editMark")) {
				order.setRemarkInfo(data);
			}
			// 修改商品总金额
			else if (type.equalsIgnoreCase("editTotalGoodsPrice")) {
				double goodsPrice = Double.parseDouble(data);
				order.setTotalGoodsPrice(goodsPrice);
				order.setTotalPrice(goodsPrice + order.getPayTypeFee() + order.getDeliverTypeFee());
			}
			// 修改配送费用
			else if (type.equalsIgnoreCase("editDeliveryPrice")) {
				double deliveryPrice = Double.parseDouble(data);
				order.setDeliverTypeFee(deliveryPrice);
				order.setTotalPrice(deliveryPrice + order.getPayTypeFee() + order.getTotalGoodsPrice());
			}

			// 修改支付手续费
			else if (type.equalsIgnoreCase("editPayPrice")) {
				double payPrice = Double.parseDouble(data);
				order.setPayTypeFee(payPrice);
				order.setTotalPrice(payPrice + order.getTotalGoodsPrice() + order.getDeliverTypeFee());
			}
			// 修改联系方式
			else if (type.equalsIgnoreCase("editContact")) {
				order.setShippingName(name);
				order.setShippingAddress(address);
				order.setShippingPhone(mobile);
				order.setPostalCode(postal);
			}
			// 确认订单
			else if (type.equalsIgnoreCase("orderConfirm")) {
				if (order.getStatusId().equals(1L)) {
					order.setStatusId(3L);
					order.setCheckTime(new Date());
					order.setPayTime(new Date());
					order.setPayTypeId(0L);
					order.setPayTypeTitle("幸运抽奖");
				}
			}
			// 确认付款
			else if (type.equalsIgnoreCase("orderPay")) {
				if (order.getStatusId().equals(2L)) {
					order.setStatusId(3L);
					order.setPayTime(new Date());

					// 支付成功后续操作
					tdCommonService.afterPay(order);
					order.setPayTypeId(0L);
					order.setPayTypeTitle("后台管理员" + username + "确认支付");
				}
			}

			// 确认发货
			else if (type.equalsIgnoreCase("orderDelivery")) {
				if (order.getStatusId().equals(3L)) {
					order.setDeliverTypeId(deliverTypeId);
					order.setExpressNumber(expressNumber);

					TdDeliveryType tdDeliveryType = tdDeliveryTypeService.findOne(deliverTypeId);
					if (null != tdDeliveryType && null != tdDeliveryType.getTitle()) {
						order.setDeliverTypeTitle(tdDeliveryType.getTitle());

						// 快递100 物流查询 返回链接
						String content = "";
						try {
							URL url = new URL("http://www.kuaidi100.com/applyurl?key=" + "d6d6c0bf0672af64" + "&com="
									+ tdDeliveryType.getCode() + "&nu=" + expressNumber);
							URLConnection con = url.openConnection();
							con.setAllowUserInteraction(false);
							InputStream urlStream = url.openStream();
							byte b[] = new byte[10000];
							int numRead = urlStream.read(b);
							content = new String(b, 0, numRead);
							while (numRead != -1) {
								numRead = urlStream.read(b);
								if (numRead != -1) {
									String newContent = new String(b, 0, numRead, "UTF-8");
									content += newContent;
								}
							}
							urlStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						order.setExpressUri(content);

					}
					order.setStatusId(4L);
					order.setSendTime(new Date());
				}
			}
			// 确认收货
			else if (type.equalsIgnoreCase("orderReceive")) {
				if (order.getStatusId().equals(4L)) {
					order.setStatusId(5L);
					order.setReceiveTime(new Date());
				}
			}
			// 确认完成
			else if (type.equalsIgnoreCase("orderFinish")) {
				if (order.getStatusId().equals(5L)) {
					order.setStatusId(6L);
					order.setFinishTime(new Date());
				}
			}
			// 确认取消
			else if (type.equalsIgnoreCase("orderCancel")) {
				if (order.getStatusId().equals(1L) || order.getStatusId().equals(2L) || order.getStatusId().equals(3L)) // zhangji
				{
					order.setStatusId(7L);
					order.setCancelTime(new Date());
				}
			}

			tdOrderService.save(order);
			tdManagerLogService.addLog("edit", "修改订单", req);

			res.put("code", 0);
			res.put("message", "修改成功!");
			return res;
		}

		res.put("message", "参数错误!");
		return res;
	}

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
				map.addAttribute("delivery_type_page", tdDeliveryTypeService.findAllOrderBySortIdAsc(page, size));
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

	@RequestMapping(value = "/setting/delivery/save", method = RequestMethod.POST)
	public String save(TdDeliveryType tdDeliveryType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdDeliveryType.getId()) {
			tdManagerLogService.addLog("add", "新增配送方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改配送方式", req);
		}

		tdDeliveryTypeService.save(tdDeliveryType);

		return "redirect:/Verwalter/order/setting/delivery/list";
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
			Long[] listId, Integer[] listChkId, ModelMap map, HttpServletRequest req,String city) {
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
		map.addAttribute("city", city);
		map.addAttribute("city_list", tdCityService.findAll());

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
		
		map.addAttribute("delivery_type_list", tdDeliveryTypeService.findByIsEnableTrue());

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
				} else if (type.equalsIgnoreCase("delivery")) {
					tdDeliveryTypeService.delete(id);
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
}
