package com.jfzy.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.OrderPo;

public interface OrderRepository extends JpaRepository<OrderPo, Integer> {

	Page<OrderPo> findByCityIdAndStatus(int cityId, int status, Pageable page);

}