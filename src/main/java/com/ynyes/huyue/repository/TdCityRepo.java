package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdCity;

/**
 * 城市仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月26日下午10:08:07
 */

public interface TdCityRepo extends PagingAndSortingRepository<TdCity, Long>, JpaSpecificationExecutor<TdCity> {

	/**
	 * 查找启用的城市
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午10:09:14
	 */
	List<TdCity> findByIsEnableTrueOrderBySortIdAsc();
}
