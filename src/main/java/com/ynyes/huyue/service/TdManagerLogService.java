package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdManagerLog;
import com.ynyes.huyue.repository.TdManagerLogRepo;

@Service
@Transactional
public class TdManagerLogService {

	@Autowired
	private TdManagerLogRepo repository;

	public TdManagerLog save(TdManagerLog e) {
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

	public TdManagerLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdManagerLog> findAll() {
		return (List<TdManagerLog>) repository.findAll();
	}
}
