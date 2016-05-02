package com.ynyes.huyue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	/**
	 * 根据用户名查找用户
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:20:17
	 */
	TdUser findByUsername(String username);

	/**
	 * 根据用户名查找启用的用户
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:08:56
	 */
	TdUser findByUsernameAndIsEnableTrue(String username);

	/**
	 * 根据用户名查找非指定id的用户
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:20:51
	 */
	TdUser findByUsernameAndIdNot(String username, Long id);

	/**
	 * 根据关键字模糊查询用户（相关字段：用户名）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:26:19
	 */
	Page<TdUser> findByUsernameContainingOrderBySortIdAsc(String keywords, Pageable page);
}
