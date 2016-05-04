package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdCartGoods;

/**
 * 购物车仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月4日下午3:03:48
 */
public interface TdCartGoodsRepo
		extends PagingAndSortingRepository<TdCartGoods, Long>, JpaSpecificationExecutor<TdCartGoods> {

	/**
	 * 查询指定用户指定商品的购物车
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月4日下午3:26:04
	 */
	TdCartGoods findByGoodsIdAndUsername(Long goodsId, String username);

	/**
	 * 根据用户名查找购物车
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月4日下午3:34:05
	 */
	List<TdCartGoods> findByUsername(String username);
}
