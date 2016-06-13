package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdPointLog;
import com.ynyes.huyue.repository.TdPointLogRepo;

/**
 * 积分变更明细服务类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月30日下午3:40:40
 */

@Service
@Transactional
public class TdPointLogService {

	@Autowired
	private TdPointLogRepo repository;

	public TdPointLog save(TdPointLog e) {
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

	public TdPointLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdPointLog> findAll() {
		return (List<TdPointLog>) repository.findAll();
	}

	/**
	 * 根据用户id查找积分变更明细
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:39:50
	 */
	public List<TdPointLog> findByUsernameOrderByChangeTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByChangeTimeDesc(username);
	}

	/**
	 * 根据用户id查找积分变更明细
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月30日下午3:39:50
	 */
	public Page<TdPointLog> findByUsernameOrderByChangeTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByChangeTimeDesc(username, pageRequest);
	}
}
