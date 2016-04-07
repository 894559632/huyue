package com.ynyes.huyue.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdManager;

public interface TdManagerRepo
		extends PagingAndSortingRepository<TdManager, Long>, JpaSpecificationExecutor<TdManager> {

	/**
	 * 根据账号密码查找指定的后台用户
	 * 
	 * @author DengXiao
	 */
	TdManager findByUsernameAndPassword(String username, String password);
	
}
