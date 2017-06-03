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
	@Query("UPDATE OrderPo SET memo=?1, updateTime=?2 WHERE id=?3")
	void updateMemo(String memo, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET startTime=?1, endTime=?2 , updateTime=?3 WHERE id=?4")
	void setStartAndEndTime(Timestamp startTime, Timestamp endTime, Timestamp updateTime, int id);

	Page<OrderPo> findByUserId(int userId, Pageable page);

	OrderPo findByUserIdAndId(int userId, int id);

}