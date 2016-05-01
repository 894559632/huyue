package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdCity;
import com.ynyes.huyue.repository.TdCityRepo;

/**
 * 城市服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月26日下午10:09:48
 */

@Service
@Transactional
public class TdCityService {

	@Autowired
	private TdCityRepo repository;

	public TdCity save(TdCity e) {
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

	public TdCity findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCity> findAll() {
		return (List<TdCity>) repository.findAll();
	}

	public Page<TdCity> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}
}
