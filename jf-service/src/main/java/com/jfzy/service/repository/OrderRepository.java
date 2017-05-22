package com.jfzy.service.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.ArticlePo;
import com.jfzy.service.po.OrderPo;

public interface OrderRepository extends JpaRepository<OrderPo, Integer> {

	Page<OrderPo> findByCityIdAndStatus(int cityId, int status, Pageable page);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET status=?1 WHERE id=?2")
	void updateStatus(int status, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OrderPo SET payWay=?1 WHERE id=?2")
	void updatePayWay(int payWay, int id);
	
	
	Page<OrderPo> findByUserId(int userId, Pageable page);

}