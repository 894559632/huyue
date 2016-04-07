package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdAd;
import com.ynyes.huyue.repository.TdAdRepo;

@Service
@Transactional
public class TdAdService {

	@Autowired
	private TdAdRepo repository;

	public TdAd save(TdAd e) {
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

	public TdAd findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdAd> findAll() {
		return (List<TdAd>) repository.findAll();
	}

	/**
	 * 根据位置查找广告
	 * 
	 * @author DengXiao
	 */
	public List<TdAd> findByTypeIdOrderBySortIdAsc(Long typeId) {
		if (null == typeId) {
			return null;
		}
		return repository.findByTypeIdOrderBySortIdAsc(typeId);
	}

}
