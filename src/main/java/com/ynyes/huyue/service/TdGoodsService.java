package com.ynyes.huyue.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.huyue.entity.TdBrand;
import com.ynyes.huyue.entity.TdGoods;
import com.ynyes.huyue.entity.TdPriceChangeLog;
import com.ynyes.huyue.entity.TdProductCategory;
import com.ynyes.huyue.repository.TdGoodsRepo;

/**
 * TdGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGoodsService {
	@Autowired
	TdGoodsRepo repository;

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdBrandService tdBrandService;

	@Autowired
	TdParameterService tdParameterService;

	@Autowired
	TdGoodsParameterService tdGoodsParameterService;

	@Autowired
	TdPriceChangeLogService tdPriceChangeLogService;

	@Autowired
	TdUserService tdUserService;

	/**
	 * 搜索商品
	 * 
	 * @param keywords
	 *            关键字
	 * @param page
	 *            页号
	 * @param size
	 *            页大小
	 * @param sortName
	 *            排序字段名
	 * @param sd
	 *            排序方向
	 * @return
	 */
	public Page<TdGoods> searchGoods(String keywords, int page, int size, String sortName, Direction dir) {
		if (null == keywords || null == sortName) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size, new Sort(dir, sortName));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
						keywords, keywords, keywords, keywords, pageRequest);
	}

	/**
	 * 查找所有商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List<TdGoods> findByCategoryId(Long categoryId) {
		return repository.findByCategoryId(categoryId);
	}

	public TdGoods findByIdAndIsOnsaleTrue(Long id) {
		return repository.findByIdAndIsOnSaleTrue(id);
	}

	public Page<TdGoods> findAll(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findAll(pageRequest);
	}

	public Page<TdGoods> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdGoods> findByIshotTrueAndIsOnSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findByIsHotTrueAndIsOnSaleTrue(pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	public Page<TdGoods> findAllAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	public List<TdGoods> findTop10ByIsOnSaleTrueOrderBySoldNumberDesc() {
		return repository.findTop10ByIsOnSaleTrueOrderBySoldNumberDesc();
	}

	public List<TdGoods> findTop20ByIsOnSaleTrueOrderBySoldNumberDesc() {
		return repository.findTop20ByIsOnSaleTrueOrderBySoldNumberDesc();
	}

	public Page<TdGoods> searchAndOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrSubTitleContainingOrDetailContainingOrderBySortIdAsc(keywords,
				keywords, keywords, pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrSubTitleContainingOrDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
				keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdOrderBySortIdAsc(String keywords, Long categoryId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingOrCategoryIdTreeContainingAndSubTitleContainingOrCategoryIdTreeContainingAndDetailContainingOrderBySortIdAsc(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingAndIsOnSaleTrueOrderBySortIdAsc(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	// 猜你喜欢 zhangji
	public Page<TdGoods> findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(categoryId,
				pageRequest);
	}

	public Page<TdGoods> findByCategoryTreeAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + categoryId + "]";

		return repository.findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(catIdStr,
				pageRequest);

	}

	public List<TdGoods> findByIdAndIsOnSaleTrue(Iterable<Long> ids) {
		return repository.findByIdAndIsOnSaleTrue(ids);
	}

	// 查找新品推荐上架商品按id排序
	public Page<TdGoods> findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsNewTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	/**
	 * @author lc
	 * @注释：后台商品删选
	 */
	public Page<TdGoods> findByIsOnSaleTrueAndFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleTrueAndIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalseAndIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalse(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseOrDetailContainingIgnoreCaseAndIsOnSaleFalse(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsFlashSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsFlashSaleTrueOrSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrDetailContainingIgnoreCaseAndIsFlashSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalse(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsFlashSaleTrueOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsFlashSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsFlashSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsFlashSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleTrueAndGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleTrueAndIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsOnSaleFalseAndIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrue(pageRequest);
	}

	public Page<TdGoods> searchAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByTitleContainingIgnoreCaseAndIsGroupSaleTrueOrSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrDetailContainingIgnoreCaseAndIsGroupSaleTrue(
						keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(Long catId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(String keywords,
			Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsOnSaleTrueAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsGroupSaleTrueOrderBySortIdAsc(Long catId, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsGroupSaleTrue(catIdStr, pageRequest);
	}

	public Page<TdGoods> searchAndFindByCategoryIdAndIsGroupSaleTrueOrderBySortIdAsc(String keywords, Long categoryId,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		String catIdStr = "[" + categoryId + "]";

		return repository
				.findByCategoryIdTreeContainingAndTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingIgnoreCaseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndDetailContainingIgnoreCaseAndIsGroupSaleTrue(
						catIdStr, keywords, catIdStr, keywords, catIdStr, keywords, pageRequest);
	}

	/**** 后台商品删选 ****/

	/**
	 * @author lc
	 * @注释：新的商品筛选
	 */
	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
			long catId, double priceLow, double priceHigh, List<String> paramValueList, Pageable pageRequest) {

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
			long catId, long brandId, double priceLow, double priceHigh, List<String> paramValueList,
			Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(long catId,
			List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(long catId,
			long brandId, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(long catId, double priceLow,
			double priceHigh, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(long catId,
			long brandId, double priceLow, double priceHigh, List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrue(long catId, List<String> paramValueList,
			Pageable pageRequest) {
		String paramStr = "%";

		if (null != paramValueList) {
			for (int i = 0; i < paramValueList.size(); i++) {
				String value = paramValueList.get(i);
				if (!"".equals(value)) {
					paramStr += value;
					paramStr += "%";
				}
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(long catId, long brandId,
			List<String> paramValueList, Pageable pageRequest) {
		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	/**
	 * 按类型查找所有商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByReturnPriceNotZeroAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByReturnPriceNotAndIsOnSaleTrue(0.0, pageRequest);
	}

	public Page<TdGoods> findByReturnPriceNotZeroAndSearchAndIsOnSaleTrue(int page, int size, String keywords) {
		if (null == keywords) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByReturnPriceNotAndTitleContainingAndIsOnSaleTrue(0.0, keywords, pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStartTimeBeforeAndGroupSaleStopTimeAfterAndIsOnSaleTrue(
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueAndGroupSaleStartTimeAfterAndGroupSaleStopTimeAfterAndIsOnSaleTrue(
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByIsGroupSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeAfterAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	public Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingOrderBySortIdAsc(Long catId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingOrderBySortIdAsc(catIdStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(Long catId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		String catIdStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(catIdStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrue(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrue(catStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrue(Long catId, Pageable pageRequest) {
		if (null == catId) {
			return null;
		}

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrue(catStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(Long catId, int page,
			int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(catStr,
				pageRequest);
	}

	public Page<TdGoods> findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsRecommendIndexTrueAndIsOnSaleTrueOrderBySortIdAsc(Long catId, int page,
			int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsRecommendIndexTrueAndIsOnSaleTrueOrderBySortIdAsc(catStr,
				pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(catStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderByOnSaleTimeDesc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderByOnSaleTimeDesc(catStr, pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleTrueOrderByOnSaleTimeDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIsOnSaleTrueOrderByOnSaleTimeDesc(pageRequest);
	}

	public Page<TdGoods> findByIsOnSaleTrueOrderBySoldNumberDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		return repository.findByIsOnSaleTrue(pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, priceLow, priceHigh, paramStr, pageRequest);
	}

	// 无价格区间
	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, 0L, paramStr, pageRequest);
	}

	// 显示有货和无货商品
	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(long catId,
			double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> searchGoods(String keywords, int page, int size) {
		if (null == keywords) {
			return null;
		}

		keywords = keywords.replace(" ", "%");

		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository
				.findByTitleContainingAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrDetailContainingAndIsOnSaleTrue(
						keywords, keywords, keywords, keywords, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(long catId,
			double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(long catId,
			double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(long catId,
			double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, double priceLow, double priceHigh, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
			long catId, long brandId, double priceLow, double priceHigh, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository
				.findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
						"[" + catId + "]", brandId, priceLow, priceHigh, paramStr, pageRequest);
	}

	// 无价格区间
	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(long catId, int page,
			int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(long catId, int page,
			int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(long catId, int page, int size,
			List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(long catId, int page,
			int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(long catId, int page,
			int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(long catId, int page,
			int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue("[" + catId + "]",
				paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "soldNumber"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "salePrice"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(long catId,
			long brandId, int page, int size, List<String> paramValueList) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));

		String paramStr = "%";

		for (int i = 0; i < paramValueList.size(); i++) {
			String value = paramValueList.get(i);
			if (!"".equals(value)) {
				paramStr += value;
				paramStr += "%";
			}
		}

		return repository.findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
				"[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public List<TdGoods> findByProductIdAndIsOnSaleTrue(Long productId) {
		if (null == productId) {
			return null;
		}

		return repository.findByProductIdAndIsOnSaleTrue(productId);
	}

	/**
	 * @author lc
	 * @注释：所有秒杀商品
	 */
	public Page<TdGoods> findByFlashSaleAllOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueOrderByFlashSaleStartTimeAsc(pageRequest);
	}

	/**
	 * @author lc
	 * @注释：限定类别的秒杀商品
	 */
	public Page<TdGoods> findByCategoryAndFlashSaleOrderByFlashSaleStartTimeAsc(Long catId, int page, int size) {
		if (null == catId) {
			return null;
		}

		PageRequest pageRequest = new PageRequest(page, size);

		String catStr = "[" + catId + "]";

		return repository
				.findByIsFlashSaleTrueAndIsOnSaleTrueAndCategoryIdTreeContainingAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(
						catStr, new Date(), new Date(), pageRequest);
	}

	/**
	 * 所有团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleAllOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(pageRequest);
	}

	/**
	 * 正在团购商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSalingOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeOrderByGroupSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	/**
	 * 正在秒杀商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSalingOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository
				.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(
						new Date(), new Date(), pageRequest);
	}

	/**
	 * 已结束团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleEndedOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeBeforeOrderByGroupSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 已结束秒杀
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSaleEndedOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeOrderByFlashSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 即将开始团购
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByGroupSaleGoingToStartOrderByGroupSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStartTimeAfterOrderByGroupSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 即将开始秒杀
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TdGoods> findByFlashSaleGoingToStartOrderByFlashSaleStartTimeAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size,
				new Sort(Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

		return repository.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterOrderByFlashSaleStartTimeAsc(
				new Date(), pageRequest);
	}

	/**
	 * 判断该商品是否正在进行秒杀
	 * 
	 * @param tdGoods
	 * @return
	 */
	public boolean isFlashSaleTrue(TdGoods tdGoods) {
		if (null == tdGoods) {
			return false;
		}

		Date curr = new Date();

		if (null != tdGoods.getIsFlashSale() && tdGoods.getIsFlashSale() && null != tdGoods.getFlashSaleStartTime()
				&& tdGoods.getFlashSaleStartTime().before(curr) && null != tdGoods.getFlashSaleStopTime()
				&& tdGoods.getFlashSaleStopTime().after(curr) && null != tdGoods.getFlashSaleLeftNumber()
				&& tdGoods.getFlashSaleLeftNumber().compareTo(0L) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 计算实时秒杀价
	 * 
	 * @param goods
	 * @return
	 */
	public Double getFlashPrice(TdGoods goods) {
		if (null == goods) {
			return null;
		}

		Double flashPrice = null;
		Date curr = new Date();

		if (null != goods.getIsFlashSale() && null != goods.getFlashSaleStartTime()
				&& null != goods.getFlashSaleStopTime() && null != goods.getFlashSalePrice() && goods.getIsFlashSale()
				&& goods.getFlashSaleStopTime().after(curr) && goods.getFlashSaleStartTime().before(curr)) {
			// 剩余毫秒数
			long ts = goods.getFlashSaleStopTime().getTime() - curr.getTime();
			// 总共毫秒数
			long allts = goods.getFlashSaleStopTime().getTime() - goods.getFlashSaleStartTime().getTime();

			flashPrice = goods.getFlashSalePrice() * ts / allts;

			BigDecimal b = new BigDecimal(flashPrice);

			flashPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		return flashPrice;
	}

	/**
	 * 判断该商品是否正在进行团购
	 * 
	 * @param tdGoods
	 * @return
	 */
	public boolean isGroupSaleTrue(TdGoods tdGoods) {
		if (null == tdGoods) {
			return false;
		}

		Date curr = new Date();

		if (null != tdGoods.getIsGroupSale() && tdGoods.getIsGroupSale() && null != tdGoods.getGroupSaleStartTime()
				&& tdGoods.getGroupSaleStartTime().before(curr) && null != tdGoods.getGroupSaleStopTime()
				&& tdGoods.getGroupSaleStopTime().after(curr) && null != tdGoods.getGroupSaleLeftNumber()
				&& tdGoods.getGroupSaleLeftNumber().compareTo(0L) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdGoods e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdGoods findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 保存类型
	 * 
	 * @param e
	 * @return
	 */
	public TdGoods save(TdGoods e, String manager) {
		if (null == e) {
			return null;
		}

		// 参数类型ID
		Long paramCategoryId = null;

		// 保存分类名称
		if (null != e.getCategoryId()) {
			TdProductCategory cat = tdProductCategoryService.findOne(e.getCategoryId());
			e.setCategoryTitle(cat.getTitle());
			e.setCategoryIdTree(cat.getParentTree());

			paramCategoryId = cat.getParamCategoryId();
		}

		// 保存品牌名称
		if (null != e.getBrandId()) {
			TdBrand brand = tdBrandService.findOne(e.getBrandId());

			if (null != brand) {
				e.setBrandTitle(brand.getTitle());
			}
		}

		// 保存销售价
		if (null == e.getReturnPrice()) {
			e.setReturnPrice(0.0);
		}

		if (null == e.getOutFactoryPrice()) {
			e.setOutFactoryPrice(0.0);
		}

		// 创建时间
		if (null == e.getCreateTime()) {
			e.setCreateTime(new Date());
		}

		// 上架时间
		if (null == e.getOnSaleTime() && e.getIsOnSale()) {
			e.setOnSaleTime(new Date());
		}

		e = repository.save(e);

		// 添加改价记录
		TdPriceChangeLog priceLog = tdPriceChangeLogService.findTopByGoodsIdOrderByIdDesc(e.getId());

		// 没有改过价，或改价后的记录与当前销售价不相等
		if (null == priceLog || !priceLog.getPrice().equals(e.getSalePrice())) {
			TdPriceChangeLog newPriceLog = new TdPriceChangeLog();

			newPriceLog.setCreateTime(new Date());
			newPriceLog.setGoodsId(e.getId());
			newPriceLog.setGoodsTitle(e.getTitle() + (null == e.getSelectOneValue() ? "" : " " + e.getSelectOneValue())
					+ (null == e.getSelectTwoValue() ? "" : " " + e.getSelectTwoValue())
					+ (null == e.getSelectThreeValue() ? "" : " " + e.getSelectThreeValue()));
			newPriceLog.setOperator(manager);

			if (null != priceLog) {
				newPriceLog.setOriginPrice(priceLog.getPrice());
			}

			newPriceLog.setPrice(e.getSalePrice());
			newPriceLog.setSortId(99L);

			tdPriceChangeLogService.save(newPriceLog);
		}

		return e;
	}

	public Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySortIdAsc(int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySortIdAsc(pageRequest);
	}

	public Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsTrueOrderBySortIdAsc(int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIsRecommendIndexTrueAndIsOnSaleTrueAndIsPointGoodsTrueOrderBySortIdAsc(pageRequest);
	}

	public Page<TdGoods> findByisHotTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySoldNumberDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByisHotTrueAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySoldNumberDesc(pageRequest);
	}

	/**
	 * 根据分类编号查找上架的非积分商品
	 * 
	 * @author 作者：DengXiao
	 * @version 创建时间：2016年5月3日下午3:07:27
	 */
	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySortIdAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySortIdAsc(categoryId);
	}

	public List<TdGoods> findAll() {
		return (List<TdGoods>) repository.findAll();
	}

	public List<TdGoods> findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc() {
		return repository.findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc();
	}

	public List<TdGoods> findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc() {
		return repository.findByIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc();
	}

	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceAsc(categoryId);
	}

	public List<TdGoods> findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc(Long categoryId) {
		if (null == categoryId) {
			return null;
		}
		return repository.findByCategoryIdAndIsOnSaleTrueAndIsPointGoodsFalseOrderBySalePriceDesc(categoryId);
	}
}
