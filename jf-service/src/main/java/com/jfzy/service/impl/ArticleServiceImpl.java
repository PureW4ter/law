package com.jfzy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jfzy.service.ArticleService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	@Scheduled(fixedRate = 500)
	public void retriveArticles() {
		System.out.println("aaa");
	}

	@Override
	public List<ArticleBo> searchByTags(List<String> tags) {
		return null;
	}

}
