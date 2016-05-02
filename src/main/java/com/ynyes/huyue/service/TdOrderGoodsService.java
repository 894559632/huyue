package com.ynyes.huyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdOrderGoods;
import com.ynyes.huyue.repository.TdOrderGoodsRepo;

/**
 * TdOrderGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderGoodsService {
    
    @Autowired
    TdOrderGoodsRepo repository;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdOrderGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderGoods> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdOrderGoods findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdOrderGoods> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderGoods>) repository.findAll(ids);
    }
    
    public List<TdOrderGoods> findAll()
    {
        return (List<TdOrderGoods>) repository.findAll();
    }
    
    public Page<TdOrderGoods> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    /**
	 * @author lc
	 * @注释：查询特定商品类别的订单商品
	 */
    public List<TdOrderGoods> findByCategoryId(Long categoryId){
    	List<TdGoods> tdGoodsList = tdGoodsService.findByCategoryId(categoryId);
    	
    	List<Long> tdGoodsId = new ArrayList<>();
    	for(TdGoods tdGoods : tdGoodsList){
    		tdGoodsId.add(tdGoods.getId());    		
    	}
    	
    	if (!tdGoodsId.isEmpty()) {
			return repository.findByGoodsIdIn(tdGoodsId);
		}
    	
    	return null;
    }
    
    /**
	 * @author lc
	 * @注释：查询所属订单id
	 */
    public Long getOrderId(Long tdOrdergoodsId){
    	
    	if (null == repository.findById(tdOrdergoodsId)) {
			return null;
		}
    	return (long)repository.findById(tdOrdergoodsId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderGoods save(TdOrderGoods e)
    {
        
        return repository.save(e);
    }
    
    public List<TdOrderGoods> save(List<TdOrderGoods> entities)
    {
        
        return (List<TdOrderGoods>) repository.save(entities);
    }
}
