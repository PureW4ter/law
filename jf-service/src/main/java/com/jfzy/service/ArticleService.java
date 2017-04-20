package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.ArticleBo;

public interface ArticleService {

	List<ArticleBo> searchByTags(List<String> tags);
}
