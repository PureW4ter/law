package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.ArticleBo;

public interface ArticleService {

	List<ArticleBo> searchByTags(List<String> tags, Pageable page);
}
