package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.ArticlePo;

public interface ArticleRepository extends JpaRepository<ArticlePo, Integer> {

	@Query("SELECT t FROM ArticlePo t WHERE t.deleted=0")
	List<ArticlePo> findNotDeleted();

	@Query(value = "SELECT t FROM ArticlePo t WHERE t.deleted=0 and t.updateTime>?1")
	List<ArticlePo> findNotDeleted(Timestamp lastDatetime);
	
	@Query(value = "SELECT t FROM ArticlePo t WHERE t.id=?1 and t.deleted=0")
	ArticlePo getById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE ArticlePo SET deleted=1 WHERE id=:id")
	void updateDeleted(@Param("id") int id);

}
