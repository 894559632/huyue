package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	 * 查找所有的用户，按照排序号排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:30:54
	 */
	public Page<TdUser> findAllOrderBySortIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findAll(pageRequest);
	}

	/**
	 * 根据关键词模糊查找用户（相关字段：用户名；分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:31:25
	 */
	public Page<TdUser> findByUsernameContainingOrderBySortIdAsc(String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
		return repository.findByUsernameContainingOrderBySortIdAsc(keywords, pageRequest);
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

	/**
	 * 根据用户名查找用户
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:20:17
	 */
	public TdUser findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsername(username);
	}

	/**
	 * 根据用户名查找非指定id的用户
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月26日下午8:20:51
	 */
	public TdUser findByUsernameAndIdNot(String username, Long id) {
		if (null == username || null == id) {
			return null;
		}
		return repository.findByUsernameAndIdNot(username, id);
	}
}
