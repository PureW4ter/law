package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.ArticlePo;

public interface ArticleRepository extends JpaRepository<ArticlePo, Integer> {

	@Query("SELECT t FROM ArticlePo t WHERE t.deleted=0 and t.type=?1")
	List<ArticlePo> findNotDeleted(int type);

	@Query(value = "SELECT t FROM ArticlePo t WHERE t.deleted=0 and t.type=?2 and t.updateTime>?1")
	List<ArticlePo> findNotDeleted(Timestamp lastDatetime, int type);
	
	@Query(value = "SELECT t FROM ArticlePo t WHERE t.deleted=0")
	Page<ArticlePo> getArticles(Pageable page);
	
	@Query(value = "SELECT t FROM ArticlePo t WHERE t.type=?2 and t.updateTime>?1")
	List<ArticlePo> findUpdates(Timestamp lastDatetime, int type);

	@Query(value = "SELECT t FROM ArticlePo t WHERE t.id=?1 and t.deleted=0")
	ArticlePo getById(int id);

	Page<ArticlePo> findByType(int type, Pageable page);

	@Transactional
	@Modifying
	@Query("UPDATE ArticlePo SET deleted=1 WHERE id=:id")
	void updateDeleted(@Param("id") int id);

}
