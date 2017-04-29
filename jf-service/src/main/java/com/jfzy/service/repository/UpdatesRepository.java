package com.jfzy.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.UpdatePo;

public interface UpdatesRepository extends JpaRepository<UpdatePo, Integer> {

	@Query("SELECT t FROM UpdatePo t WHERE t.type=?1 ORDER BY t.createTime DESC")
	Page<UpdatePo> findAll(int type, Pageable page);

}
