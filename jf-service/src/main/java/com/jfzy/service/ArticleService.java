package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.ArticleBo;

public interface ArticleService {

	List<ArticleBo> searchByTags(String[] tags, Pageable page);

	void createArticle(ArticleBo bo);
}
