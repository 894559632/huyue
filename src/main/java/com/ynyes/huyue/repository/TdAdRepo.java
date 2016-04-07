package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdAd;

public interface TdAdRepo extends PagingAndSortingRepository<TdAd, Long>, JpaSpecificationExecutor<TdAd> {

	/**
	 * 根据广告位来查找广告
	 * 
	 * @author DengXiao
	 */
	List<TdAd> findByTypeIdOrderBySortIdAsc(Long typeId);
}
