package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdAdType;
import com.ynyes.huyue.repository.TdAdTypeRepo;

@Service
@Transactional
public class TdAdTypeService {

	@Autowired
	private TdAdTypeRepo repository;

	public TdAdType save(TdAdType e) {
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

	public TdAdType findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdAdType> findAll() {
		return (List<TdAdType>) repository.findAll();
	}

	/**
	 * 根据广告位标题查找指定的广告位
	 * 
	 * @author DengXiao
	 */
	public TdAdType findByTitle(String title) {
		if (null == title) {
			return null;
		}
		return repository.findByTitle(title);
	}
}
