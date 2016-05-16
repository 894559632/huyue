package com.ynyes.huyue.service;

import java.util.ArrayList;
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

import com.ynyes.huyue.entity.TdOrder;
import com.ynyes.huyue.entity.TdOrderGoods;
import com.ynyes.huyue.repository.TdOrderRepo;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderService {
	@Autowired
	TdOrderRepo repository;

	@Autowired
	TdOrderGoodsService tdOrderGoodsService;

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
	public void delete(TdOrder e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdOrder> entities) {
		if (null != entities) {
			repository.delete(entities);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdOrder findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * @author lc
	 * @注释：搜索
	 */
	public Page<TdOrder> searchAll(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository
				.findByorderNumberContainingOrShippingAddressContainingOrShippingNameContainingOrUsernameContainingOrShippingPhoneContaining(
						keywords, keywords, keywords, keywords, keywords, pageRequest);
	}

	public Page<TdOrder> searchByStatusId(String keywords, Long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository
				.findByorderNumberContainingAndStatusIdOrShippingAddressContainingAndStatusIdOrShippingNameContainingAndStatusIdOrUsernameContainingAndStatusIdOrShippingPhoneContainingAndStatusId(
						keywords, statusId, keywords, statusId, keywords, statusId, keywords, statusId, keywords,
						statusId, pageRequest);
	}

	/**
	 * 查找
	 * 
	 * @param ids
	 * @return
	 */

	/**
	 * @author lc
	 * @注释 按时间、订单类型和订单状态查询
	 */
	public Page<TdOrder> findByTimeAfterOrderByIdDesc(Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByOrderTimeAfterOrderByIdDesc(time, pageRequest);
	}

	public List<TdOrder> findByTimeAfterOrderByIdDesc(Date time) {
		return repository.findByOrderTimeAfterOrderByIdDesc(time);
	}

	public Page<TdOrder> findByStatusIdAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time, pageRequest);
	}

	public List<TdOrder> findByStatusAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time) {
		return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time);
	}

	public Page<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time, pageRequest);
	}

	public List<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time) {
		return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time);
	}

	public Page<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time, pageRequest);
	}

	public List<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time) {
		return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time);
	}

	public List<TdOrder> findAll(Iterable<Long> ids) {
		return (List<TdOrder>) repository.findAll(ids);
	}

	public List<TdOrder> findAll() {
		return (List<TdOrder>) repository.findAll();
	}

	public Page<TdOrder> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdOrder> findAllOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findAll(pageRequest);
	}

	public Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByStatusIdOrderByIdDesc(statusId, pageRequest);
	}

	public Page<TdOrder> findByUsername(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdNot(String username, Long StatusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdNotOrderByIdDesc(username, StatusId, pageRequest);
		// return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(
			String username1, Long statusId1, String username2, Long statusId2, String username3, Long statusId3,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(username1,
				4L, username2, 5L, username3, 6L, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(String username, Long statusId1,
			Long statusId2, Long statusId3, Long statusId4, Integer page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndStatusIdOrStatusIdOrStatusIdOrStatusId(username, 3L, 4L, 6L, 7L,
				pageRequest);
	}

	public TdOrder findByOrderNumber(String orderNumber) {
		return repository.findByOrderNumber(orderNumber);
	}

	public Page<TdOrder> findByUsernameAndTimeAfter(String username, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterOrderByIdDesc(username, time, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndTimeAfterAndSearch(String username, Date time, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, time, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndSearch(String username, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderNumberContainingOrderByIdDesc(username, keywords, pageRequest);
	}

	public Page<TdOrder> findByisComplainedByusernameAndSearch(List<Long> orderids, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIdInAndOrderNumberContainingOrderByIdDesc(orderids, keywords, pageRequest);
	}

	public Page<TdOrder> findByisComplainedByusername(List<Long> orderids, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIdInOrderByIdDesc(orderids, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdNotAndSearch(String username, Long StatusId, String keywords,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdNotAndOrderNumberContainingOrderByIdDesc(username, StatusId,
				keywords, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusId(String username, long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdOrderByIdDesc(username, statusId, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndSearchAndStatusIdOrUsernameAndSearchAndStatusIdOrUsernameAndSearchAndStatusId(
			String username1, String keywords1, Long statuisId1, String username2, String keywords2, Long statuisId2,
			String username3, String keywords3, Long statuisId3, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrUsernameAndOrderNumberContainingAndStatusIdOrderByIdDesc(
						username1, keywords1, 4L, username2, keywords2, 5L, username3, keywords3, 6L, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusId(
			String username1, String keywords1, Long statuisId1, String username2, String keywords2, Long statuisId2,
			String username3, String keywords3, Long statuisId3, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository
				.findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrderByIdDesc(
						username1, keywords1, 4L, username2, keywords2, 5L, username3, keywords3, 6L, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByUsernameAndIsCancelTrue(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndIsCancelTrue(username, pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrue(pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrueAndIsRefundFalse(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrueAndIsRefundFalse(pageRequest);
	}

	// zhangji
	public Page<TdOrder> findByIsCancelTrueAndIsRefundTrue(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByIsCancelTrueAndIsRefundTrue(pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndSearch(String username, long statusId, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(username, statusId, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfter(String username, long statusId, Date time, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(username, statusId, time,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfterAndSearch(String username, long statusId, Date time,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username,
				statusId, time, keywords, pageRequest);
	}

	public Long countByUsernameAndStatusId(String username, long statusId) {
		return repository.countByUsernameAndStatusId(username, statusId);
	}

	public List<TdOrder> findByStatusId(Long statusId) {
		return repository.findByStatusId(statusId);
	}

	public Long countByStatusId(Long statusId) {
		return repository.countByStatusId(statusId);
	}

	// public Page<TdOrder> searchByOrderNumberAndTimeAfterOrderByIdDesc(String
	// orderNumber,Date time, int page, int size)
	// {
	// if(null == orderNumber){
	// return null;
	// }
	// PageRequest pageRequest = new PageRequest(page, size, new
	// Sort(Direction.DESC, "id"));
	//
	// return
	// repository.findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfter(orderNumber,
	// time, orderNumber, time, orderNumber, time, pageRequest);
	// }
	//
	// public Page<TdOrder>
	// searchByOrderNumberAndStatusAndTimeAfterOrderByIdDesc(String
	// orderNumber,long StatusId, Date time, int page, int size){
	// if(null == orderNumber){
	// return null;
	// }
	// PageRequest pageRequest = new PageRequest(page, size, new
	// Sort(Direction.DESC, "id"));
	// return
	// repository.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(orderNumber,StatusId,
	// time, orderNumber,StatusId, time, orderNumber,StatusId, time,
	// pageRequest);
	// }
	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdOrder save(TdOrder e) {

		return repository.save(e);
	}

	public List<TdOrder> save(List<TdOrder> entities) {

		return (List<TdOrder>) repository.save(entities);
	}

	/**
	 * 订单各类型排序
	 * 
	 * @author Max
	 * 
	 */

	public Page<TdOrder> searchByOrderNumber(String orderNumber, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrPayTypeTitleContainingIgnoreCase(
						orderNumber, orderNumber, orderNumber, pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndTypeIdOrderByIdDesc(String keywords, Long type) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrPayTypeTitleContainingIgnoreCaseOrderByIdDesc(
						keywords, keywords, keywords, type);
	}

	public Page<TdOrder> searchByOrderNumberAndTypeIdOrderByIdDesc(String keywords, Long type, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndTypeIdOrUsernameContainingIgnoreCaseAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndTypeId(
						keywords, type, keywords, type, keywords, type, pageRequest);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusOrderByIdDesc(String keywords, long StatusId, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdOrUsernameContainingIgnoreCaseAndStatusIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdOrderByIdDesc(
						keywords, StatusId, keywords, StatusId, keywords, StatusId, pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndStatusOrderByIdDesc(String keywords, long StatusId) {
		if (null == keywords) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdOrUsernameContainingIgnoreCaseAndStatusIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdOrderByIdDesc(
						keywords, StatusId, keywords, StatusId, keywords, StatusId);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusAndTypeOrderByIdDesc(String orderNumber, long statusId,
			long typeId, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeId(
						orderNumber, statusId, typeId, orderNumber, statusId, typeId, orderNumber, statusId, typeId,
						pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndStatusAndTypeIdOrderByIdDesc(String orderNumber, long statusId,
			long typeId) {
		if (null == orderNumber) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdOrderByIdDesc(
						orderNumber, statusId, typeId, orderNumber, statusId, typeId, orderNumber, statusId, typeId);
	}

	public Page<TdOrder> searchByOrderNumberAndTimeAfterOrderByIdDesc(String orderNumber, Date time, int page,
			int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository
				.findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfter(
						orderNumber, time, orderNumber, time, orderNumber, time, pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndTimeAfterOrderByIdDesc(String orderNumber, Date time) {
		if (null == orderNumber) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndOrderTimeAfterOrderByIdDesc(
						orderNumber, time, orderNumber, time, orderNumber, time);
	}

	public Page<TdOrder> searchByOrderNumberAndtypeIdAndTimeAfterOrderByIdDesc(String orderNumber, long typeId,
			Date time, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndTypeIdAndOrderTimeAfter(
						orderNumber, typeId, time, orderNumber, typeId, time, orderNumber, typeId, time, pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndtypeIdAndTimeAfterOrderByIdDesc(String orderNumber, long typeId,
			Date time) {
		if (null == orderNumber) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndTypeIdAndOrderTimeAfterOrderByIdDesc(
						orderNumber, typeId, time, orderNumber, typeId, time, orderNumber, typeId, time);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusAndTimeAfterOrderByIdDesc(String orderNumber, long StatusId,
			Date time, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(
						orderNumber, StatusId, time, orderNumber, StatusId, time, orderNumber, StatusId, time,
						pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndStatusAndTimeAfterOrderByIdDesc(String orderNumber, long StatusId,
			Date time) {
		if (null == orderNumber) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrderByIdDesc(
						orderNumber, StatusId, time, orderNumber, StatusId, time, orderNumber, StatusId, time);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusIdAndTypeIdAndTimeAfterOrderByIdDesc(String orderNumber,
			long statusId, long typeId, Date time, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfter(
						orderNumber, statusId, typeId, time, orderNumber, statusId, typeId, time, orderNumber, statusId,
						typeId, time, pageRequest);
	}

	public List<TdOrder> searchByOrderNumberAndStatusAndTypeIdAndTimeAfterOrderByIdDesc(String orderNumber,
			long statusId, long typeId, Date time) {
		if (null == orderNumber) {
			return null;
		}
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrPayTypeTitleContainingIgnoreCaseAndStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(
						orderNumber, statusId, typeId, time, orderNumber, statusId, typeId, time, orderNumber, statusId,
						typeId, time);
	}

	// ============================================================================================================================
	// ============================================================================================================================
	/**
	 * 
	 * 增加结尾时间的筛选
	 * 
	 */
	public Page<TdOrder> searchByOrderNumberAndOrderTimeBefore(String orderNumber, Date endTime, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndOrderTimeBeforeOrUsernameContainingIgnoreCaseAndOrderTimeBefore(
						orderNumber, endTime, orderNumber, endTime, pageRequest);
	}

	public Page<TdOrder> findByOrderTimeBeforeOrderByIdDesc(Date orderTime, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByOrderTimeBefore(orderTime, pageRequest);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusAndOrderTimeBeforeOrderByIdDesc(String keywords, long StatusId,
			Date endTime, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeBeforeOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeBefore(
						keywords, StatusId, endTime, keywords, StatusId, endTime, pageRequest);
	}

	public Page<TdOrder> findByStatusAndOrderTimeBeforeOrderByIdDesc(long StatusId, Date endTime, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByStatusIdAndOrderTimeBefore(StatusId, endTime, pageRequest);
	}

	// ============================================================================================================================
	// ============================================================================================================================
	/**
	 * 
	 * 增加起始时间的筛选
	 * 
	 */
	public Page<TdOrder> searchByOrderNumberAndOrderTimeAfter(String orderNumber, Date startTime, int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndOrderTimeAfter(
						orderNumber, startTime, orderNumber, startTime, pageRequest);
	}

	public Page<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date orderTime, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByOrderTimeAfter(orderTime, pageRequest);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusAndOrderTimeAfterOrderByIdDesc(String keywords, long StatusId,
			Date orderTime, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeAfterOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeAfter(
						keywords, StatusId, orderTime, keywords, StatusId, orderTime, pageRequest);
	}

	public Page<TdOrder> findByStatusAndOrderTimeAfterOrderByIdDesc(long StatusId, Date orderTime, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByStatusIdAndOrderTimeAfter(StatusId, orderTime, pageRequest);
	}

	// ↓↓↓↓↓↓↓ 起始时间+截止时间 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	public Page<TdOrder> searchByOrderNumberAndOrderTimeDetween(String orderNumber, Date startTime, Date endTime,
			int page, int size) {
		if (null == orderNumber) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndOrderTimeBetweenOrUsernameContainingIgnoreCaseAndOrderTimeBetween(
						orderNumber, startTime, endTime, orderNumber, startTime, endTime, pageRequest);
	}

	public Page<TdOrder> findByOrderTimeBetweenOrderByIdDesc(Date startTime, Date endTime, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByOrderTimeBetween(startTime, endTime, pageRequest);
	}

	public Page<TdOrder> searchByOrderNumberAndStatusAndOrderTimeBetweenOrderByIdDesc(String keywords, long StatusId,
			Date startTime, Date endTime, int page, int size) {
		if (null == keywords) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository
				.findByOrderNumberContainingIgnoreCaseAndStatusIdAndOrderTimeBetweenOrUsernameContainingIgnoreCaseAndStatusIdAndOrderTimeBetween(
						keywords, StatusId, startTime, endTime, keywords, StatusId, startTime, endTime, pageRequest);
	}

	public Page<TdOrder> findByStatusAndOrderTimeBetweenOrderByIdDesc(long StatusId, Date startTime, Date endTime,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
		return repository.findByStatusIdAndOrderTimeBetween(StatusId, startTime, endTime, pageRequest);
	}

	/**
	 * @author lc
	 * @注释：增加商品类别的筛选
	 */

	// 通过商品类别id查询出订单id列表
	public List<Long> findOrderByCategoryId(Long categoryId) {
		List<TdOrderGoods> tdOrderGoodsList = tdOrderGoodsService.findByCategoryId(categoryId);

		if (null != tdOrderGoodsList) {
			List<Long> orderIdList = new ArrayList<>();

			Long tdorderIdTemp;
			for (TdOrderGoods tdOrderGoods : tdOrderGoodsList) {

				tdorderIdTemp = tdOrderGoodsService.getOrderId(tdOrderGoods.getId());
				if (null != tdorderIdTemp && !orderIdList.contains(tdorderIdTemp)) {
					orderIdList.add(tdorderIdTemp);
				}
			}

			return orderIdList;
		}
		return null;
	}

	public Page<TdOrder> findByCategoryId(Long categoryId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		List<Long> orderIdList = findOrderByCategoryId(categoryId);

		if (null != orderIdList && !orderIdList.isEmpty()) {
			return repository.findByIdIn(orderIdList, pageRequest);
		}

		return null;

	}

	public Long findCountByUsernameAndStatusId(String username, Long statusId) {
		if (null == username || null == statusId) {
			return null;
		}
		return repository.findCountByUsernameAndStatusId(username, statusId);
	}

	/**
	 * 查询指定用户的所有订单（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午3:31:05
	 */
	public Page<TdOrder> findByUsernameOrderByOrderTimeDesc(String username, int page, int size) {
		if (null == username) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameOrderByOrderTimeDesc(username, pageRequest);
	}

	/**
	 * 查询指定用户的指定状态的订单（分页）
	 * 
	 * @author 作者：DengXiao
	 * @version 版本：20162016年5月16日下午3:32:04
	 */
	public Page<TdOrder> findByUsernameAndStatusIdOrderByOrderTimeDesc(String username, Long statusId, int page,
			int size) {
		if (null == username || null == statusId) {
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, statusId, pageRequest);
	}

}
