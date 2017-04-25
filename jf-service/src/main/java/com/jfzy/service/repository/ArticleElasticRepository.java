package com.jfzy.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.jfzy.service.bo.ArticleBo;

public interface ArticleElasticRepository extends ElasticsearchRepository<ArticleBo, Integer> {

	@Query("{terms:{tags:?0}}")
	Page<ArticleBo> findByTagsContaining(String tags, Pageable pageable);
}
