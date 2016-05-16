package com.ynyes.huyue.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdUserAdvice;

/**
 * 用户反馈实体类
 * 
 * @author 作者：DengXiao
 * @version 版本：下午4:36:28
 */
public interface TdUserAdviceRepo
		extends PagingAndSortingRepository<TdUserAdvice, Long>, JpaSpecificationExecutor<TdUserAdvice> {

}
