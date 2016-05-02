package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdShippingAddress;

/**
 * 收货地址仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月2日下午3:48:52
 */
public interface TdShippingAddressRepo
		extends PagingAndSortingRepository<TdShippingAddress, Long>, JpaSpecificationExecutor<TdShippingAddress> {

	/**
	 * 根据用户id查找默认收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:50:42
	 */
	TdShippingAddress findByUserIdAndIsDefaultTrue(Long userId);

	/**
	 * 根据用户id查找所有的收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:51:25
	 */
	List<TdShippingAddress> findByUserId(Long userId);

	/**
	 * 根据用户名查找默认收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:50:42
	 */
	TdShippingAddress findByUsernameAndIsDefaultTrue(String username);

	/**
	 * 根据用户名查找所有的收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:51:25
	 */
	List<TdShippingAddress> findByUsername(String username);
}
