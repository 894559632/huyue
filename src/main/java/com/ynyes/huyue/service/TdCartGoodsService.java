package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdCartGoods;
import com.ynyes.huyue.repository.TdCartGoodsRepo;

/**
 * 购物车服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月4日下午3:04:43
 */

@Service
@Transactional
public class TdCartGoodsService {

	@Autowired
	private TdCartGoodsRepo repository;

	public TdCartGoods save(TdCartGoods e) {
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

	public TdCartGoods findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdCartGoods> findAll() {
		return (List<TdCartGoods>) repository.findAll();
	}

	/**
	 * 查询指定用户指定商品的购物车
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月4日下午3:26:04
	 */
	public TdCartGoods findByGoodsIdAndUsername(Long goodsId, String username) {
		if (null == goodsId || null == username) {
			return null;
		}
		return repository.findByGoodsIdAndUsername(goodsId, username);
	}

	/**
	 * 根据用户名查找购物车
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月4日下午3:34:05
	 */
	public List<TdCartGoods> findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsername(username);
	}
}
