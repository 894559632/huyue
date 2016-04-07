package com.ynyes.huyue.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdAdType;

public interface TdAdTypeRepo extends PagingAndSortingRepository<TdAdType, Long>, JpaSpecificationExecutor<TdAdType> {

	/**
	 * 根据广告位名称查找指定的广告位
	 * 
	 * @author DengXiao
	 */
	TdAdType findByTitle(String title);
}
