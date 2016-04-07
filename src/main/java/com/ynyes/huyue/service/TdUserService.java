package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdUser;
import com.ynyes.huyue.repository.TdUserRepo;

@Service
@Transactional
public class TdUserService {

	@Autowired
	private TdUserRepo repository;

	public TdUser save(TdUser e) {
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

	public TdUser findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUser> findAll() {
		return (List<TdUser>) repository.findAll();
	}

	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @author DengXiao
	 */
	public TdUser findByUsernameAndPassword(String username, String password) {
		if (null == username || null == password) {
			return null;
		}
		return repository.findByUsernameAndPassword(username, password);
	}
}
