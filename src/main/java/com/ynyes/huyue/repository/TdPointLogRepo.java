package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdPointLog;

/**
 * 积分变更记录仓库类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月30日下午3:38:43
 */
public interface TdPointLogRepo
		extends PagingAndSortingRepository<TdPointLog, Long>, JpaSpecificationExecutor<TdPointLog> {

	/**
	 * 根据用户id查找积分变更明细
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:39:50
	 */
	List<TdPointLog> findByUsernameOrderByChangeTimeDesc(String username);
	
	/**
	 * 根据用户id查找积分变更明细
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:39:50
	 */
	Page<TdPointLog> findByUsernameOrderByChangeTimeDesc(String username,Pageable page);
}
