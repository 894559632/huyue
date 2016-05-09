package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdUserVisited;

/**
 * 用户浏览记录仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月3日下午2:25:12
 */
public interface TdUserVisitedRepo
		extends PagingAndSortingRepository<TdUserVisited, Long>, JpaSpecificationExecutor<TdUserVisited> {

	/**
	 * 根据用户名查找用户浏览记录，按照浏览时间倒序排序
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月3日下午2:27:13
	 */
	List<TdUserVisited> findByUsernameOrderByVisitTimeDesc(String username);

	/**
	 * 删除指定用户浏览记录的方法
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月3日下午2:49:18
	 */
	void deleteByUsername(String username);

	/**
	 * 查找指定用户，指定商品id的浏览记录
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月5日上午10:01:52
	 */
	TdUserVisited findByUsernameAndGoodsId(String username, Long goodsId);

	/**
	 * 查找指定用户，指定商品id的浏览记录
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月5日上午10:01:52
	 */
	TdUserVisited findByUserIdAndGoodsId(Long userId, Long goodsId);

}
