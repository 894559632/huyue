package com.ynyes.rongcheng.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.rongcheng.entity.Product;

/**
 * Product 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface ProductRepo extends
		PagingAndSortingRepository<Product, Long>,
		JpaSpecificationExecutor<Product> 
{
    /*
     * 查找所有商品
     */
    
    // 通过ID查找
    Page<Product> findByIdIn(Collection<Long> ids, Pageable page);
    List<Product> findByIdIn(Collection<Long> ids);
    
    // 通过ID查找上架商品
    Page<Product> findByIsOnSaleTrueAndIdIn(Collection<Long> ids, Pageable page);
    List<Product> findByIsOnSaleTrueAndIdIn(Collection<Long> ids);
    
    /*
     * 查找上线的商品
     */
    
    // 通过ID查找
    Page<Product> findByIdInAndIsOnSaleTrue(Collection<Long> ids, Pageable page);
    List<Product> findByIdInAndIsOnSaleTrue(Collection<Long> ids);
    
    Product findByIdAndIsOnSaleTrue(Long id);
    
    // 通过类型查找
    Page<Product> findByTypeAllLikeAndIsOnSaleTrue(String type, Pageable page);
    List<Product> findByTypeAllLikeAndIsOnSaleTrue(String type);
    

    // 这个函数用于按类型、品牌、各个后台设定的可检索属性值进行查找，并能根据销量、价格、上架时间等进行排序，还能进行分页，十分牛逼
    Page<Product> findByTypeAllLikeAndBrandNameAndParamValueAllLikeAndIsOnSaleTrue(String type, 
                                                               String brandName, 
                                                               String paramValue,
                                                               Pageable page);
    
    Page<Product> findByTypeAllLikeAndParamValueAllLikeAndIsOnSaleTrue(String type,
                                                                String paramValue,
                                                                Pageable page);
    
    Page<Product> findByTypeAllLikeAndBrandNameAndParamValueAllLikeAndPriceMinimumBetweenAndIsOnSaleTrue(String type, 
                                                                String brandName, 
                                                                String paramValue,
                                                                Double priceLow,
                                                                Double priceHigh,
                                                                Pageable page);
    
    Page<Product> findByTypeAllLikeAndParamValueAllLikeAndPriceMinimumBetweenAndIsOnSaleTrue(String type, 
                                                                String paramValue,
                                                                Double priceLow,
                                                                Double priceHigh,
                                                                Pageable page);
    
    // 通过类型查找商品ID
    @Query("select p.id from Product p where p.typeAll like ?1 and p.isOnSale = 1")
    List<Long> findIdByTypeAllLikeAndIsOnSaleTrue(String type);
 
    // 查找限时抢购商品
    Page<Product> findByIsOnSaleTrueAndIsFlashSaleTrueAndFlashSaleStopTimeAfterOrderByFlashSaleStartTimeDesc(Date now, Pageable page);
    List<Product> findByIsOnSaleTrueAndIsFlashSaleTrueAndFlashSaleStopTimeAfterOrderByFlashSaleStartTimeDesc(Date now);
    Page<Product> findByIsOnSaleTrueAndIsFlashSaleTrueAndFlashSaleStopTimeAfter(Date now, Pageable page);
    List<Product> findByIsOnSaleTrueAndIsFlashSaleTrueAndFlashSaleStopTimeAfter(Date now);
    
    // 查找明星产品
    Page<Product> findByIsOnSaleTrueAndIsStarProductTrue(Pageable page);
    Page<Product> findByIsOnSaleTrueAndIsStarProductTrueAndPriceMinimumBetween(Pageable page, Double priceLow, Double priceHigh);
    List<Product> findByIsOnSaleTrueAndIsStarProductTrue();

    // 搜索商品
    Page<Product> findByIsOnSaleTrueAndPriceMinimumBetweenAndNameLikeOrIsOnSaleTrueAndPriceMinimumBetweenAndBriefLikeOrIsOnSaleTrueAndPriceMinimumBetweenAndDetailLike(
            Double priceLow1, Double priceHigh1, String name,
            Double priceLow2, Double priceHigh2, String brief,
            Double priceLow3, Double priceHigh3, String detail, Pageable page);
    Page<Product> findByIsOnSaleTrueAndNameLikeOrIsOnSaleTrueAndBriefLikeOrIsOnSaleTrueAndDetailLike(
            String name, String brief, String detail, Pageable page);
}
