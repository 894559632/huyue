package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdLotteryLog;

/**
 * 抽奖记录仓库类
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年6月6日下午5:41:48
 */
public interface TdLotteryLogRepo
		extends PagingAndSortingRepository<TdLotteryLog, Long>, JpaSpecificationExecutor<TdLotteryLog> {

	List<TdLotteryLog> findByUsernameOrderByLotteryTimeDesc(String username);
}
