package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.OrderPo;

public interface OrderRepository extends JpaRepository<OrderPo, Integer> {

	Page<OrderPo> findByCityIdAndStatus(int cityId, int status, Pageable page);

	Page<OrderPo> findByLawyerId(int lawyerId, Pageable page);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET status=?1, updateTime=?2 WHERE id=?3")
	void updateStatus(int status, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET payWay=?1, updateTime=?2 WHERE id=?3")
	void updatePayWay(int payWay, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET pay_status=?1, status=?2 WHERE id=?3")
	void updatePayStatusAndStatus(int payStatus, int status, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET memo=?1, updateTime=?2 WHERE id=?3")
	void updateMemo(String memo, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET startTime=?1, endTime=?2 , phoneEndTime=?3, updateTime=?4 WHERE id=?5")
	void setStartAndEndTime(Timestamp startTime, Timestamp endTime, Timestamp phoneEndTime, Timestamp updateTime,
			int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET status=?3 WHERE id=?1 AND status=?2")
	int updateStatus(int id, int oldStatus, int newStatus);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET status=?2 WHERE id=?1")
	int updateStatus(int id, int newStatus);

	Page<OrderPo> findByUserId(int userId, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.productCode='H' or t.productCode='C' or t.productCode='HX'")
	Page<OrderPo> getSearchOrders(Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.productCode='Y' or t.productCode='YP' or t.productCode='J'")
	Page<OrderPo> getInvestOrders(Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE (t.productCode='H' or t.productCode='C' or t.productCode='HX') and t.userId=?1")
	Page<OrderPo> getSearchOrdersById(int userId, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE (t.productCode='Y' or t.productCode='YP' or t.productCode='J') and t.userId=?1")
	Page<OrderPo> getInvestOrdersById(int userId, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE (t.productCode='H' or t.productCode='C' or t.productCode='HX') and t.lawyerId=?1 and status=?2")
	Page<OrderPo> getSearchOrdersByLawyerId(int lawyerId, int status, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE (t.productCode='Y' or t.productCode='YP' or t.productCode='J') and t.lawyerId=?1 and status=?2")
	Page<OrderPo> getInvestOrdersByLawyerId(int lawyerId, int status, Pageable page);

	@Query(value = "SELECT count(*) FROM OrderPo t WHERE t.userId=?1")
	int getTotal(int userId);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.cityId=?1 AND t.status=?2 ORDER BY -t.phoneEndTime DESC,t.endTime ASC")
	Page<OrderPo> findUncompletedOrderByCityIdAndStatusOrderByAsc(int cityId, int status, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.cityId=?1 AND t.status<90 AND t.status>0 ORDER BY -t.phoneEndTime DESC,t.endTime ASC")
	Page<OrderPo> findUncompletedOrdersByCityIdOrderByAsc(int cityId, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.cityId=?1 AND t.status=?2 ORDER BY t.endTime DESC")
	Page<OrderPo> findCompletedByCityIdAndStatusOrderByDesc(int cityId, int status, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.cityId=?1 AND t.status>=90 ORDER BY t.endTime DESC")
	Page<OrderPo> findCompletedByCityIdOrderByDesc(int cityId, Pageable page);

	OrderPo findByUserIdAndId(int userId, int id);

	int countByCityIdAndStatus(int cityId, int status);

	Page<OrderPo> findByStatus(int status, Pageable page);

	List<OrderPo> findByOrderNum(String orderNum);

	@Query(value = "SELECT t FROM orderPo t WHERE t.status = 4 AND t.notifyStatus = 0")
	Page<OrderPo> findUnNotifiedOrders(int size, Pageable page);

}