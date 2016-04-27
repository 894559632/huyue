package com.ynyes.huyue.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdUserCollect;

/**
 * 用户收藏仓库类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月27日下午8:52:27
 */
public interface TdUserCollectRepo
		extends PagingAndSortingRepository<TdUserCollect, Long>, JpaSpecificationExecutor<TdUserCollect> {

	/**
	 * 根据用户名查找用户收藏，按照收藏时间倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:53:36
	 */
	Page<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏，按照收藏时间正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:53:36
	 */
	Page<TdUserCollect> findByUsernameOrderByCollectTimeAsc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏，按照销量正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	Page<TdUserCollect> findByUsernameOrderBySoldNumberAsc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏，按照销量倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	Page<TdUserCollect> findByUsernameOrderBySoldNumberDesc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏，按照价格正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	Page<TdUserCollect> findByUsernameOrderByPriceAsc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏，按照价格倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	Page<TdUserCollect> findByUsernameOrderByPriceDesc(String username, Pageable page);

	/**
	 * 根据用户名查找用户收藏（不分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:54:17
	 */
	List<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username);
}
