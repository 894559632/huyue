package com.ynyes.huyue.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdExchangeLog;
import com.ynyes.huyue.repository.TdExchangeLogRepo;

/**
 * 说明
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月31日下午3:32:54
 */
@Service
@Transactional
public class TdExchangeLogService {

	@Autowired
	private TdExchangeLogRepo repository;

	public TdExchangeLog save(TdExchangeLog e) {
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

	public TdExchangeLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public TdExchangeLog findAll() {
		return (TdExchangeLog) repository.findAll();
	}

	/**
	 * 查找指定用户的兑换记录
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月31日下午3:32:06
	 */
	public Page<TdExchangeLog> findByUsernameOrderByExchangeTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByExchangeTimeDesc(username, pageRequest);
	}
}
