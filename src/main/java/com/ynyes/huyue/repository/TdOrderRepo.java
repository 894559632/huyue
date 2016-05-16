package com.ynyes.huyue.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.huyue.entity.TdOrder;

/**
 * TdOrder 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderRepo extends PagingAndSortingRepository<TdOrder, Long>, JpaSpecificationExecutor<TdOrder> {
	Page<TdOrder> findByStatusIdOrderByIdDesc(Long statusId, Pageable page);

	Page<TdOrder> findByUsernameOrderByIdDesc(String username, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdNotOrderByIdDesc(String username, Long statusId, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(String username1,
			Long statusId1, String username2, Long statusId2, String username3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterOrderByIdDesc(String username, Date time, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username, Date time,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndOrderNumberContainingOrderByIdDesc(String username, String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdNotAndOrderNumberContainingOrderByIdDesc(String username, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByIdInAndOrderNumberContainingOrderByIdDesc(List<Long> orderids, String keywords, Pageable page);

	Page<TdOrder> findByIdInOrderByIdDesc(List<Long> orderids, Pageable page);

	Page<TdOrder> findByorderNumberContainingOrShippingAddressContainingOrShippingNameContainingOrUsernameContainingOrShippingPhoneContaining(
			String keywords, String keywords1, String keywords2, String keywords3, String keywords4, Pageable page);

	Page<TdOrder> findByorderNumberContainingAndStatusIdOrShippingAddressContainingAndStatusIdOrShippingNameContainingAndStatusIdOrUsernameContainingAndStatusIdOrShippingPhoneContainingAndStatusId(
			String keywords, Long statusId, String keywords1, Long statusId1, String keywords2, Long statusId2,
			String keywords3, Long statusId3, String keywords4, Long statusId4, Pageable page);

	/**
	 * @author lc @注释： 按时间、订单类型和订单状态查询
	 */
	Page<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time, Pageable page);

	List<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time);

	Page<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time,
			Pageable page);

	List<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time);

	Page<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time, Pageable page);

	List<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time);

	Page<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time, Pageable page);

	List<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time);

	// zhangji
	Page<TdOrder> findByUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrderByIdDesc(
			String username1, String keywords1, Long statusId1, String username2, String keywords2, Long statusId2,
			String username3, String keywords3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrderByIdDesc(
			String username1, String keywords1, Long statusId1, String username2, String keywords2, Long statusId2,
			String username3, String keywords3, Long statusId3, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrderByIdDesc(String username, Long statusId, Pageable page);

	// Page<TdOrder>
	// findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfter(String
	// orderNumber,Date time, String orderNumber2,Date time2, String
	// orderNumber3,Date time3, Pageable page);
	Page<TdOrder> findByUsernameAndIsCancelTrue(String username, Pageable page); // 取消订单
																					// zhangji

	Page<TdOrder> findByIsCancelTrue(Pageable page); // 取消订单 zhangji

	Page<TdOrder> findByIsCancelTrueAndIsRefundFalse(Pageable page); // 取消订单
																		// zhangji

	Page<TdOrder> findByIsCancelTrueAndIsRefundTrue(Pageable page); // 取消订单
																	// zhangji

	Page<TdOrder> findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(String username, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(String username, Long statusId, Date time,
			Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username,
			Long statusId, Date time, String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(String username, Long statusId1,
			Long statusId2, Long statusId3, Long statusId4, Pageable page); // zhangji

	Long countByUsernameAndStatusId(String username, Long statusId);

	List<TdOrder> findByStatusId(Long statusId);

	Long countByStatusId(Long statusId);

	TdOrder findByOrderNumber(String orderNumber);

	// Page<TdOrder>
	// findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(String
	// OrderNumber, Long statusId, Date time, String OrderNumber2, Long
	// statusId2, Date time2, String OrderNumber3, Long statusId3, Date time3,
	// Pageable page);

	/**
	 * 订单各状态排序
	 * 
	 * @author Max
	 * 
	 */
	Page<TdOrder> findByOrderNumberContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrPayTypeTitleContainingIgnoreCase(
			String orderNumber, String username, String payTypeTitle, Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrPayTypeTitleContainingIgnoreCaseOrderByIdDesc(
			String orderNumber, String username, String payTypeTitle, long type);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndTypeIdOrUsernameContainingIgnoreCaseAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndTypeId(
			String orderNumber, Long typeId1, String username, Long typeId2, String payTypeTitle, Long typeId3,
			Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdOrUsernameContainingIgnoreCaseAndStatusIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdOrderByIdDesc(
			String orderNumber, Long statusId1, String username, Long statusId2, String payTypeTitle, Long statusId3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdOrUsernameContainingIgnoreCaseAndStatusIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdOrderByIdDesc(
			String orderNumber, Long statusId1, String username, Long statusId2, String payTypeTitle, Long statusId3,
			Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdOrderByIdDesc(
			String orderNumber, Long statusId, Long typeId, String username, Long statusId2, Long typeId2,
			String payTypeTitle, Long statusId3, Long typeId3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeId(
			String orderNumber, Long statusId, Long typeId, String username, Long statusId2, Long typeId2,
			String payTypeTitle, Long statusId3, Long typeId3, Pageable pageRequest);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfterOrderByIdDesc(
			String orderNumber, Date time, String orderNumber2, Date time2, String orderNumber3, Date time3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfter(
			String orderNumber, Date time, String orderNumber2, Date time2, String orderNumber3, Date time3,
			Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrderByIdDesc(
			String orderNumber, Long typeId, Date time, String orderNumber2, Long typeId2, Date time2,
			String orderNumber3, Long typeId3, Date time3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndTypeIdAndOrderTimeAfter(
			String orderNumber, Long typeId, Date time, String orderNumber2, Long typeId2, Date time2,
			String orderNumber3, Long typeId3, Date time3, Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrderByIdDesc(
			String orderNumber, Long statusId, Date time, String orderNumber2, Long statusId2, Date time2,
			String orderNumber3, Long statusId3, Date time3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(
			String OrderNumber, Long statusId, Date time, String OrderNumber2, Long statusId2, Date time2,
			String OrderNumber3, Long statusId3, Date time3, Pageable page);

	List<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(
			String orderNumber, Long statusId, Long typeId, Date time, String orderNumber2, Long statusId2,
			Long typeId2, Date time2, String orderNumber3, Long statusId3, Long typeId3, Date time3);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfter(
			String orderNumber, Long statusId, Long typeId, Date time, String orderNumber2, Long statusId2,
			Long typeId2, Date time2, String orderNumber3, Long statusId3, Long typeId3, Date time3, Pageable page);

	// ==========================================================================================================
	// ==========================================================================================================
	/**
	 * 加截止时间筛选
	 * 
	 */
	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndOrderTimeBeforeOrUsernameContainingIgnoreCaseAndOrderTimeBefore(
			String ordernumber, Date orderTime1, String username, Date orderTime2, Pageable page);

	Page<TdOrder> findByOrderTimeBefore(Date orderTime, Pageable page);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeBeforeOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeBefore(
			String orderNumber, Long statusId1, Date endTime1, String username, Long statusId2, Date endTime2,
			Pageable page);

	Page<TdOrder> findByStatusIdAndOrderTimeBefore(Long statusId, Date endTime, Pageable page);

	// 加起始时间
	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfter(
			String ordernumber, Date orderTime1, String username, Date orderTime2, Pageable page);

	Page<TdOrder> findByOrderTimeAfter(Date orderTime, Pageable page);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(
			String orderNumber, Long statusId1, Date endTime1, String username, Long statusId2, Date endTime2,
			Pageable page);

	Page<TdOrder> findByStatusIdAndOrderTimeAfter(Long statusId, Date orderTime, Pageable page);

	// 起始时间+截止时间
	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndOrderTimeBetweenOrUsernameContainingIgnoreCaseAndOrderTimeBetween(
			String ordernumber, Date startTime1, Date endTime1, String username, Date startTime2, Date endTime2,
			Pageable page);

	Page<TdOrder> findByOrderTimeBetween(Date startTime, Date endTime, Pageable page);

	Page<TdOrder> findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeBetweenOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeBetween(
			String orderNumber, Long statusId1, Date startTime1, Date endTime1, String username, Long statusId2,
			Date startTime2, Date endTime2, Pageable page);

	Page<TdOrder> findByStatusIdAndOrderTimeBetween(Long statusId, Date startTime, Date endTime, Pageable page);

	Page<TdOrder> findByIdIn(List<Long> orderId, Pageable page);

	@Query("select count(0) from TdOrder o where o.username = ?1 and o.statusId = ?2")
	Long findCountByUsernameAndStatusId(String username, Long statusId);

	/**
	 * 查询指定用户的所有订单（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午3:31:05
	 */
	Page<TdOrder> findByUsernameOrderByOrderTimeDesc(String username, Pageable page);

	/**
	 * 查询指定用户的指定状态的订单（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午3:32:04
	 */
	Page<TdOrder> findByUsernameAndStatusIdOrderByOrderTimeDesc(String username, Long statusId, Pageable page);
}
