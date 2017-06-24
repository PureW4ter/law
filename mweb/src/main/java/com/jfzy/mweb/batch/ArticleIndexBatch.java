package com.jfzy.mweb.batch;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfzy.service.ArticleService;

@EnableScheduling
@Component
public class ArticleIndexBatch {

	@Autowired
	private ArticleService articleService;

	@PostConstruct
	public void init() {
		refresh();
	}

	@Scheduled(fixedRate = 60000)
	public void refresh() {
		articleService.retriveArticles();
	}

}
