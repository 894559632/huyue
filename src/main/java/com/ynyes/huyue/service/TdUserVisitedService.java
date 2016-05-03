package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdUserVisited;
import com.ynyes.huyue.repository.TdUserVisitedRepo;

/**
 * 用户浏览记录服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月3日下午2:28:28
 */

@Service
@Transactional
public class TdUserVisitedService {

	private TdUserVisitedRepo repository;

	public TdUserVisited save(TdUserVisited e) {
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

	public TdUserVisited findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUserVisited> findAll() {
		return (List<TdUserVisited>) repository.findAll();
	}

	/**
	 * 根据用户名查找用户浏览记录，按照浏览时间倒序排序
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月3日下午2:27:13
	 */
	public List<TdUserVisited> findByUsernameOrderByVisitTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByVisitTimeDesc(username);
	}

	/**
	 * 删除指定用户浏览记录的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月3日下午2:49:18
	 */
	public void deleteByUsername(String username) {
		if (null != username) {
			repository.deleteByUsername(username);
		}
	}

}
