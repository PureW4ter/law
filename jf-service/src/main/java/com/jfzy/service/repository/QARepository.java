package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.QAPo;

public interface QARepository extends JpaRepository<QAPo, Integer> {

	List<QAPo> findByCityIdAndStatus(int cityId, int status);

	List<QAPo> findByUserId(int userId);

	@Query("UPDATE QAPo SET status=:status WHERE id in (:ids)")
	void updateStatus(int status, int[] ids);

}
