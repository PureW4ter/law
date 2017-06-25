package com.jfzy.service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.IdPo;

public interface IdRepository extends JpaRepository<IdPo, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE IdPo SET currentId=currentId+1 WHERE id=?1 AND currentId=?2")
	int updateCurrentId(int id, int oldValue);

	List<IdPo> findByTypeAndPage(int type, int page);
}
