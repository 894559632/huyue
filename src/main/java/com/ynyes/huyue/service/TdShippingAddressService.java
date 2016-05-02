package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdShippingAddress;
import com.ynyes.huyue.repository.TdShippingAddressRepo;

/**
 * 收货地址服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月2日下午3:52:11
 */

@Service
@Transactional
public class TdShippingAddressService {

	@Autowired
	private TdShippingAddressRepo repository;

	public TdShippingAddress save(TdShippingAddress e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdShippingAddress findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdShippingAddress> findAll() {
		return (List<TdShippingAddress>) repository.findAll();
	}

	/**
	 * 根据用户id查找默认收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:50:42
	 */
	public TdShippingAddress findByUserIdAndIsDefaultTrue(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserIdAndIsDefaultTrue(userId);
	}

	/**
	 * 根据用户id查找所有的收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:51:25
	 */
	public List<TdShippingAddress> findByUserId(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserId(userId);
	}

	/**
	 * 根据用户名查找默认收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:50:42
	 */
	public TdShippingAddress findByUsernameAndIsDefaultTrue(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndIsDefaultTrue(username);
	}

	/**
	 * 根据用户名查找所有的收货地址
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月2日下午3:51:25
	 */
	public List<TdShippingAddress> findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsername(username);
	}
}
