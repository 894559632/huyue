package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdManager;
import com.ynyes.huyue.repository.TdManagerRepo;

@Service
@Transactional
public class TdManagerService {

	@Autowired
	private TdManagerRepo repository;

	public TdManager save(TdManager e) {
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

	public TdManager findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdManager> findAll() {
		return (List<TdManager>) repository.findAll();
	}

	/**
	 * 根据用户名和密码查找指定的后台用户
	 * 
	 * @author DengXiao
	 */
	public TdManager findByUsernameAndPassword(String username, String password) {
		if (null == username || null == password) {
			return null;
		}
		return repository.findByUsernameAndPassword(username, password);
	}
}
