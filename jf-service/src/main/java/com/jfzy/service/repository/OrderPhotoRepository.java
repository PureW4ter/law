package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.OrderPhotoPo;

public interface OrderPhotoRepository extends JpaRepository<OrderPhotoPo, Integer> {
	List<OrderPhotoPo> findByOrderIdAndType(int orderId, int type);
}
