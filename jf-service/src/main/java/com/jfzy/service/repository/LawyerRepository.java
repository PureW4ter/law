package com.jfzy.service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.LawyerPo;

public interface LawyerRepository extends JpaRepository<LawyerPo, Integer> {

	List<LawyerPo> findByCityIdAndStatus(int cityId, int status);

	List<LawyerPo> findByCityId(int cityId);

	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET status=:status WHERE id=:id")
	void updateStatus(int id, int status);

}
