package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdOrderGoods;

/**
 * TdOrderGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderGoodsRepo extends
		PagingAndSortingRepository<TdOrderGoods, Long>,
		JpaSpecificationExecutor<TdOrderGoods> 
{
	List<TdOrderGoods> findByGoodsIdIn(List<Long> goodsIdList);
	
    @Query(value="select og.td_order_id from td_order_goods og where og.id=?1",nativeQuery=true)
    Integer findById(Long ordergoodsId);
}
