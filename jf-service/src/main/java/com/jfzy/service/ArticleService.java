package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.ArticleBo;

public interface ArticleService {
	List<ArticleBo> searchByTags(String tags, int page, int size);

	List<ArticleBo> getQAs(Pageable page);

	ArticleBo get(int id);

	void create(ArticleBo bo);

	void delete(int id);
}
