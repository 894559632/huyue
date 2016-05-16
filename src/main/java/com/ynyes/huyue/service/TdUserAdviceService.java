package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdUserAdvice;
import com.ynyes.huyue.repository.TdUserAdviceRepo;

/**
 * 用户反馈服务类
 * 
 * @author 作者：DengXiao
 * @version 版本：下午4:38:02
 */

@Service
@Transactional
public class TdUserAdviceService {

	@Autowired
	private TdUserAdviceRepo repository;

	public TdUserAdvice save(TdUserAdvice e) {
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

	public TdUserAdvice findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUserAdvice> findAll() {
		return (List<TdUserAdvice>) repository.findAll();
	}

	public Page<TdUserAdvice> findAllOrderByAdviceTimeDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "adviceTime"));
		return repository.findAll(pageRequest);
	}
}
