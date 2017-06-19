package com.jfzy.service.repository;

import java.sql.Timestamp;

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
	@Query("UPDATE OrderPo SET startTime=?1, endTime=?2 , updateTime=?3 WHERE id=?4")
	void setStartAndEndTime(Timestamp startTime, Timestamp endTime, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET status=?3 WHERE id=?! AND status=?2")
	int updateStatus(int id, int oldStatus, int newStatus);

	Page<OrderPo> findByUserId(int userId, Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.productCode='H' or t.productCode='C'")
	Page<OrderPo> getSearchOrders(Pageable page);

	@Query(value = "SELECT t FROM OrderPo t WHERE t.productCode='Y' or t.productCode='YP' or t.productCode='J'")
	Page<OrderPo> getInvestOrders(Pageable page);

	OrderPo findByUserIdAndId(int userId, int id);

	int countByCityIdAndStatus(int cityId, int status);

	Page<OrderPo> findByStatus(int status, Pageable page);

}