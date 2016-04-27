package com.ynyes.huyue.controller.manager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.entity.TdServiceItem;
import com.ynyes.huyue.entity.TdSetting;
import com.ynyes.huyue.service.TdCityService;
import com.ynyes.huyue.service.TdManagerLogService;
import com.ynyes.huyue.service.TdServiceItemService;
import com.ynyes.huyue.service.TdSettingService;
import com.ynyes.huyue.util.SiteMagConstant;

/**
 * 后台广告管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/setting")
public class TdManagerSettingController {

	@Autowired
	TdSettingService tdSettingService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdServiceItemService tdServiceItemService;

	@Autowired
	TdCityService tdCityService;

	@RequestMapping
	public String setting(Long status, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("setting", tdSettingService.findTopBy());
		map.addAttribute("status", status);

		return "/site_mag/setting_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdSetting tdSetting, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdSetting.getId()) {
			tdManagerLogService.addLog("add", "用户修改系统设置", req);
		} else {
			tdManagerLogService.addLog("edit", "用户修改系统设置", req);
		}

		tdSettingService.save(tdSetting);

		return "redirect:/Verwalter/setting?status=1";
	}

	@RequestMapping(value = "/service/list")
	public String service(String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId,
			Integer[] listChkId, Long[] listSortId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(listId, listChkId);

				tdManagerLogService.addLog("edit", "删除服务", req);
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(listId, listSortId);

				tdManagerLogService.addLog("edit", "修改服务", req);
			}
		}

		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		map.addAttribute("service_item_list", tdServiceItemService.findAllOrderBySortIdAsc());

		return "/site_mag/service_item_list";
	}

	@RequestMapping(value = "/service/edit")
	public String edit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != id) {
			map.addAttribute("service_item", tdServiceItemService.findOne(id));
		}

		return "/site_mag/service_item_edit";
	}

	@RequestMapping(value = "/service/save", method = RequestMethod.POST)
	public String serviceItemEdit(TdServiceItem tdServiceItem, String __VIEWSTATE, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		tdServiceItemService.save(tdServiceItem);

		tdManagerLogService.addLog("edit", "修改商城服务", req);

		return "redirect:/Verwalter/setting/service/list";
	}

	@RequestMapping(value = "/city/list")
	public String settingCityList(Integer page, Integer size, String __EVENTTARGET, String __EVENTARGUMENT,
			String __VIEWSTATE, String action, Long[] listId, Integer[] listChkId, ModelMap map,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDeleteCity(listId, listChkId);
				tdManagerLogService.addLog("delete", "删除城市", req);
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
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("action", action);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("city_page", tdCityService.findAll(page, size));
		return "/site_mag/city_list";
	}
	
	@RequestMapping(value = "/city/edit")
	public String companyEdit(Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");

		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		TdCity city = tdCityService.findOne(id);

		if (null != id) {
			map.addAttribute("city", city);
		}

		return "/site_mag/city_edit";
	}
	
	@RequestMapping(value = "/city/save", method = RequestMethod.POST)
	public String companySave(TdCity tdCity, String __VIEWSTATE, ModelMap map, HttpServletRequest req, String limit) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		String type = null;
		if (null == tdCity.getId()) {
			type = "add";
		} else {
			type = "edit";
		}

		tdCity = tdCityService.save(tdCity);

		tdManagerLogService.addLog(type, "修改子公司", req);

		return "redirect:/Verwalter/setting/city/list";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "serviceItemId", required = false) Long serviceItemId, ModelMap map) {
		if (null != id) {
			map.addAttribute("tdSetting", tdSettingService.findOne(id));
		}

		if (null != serviceItemId) {
			TdServiceItem serviceItem = tdServiceItemService.findOne(serviceItemId);
			map.addAttribute("tdServiceItem", serviceItem);
		}
	}

	private void btnSave(Long[] ids, Long[] sortIds) {
		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			TdServiceItem e = tdServiceItemService.findOne(id);

			if (null != e) {
				if (sortIds.length > i) {
					e.setSortId(sortIds[i]);
					tdServiceItemService.save(e);
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

				tdServiceItemService.delete(id);
			}
		}
	}

	private void btnDeleteCity(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				tdCityService.delete(id);
			}
		}
	}
}
