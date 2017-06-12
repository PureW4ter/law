package com.jfzy.service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.LawyerReplyPo;

public interface LawyerReplyRepository extends JpaRepository<LawyerReplyPo, Integer> {

	List<LawyerReplyPo> findByOrderId(int orderId);

	@Transactional
	@Modifying
	@Query("UPDATE LawyerReplyPo SET status=?1, score=?2 WHERE id=?3")
	void updateScore(int status, int score, int id);

}