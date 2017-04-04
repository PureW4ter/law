package com.jfzy.service.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.TagPo;

public interface TagRepository extends JpaRepository<TagPo, Integer> {

	@Query("SELECT t FROM TagPo t WHERE t.deleted=0")
	List<TagPo> findAll();

	@Transactional
	@Modifying
	@Query("UPDATE TagPo SET deleted=1 WHERE id=:id")
	void updateDeleted(@Param("id") int id);
}
