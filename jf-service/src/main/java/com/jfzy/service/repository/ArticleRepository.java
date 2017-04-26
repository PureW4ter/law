package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.ArticlePo;

public interface ArticleRepository extends JpaRepository<ArticlePo, Integer> {

	@Query("SELECT t FROM ArticlePo t WHERE t.deleted=0")
	List<ArticlePo> findNotDeleted();

	@Query(value = "SELECT t FROM ArticlePo t WHERE t.deleted=0 and t.updateTime>?1")
	List<ArticlePo> findNotDeleted(Timestamp lastDatetime);

}
