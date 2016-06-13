package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdLotteryLog;
import com.ynyes.huyue.repository.TdLotteryLogRepo;

/**
 * 抽奖记录服务类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年6月6日下午5:42:45
 */

@Service
@Transactional
public class TdLotteryLogService {

	@Autowired
	private TdLotteryLogRepo repository;

	public TdLotteryLog save(TdLotteryLog e) {
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

	public TdLotteryLog findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdLotteryLog> findAll() {
		return (List<TdLotteryLog>) repository.findAll();
	}

	public Page<TdLotteryLog> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "lotteryTime"));
		return repository.findAll(pageRequest);
	}
	
	public List<TdLotteryLog> findByUsernameOrderByLotteryTimeDesc(String username){
		if(null == username){
			return null;
		}
		return repository.findByUsernameOrderByLotteryTimeDesc(username);
	}
}
