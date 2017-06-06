package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.LawyerReplyPo;

public interface LawyerReplyRepository extends JpaRepository<LawyerReplyPo, Integer> {

	List<LawyerReplyPo> findByOrderId(int orderId);

}