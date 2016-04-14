package com.ynyes.huyue.controller.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.huyue.entity.TdBrand;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdProduct;
import com.ynyes.huyue.entity.TdProductCategory;
import com.ynyes.huyue.service.TdBrandService;
import com.ynyes.huyue.service.TdGoodsService;
import com.ynyes.huyue.service.TdManagerLogService;
import com.ynyes.huyue.service.TdParameterCategoryService;
import com.ynyes.huyue.service.TdProductCategoryService;
import com.ynyes.huyue.service.TdProductService;

/**
 * 后台产品控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/product/category")
public class TdManagerProductCategoryController {

    @Autowired
    TdProductCategoryService tdProductCategoryService;

    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    @Autowired
    TdBrandService tdBrandService;
    
    @Autowired
    TdProductService tdProductService;

    @RequestMapping(value = "/list")
    public String categoryList(String __EVENTTARGET, String __EVENTARGUMENT,
            String __VIEWSTATE, Long[] listId, Integer[] listChkId, String keywords,Long categoryId,
            Long[] listSortId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
            case "btnSave":
                productCategoryBtnSave(listId, listSortId);

                break;

            case "btnDelete":
                productCategoryBtnDelete(listId, listChkId);

                break;
                
            case "changeCategory":
            	if (null != categoryId) {
                	productCategoryChange(categoryId,listId, listChkId);
                	tdManagerLogService.addLog("edit", "批量移动商品类别", req);
				}

            }
            
        }

        if (null != keywords && !keywords.isEmpty()) {
        	map.addAttribute("category_list", tdProductCategoryService.searchAll(keywords));        	
		}else {
			map.addAttribute("category_list", tdProductCategoryService.findAll());
		}
        

        // 参数注回
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("keywords", keywords);

        map.addAttribute("product_category_list", tdProductCategoryService.findAll());
        
        return "/site_mag/product_category_list";
    }

    @RequestMapping(value = "/edit")
    public String categoryEditDialog(Long id, Long sub, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        map.addAttribute("category_list", tdProductCategoryService.findAll());

        // 参数类型表
        map.addAttribute("param_category_list",
                tdParameterCategoryService.findAll());

        if (null != sub) // 添加子类
        {
            if (null != id) {
                map.addAttribute("fatherCat",
                        tdProductCategoryService.findOne(id));
            }
        } else {
            if (null != id) {
                map.addAttribute("cat", tdProductCategoryService.findOne(id));
            }
        }

        return "/site_mag/product_category_edit";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TdProductCategory cat, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null == cat.getId()) {
            tdManagerLogService.addLog("add", "用户修改产品分类", req);
        } else {
            tdManagerLogService.addLog("edit", "用户修改产品分类", req);
        }

        tdProductCategoryService.save(cat);

        return "redirect:/Verwalter/product/category/list";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();

        res.put("status", "n");

        if (null == param || param.isEmpty()) {
            res.put("info", "该字段不能为空");
            return res;
        }

        if (null == id) // 新增分类，查找所有
        {
            if (null != tdProductCategoryService.findByTitle(param)) {
                res.put("info", "已存在同名分类");
                return res;
            }
        } else // 修改，查找除当前ID的所有
        {
            if (null != tdProductCategoryService.findByTitleAndIdNot(param, id)) {
                res.put("info", "已存在同名分类");
                return res;
            }
        }

        res.put("status", "y");

        return res;
    }
    
    /**
     * 修改分类显示/隐藏状态
     * @author Max
     * 
     */
    @RequestMapping(value="/param/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeEnable(Long id,HttpServletRequest req)
    {
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 1);
    	
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
    	
        if(null != id)
        {
        	TdProductCategory category = tdProductCategoryService.findOne(id);
        	if(null != category)
        	{
        		if(category.getIsEnable())
        		{
        			category.setIsEnable(false);
        		}else{
        			category.setIsEnable(true);
        		}
        		tdProductCategoryService.save(category);
        		
        		res.put("code", 0);
        		res.put("message", "修改成功");
        		return res;
        	}
        }
    	res.put("message", "参数错误");
    	
    	return res;
    }

    private void productCategoryBtnSave(Long[] ids, Long[] sortIds) {
        if (null == ids || null == sortIds || ids.length < 1
                || sortIds.length < 1) {
            return;
        }

        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            TdProductCategory category = tdProductCategoryService.findOne(id);

            if (sortIds.length > i) {
                category.setSortId(sortIds[i]);
                tdProductCategoryService.save(category);
            }
        }
    }

    private void productCategoryBtnDelete(Long[] ids, Integer[] chkIds) {
        if (null == ids || null == chkIds || ids.length < 1
                || chkIds.length < 1) {
            return;
        }

        for (int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];

                tdProductCategoryService.delete(id);
            }
        }
    }
    
    private void productCategoryChange(Long categoryId, Long[] ids, Integer[] chkIds){
        if (null == ids || null == chkIds || ids.length < 1
                || chkIds.length < 1) {
            return;
        }
        
        // 如果移动到的类别为第三层类别则直接返回
        if (null == categoryId) {
			return;
		}

		TdProductCategory tdProductCategory = tdProductCategoryService.findOne(categoryId);
		if (null == tdProductCategory || null == tdProductCategory.getLayerCount()) {
				return;
		}
			
		if (tdProductCategory.getLayerCount().equals(3L)) {
				return;
		}					
        
        Long changeLayerCount = tdProductCategory.getLayerCount();
        
        Long checkLayerCount = 1L;
        List<TdProductCategory> chirldList = new ArrayList<>();
        List<TdProductCategory> secondChirldList = new ArrayList<>();
        // 判断改变后层级是否超过三层 超过三层则直接返回 类别不发生改变
        for (int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];
                
                if (id.equals(categoryId)) {
					return;
				}
                
                checkLayerCount = 1L;
                
                // 获取选中类别以下层级数
                chirldList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(id);
                
                if (null != chirldList && !chirldList.isEmpty()) {
					checkLayerCount = 2L;
					
					for(TdProductCategory tdProductCategory2 : chirldList){
						secondChirldList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(tdProductCategory2.getId());
						
						if (null != secondChirldList && !secondChirldList.isEmpty()) {
							checkLayerCount = 3L;
						}
					}
				}
                
                if (changeLayerCount + checkLayerCount > 3) {
					return;
				}
            }
        }
        
        // 已经改变过的类别
        List<Long> changedIdList = new ArrayList<>();
        for(int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];
                
                TdProductCategory tdProductCategory2 = tdProductCategoryService.findOne(id);
                if (null != tdProductCategory2) {
                	
                	// 如果父类别存在于已经修改过的类别列表中则跳出本次循环
                	if (null != tdProductCategory2.getParentId() && changedIdList.contains(tdProductCategory2.getParentId())) {
						continue;
					}
                	
                	// 更换父类
					tdProductCategory2.setParentId(categoryId);
					
					// 更换类别树
					TdProductCategory parent = tdProductCategoryService.findOne(categoryId);
					tdProductCategory2.setLayerCount(parent.getLayerCount() + 1L);
					tdProductCategory2.setParentTree(parent.getParentTree() + ",[" + tdProductCategory2.getId() + "]");
					
					// 处理商品类别树
					changeProductTree(tdProductCategory2);			
					
					// 处理品牌类别树
					changeBrandTree(tdProductCategory2);
					
					// 处理产品
					changeProduct(tdProductCategory2);
					
					// 添加到已改变类别列表
					changedIdList.add(tdProductCategory2.getId());
					tdProductCategoryService.save(tdProductCategory2);
					
					// 处理子类别
					chirldList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(tdProductCategory2.getId());
					
					//最多出现两层
					if (null != chirldList && !chirldList.isEmpty()) {
						for(TdProductCategory tdProductCategory3: chirldList){
							tdProductCategory3.setLayerCount(tdProductCategory2.getLayerCount() + 1L);
							tdProductCategory3.setParentTree(tdProductCategory2.getParentTree() + ",[" + tdProductCategory3.getId() + "]");
							
							tdProductCategoryService.save(tdProductCategory3);
							
							// 处理商品类别树
							changeProductTree(tdProductCategory3);
							
							// 处理品牌类别树
							changeBrandTree(tdProductCategory3);
							
							// 处理产品
							changeProduct(tdProductCategory3);
						}
					}
				}
            }
        }
        
    }
    
    /**
	 * @author lc
	 * @注释：修改所属类别的商品的类别树
	 */
    public void changeProductTree(TdProductCategory tdProductCategory){
    	if (null != tdProductCategory) {
    		List<TdGoods> tdGoodsList = tdGoodsService.findByCategoryId(tdProductCategory.getId());
    		if (null != tdGoodsList && !tdGoodsList.isEmpty()) {
        		for(TdGoods tdGoods: tdGoodsList){
        			tdGoods.setCategoryIdTree(tdProductCategory.getParentTree());
        			tdGoodsService.save(tdGoods, "tdamin");
        		}
			}
		}   	
    }    
    
    /**
	 * @author lc
	 * @注释：处理品牌类别树
	 */
    public void changeBrandTree(TdProductCategory tdProductCategory){
    	if (null != tdProductCategory) {
        	List<TdBrand> tdBrandsList = tdBrandService.findByProductCategoryId(tdProductCategory.getId());       	
        	if (null != tdBrandsList && !tdBrandsList.isEmpty()) {
				for(TdBrand tdBrand : tdBrandsList){
					tdBrand.setProductCategoryTree(tdProductCategory.getParentTree());
					tdBrandService.save(tdBrand);
				}
			}
		}

    }
    
    /**
	 * @author lc
	 * @注释：处理产品
	 */
    public void changeProduct(TdProductCategory tdProductCategory){
    	if (null != tdProductCategory) {
			List<TdProduct> tdProductsList = tdProductService.findByProductCategoryId(tdProductCategory.getId());
			if (null != tdProductsList && ! tdProductsList.isEmpty()) {
				for(TdProduct tdProduct : tdProductsList){
					tdProduct.setProductCategoryTree(tdProductCategory.getParentTree());
					tdProductService.save(tdProduct);
				}
			}
		}
    }
}
