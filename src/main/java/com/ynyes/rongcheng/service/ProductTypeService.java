package com.ynyes.rongcheng.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.rongcheng.entity.Parameter;
import com.ynyes.rongcheng.entity.ProductType;
import com.ynyes.rongcheng.entity.ProductTypeParameter;
import com.ynyes.rongcheng.repository.ParameterRepo;
import com.ynyes.rongcheng.repository.ProductTypeRepo;

/**
 * 商品类型服务类
 * 
 * @author Sharon
 */

@Service
@Transactional
public class ProductTypeService {
    @Autowired
    ProductTypeRepo repository;
    
    @Autowired
    ParameterRepo parameterRepo;
    
    /**
     * 根据父类型查找类型列表
     * 
     * @param parent 父类型，为NULL时查找的是根类型，不为空时查找的是该类型下的子类型
     * 
     * @return 商品类型列表
     */
    public List<ProductType> findByParent(String parent)
    {
        List<ProductType> typeList = null;
        
        if (null == parent)
        {
            typeList = repository.findByParentIsNull();
        }
        else
        {
            typeList = repository.findByParent(parent);
        }
        
        return typeList;
    }
    
    /**
     * 通过类型ID查找该类型的参数列表
     * 
     * @param typeId 商品类型ID
     * @param direction 排序方向，asc升序，desc降序，为NULL时不进行排序
     * @param property 排序字段名，为NULL时不进行排序
     * @return 类型属性列表
     */
    public List<Parameter> findParameterById(Long typeId,
                                                String direction,
                                                String property)
    {
        List<ProductTypeParameter> paramList = null;
        List<Long> idList = new ArrayList<Long>();
        
        if (null == typeId)
        {
            return null;
        }
        
        ProductType type = repository.findOne(typeId);
        
        if (null == type)
        {
            return null;
        }
        
        paramList = type.getTypeParamList();
        
        if (null == paramList)
        {
            return null;
        }
        
        for (ProductTypeParameter param : paramList)
        {
            if (null != param)
            {
                idList.add(param.getParamId());
            }
        }
        
        if (null == direction || null == property)
        {
            return parameterRepo.findByIdIn(idList);
        }
        
        Sort sort = new Sort(direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, 
                property);
        
        return parameterRepo.findByIdIn(idList, sort);
    }
    
    /**
     * 通过类型ID查找该类型可检索的参数列表
     * 
     * @param typeId 商品类型ID
     * @return 该类型可检索的属性列表
     */
    public List<Parameter> findParametersSearchableById(Long typeId)
    {
        List<ProductTypeParameter> paramList = null;
        List<Long> idList = new ArrayList<Long>();
        
        if (null == typeId)
        {
            return null;
        }
        
        ProductType type = repository.findOne(typeId);
        
        if (null == type)
        {
            return null;
        }
        
        paramList = type.getTypeParamList();
        
        if (null == paramList)
        {
            return null;
        }
        
        for (ProductTypeParameter param : paramList)
        {
            if (null != param)
            {
                idList.add(param.getParamId());
            }
        }
        
        return parameterRepo.findByIdInAndIsSearchableTrue(idList);
    }
    
    /**
     * 查找类型列表并分页
     * 
     * @param page 页号，从0开始
     * @param size 每页大小
     * @param direction 排序方向，不区分大小写，asc表示升序，desc表示降序，为NULL时不进行排序
     * @param property 排序的字段名，为NULL时不进行排序
     * @return 类型分页
     */
    public Page<ProductType> findAll(int page, int size, 
                            String direction, String property)
    {
        Page<ProductType> typePage = null;
        PageRequest pageRequest = null;
        
        if (page < 0 || size < 0)
        {
            return null;
        }
        
        if (null == direction || null == property)
        {
            pageRequest = new PageRequest(page, size);
        }
        else
        {
            Sort sort = new Sort(direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, 
                                 property);
            pageRequest = new PageRequest(page, size, sort);
        }
        
        typePage = repository.findAll(pageRequest);
        
        return typePage;
    }
    
    /**
     * 返回所有商品类型
     * 
     * @return 所有商品类型
     */
    public List<ProductType> findAll() {

        return (List<ProductType>) repository.findAll();
    }
}