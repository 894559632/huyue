package com.ynyes.huyue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdExchangeLog;

/**
 * 积分兑换记录仓库类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月31日下午3:30:01
 */
public interface TdExchangeLogRepo
		extends PagingAndSortingRepository<TdExchangeLog, Long>, JpaSpecificationExecutor<TdExchangeLog> {

	/**
	 * 查找指定用户的兑换记录
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午3:32:06
	 */
	Page<TdExchangeLog> findByUsernameOrderByExchangeTimeDesc(String username, Pageable page);
}
