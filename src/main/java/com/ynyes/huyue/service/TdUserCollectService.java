package com.ynyes.huyue.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ynyes.huyue.entity.TdUserCollect;
import com.ynyes.huyue.repository.TdUserCollectRepo;

/**
 * 用户收藏服务类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月27日下午8:54:45
 */
@Service
@Transactional
public class TdUserCollectService {

	@Autowired
	private TdUserCollectRepo repository;

	public TdUserCollect save(TdUserCollect e) {
		if (null == e) {
			return e;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdUserCollect findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdUserCollect> findAll() {
		return (List<TdUserCollect>) repository.findAll();
	}

	public Page<TdUserCollect> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照收藏时间倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:53:36
	 */
	public Page<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByCollectTimeDesc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照收藏时间正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:53:36
	 */
	public Page<TdUserCollect> findByUsernameOrderByCollectTimeAsc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByCollectTimeAsc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照销量正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	public Page<TdUserCollect> findByUsernameOrderBySoldNumberAsc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderBySoldNumberAsc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照销量倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	public Page<TdUserCollect> findByUsernameOrderBySoldNumberDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderBySoldNumberDesc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照价格正序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	public Page<TdUserCollect> findByUsernameOrderByPriceAsc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByPriceAsc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏，按照价格倒序排序（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午9:32:34
	 */
	public Page<TdUserCollect> findByUsernameOrderByPriceDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByPriceDesc(username, pageRequest);
	}

	/**
	 * 根据用户名查找用户收藏（不分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年4月27日下午8:54:17
	 */
	public List<TdUserCollect> findByUsernameOrderByCollectTimeDesc(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameOrderByCollectTimeDesc(username);
	}

}
