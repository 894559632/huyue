package com.ynyes.huyue.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdAd;

/**
 * TdAd 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdAdRepo extends
		PagingAndSortingRepository<TdAd, Long>,
		JpaSpecificationExecutor<TdAd> 
{
    Page<TdAd> findByIsEnableTrueOrderBySortIdAsc(Pageable page);
    List<TdAd> findByTypeIdAndEndTimeAfterOrderBySortIdAsc(Long typeId, Date time);
    
    Page<TdAd> findByTitleContainingOrSubtitleContaining(String keywords, String keywords1,Pageable page);
    
    Page<TdAd> findByTypeId(Long typeId, Pageable page);
    
    List<TdAd> findByTypeId(Long typeId);
}
