package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.ArticlePo;
import com.jfzy.service.po.TagPo;

public interface ArticleRepository extends JpaRepository<ArticlePo, Integer> {

	
	@Query("SELECT t FROM ArticlePo t WHERE t.deleted=0")
	List<TagPo> findNotDeleted();
	
}
