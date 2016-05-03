package com.ynyes.huyue.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ynyes.huyue.entity.TdProductCategory;

/**
 * 公共方法
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月3日下午3:12:06
 */

@Service
@Transactional
public class TdCommonService {

	@Autowired
	private TdProductCategoryService tdProductCategoryService;

	public void findLevelOneAndLevelTwoCategories(HttpServletRequest req, ModelMap map) {
		// 查询出所有的一级分类
		List<TdProductCategory> level_one_cateogories = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();

		if (null != level_one_cateogories && level_one_cateogories.size() > 0) {
			map.addAttribute("level_one", level_one_cateogories);
			for (int i = 0; i < level_one_cateogories.size(); i++) {
				TdProductCategory level_one = level_one_cateogories.get(i);
				// 根据指定的一级分类查询其下的二级分类
				if (null != level_one) {
					List<TdProductCategory> level_two_cateogories = tdProductCategoryService
							.findByParentIdOrderBySortIdAsc(level_one.getId());
					map.addAttribute("level_two" + i, level_two_cateogories);
				}
			}
		}

	}
}
