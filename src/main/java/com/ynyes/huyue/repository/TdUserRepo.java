package com.ynyes.huyue.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdUser;

public interface TdUserRepo extends PagingAndSortingRepository<TdUser, Long>, JpaSpecificationExecutor<TdUser> {

	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @author DengXiao
	 */
	TdUser findByUsernameAndPassword(String username, String password);
}
