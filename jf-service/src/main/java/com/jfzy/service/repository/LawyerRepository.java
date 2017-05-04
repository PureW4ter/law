package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.LawyerPo;

public interface LawyerRepository extends JpaRepository<LawyerPo, Integer> {

	List<LawyerPo> findByCityIdAndStatus(int cityId, int status);

}
