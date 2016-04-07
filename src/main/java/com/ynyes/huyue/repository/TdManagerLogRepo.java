package com.ynyes.huyue.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdManagerLog;

public interface TdManagerLogRepo extends PagingAndSortingRepository<TdManagerLog, Long>,JpaSpecificationExecutor<TdManagerLog>{

}
