package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfzy.service.ArticleService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.repository.ArticleElasticRepository;
import com.jfzy.service.repository.ArticleRepository;

@Component
@EnableScheduling
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepo;

	@Autowired
	private ArticleElasticRepository articleElasticRepo;

	@PostConstruct
	public void init() {
		System.out.println("bbb");
		articleElasticRepo.save(new ArticleBo(1, "a", "b", "c", new String[] { "a", "c" }));

		articleElasticRepo.save(new ArticleBo(2, "b", "c", "d", new String[] { "a", "b" }));
	}

	@Scheduled(fixedRate = 50000)
	public void retriveArticles() {
	}

	@Override
	public List<ArticleBo> searchByTags(List<String> tags, Pageable page) {
		
		SearchQuery sq = new NativeSearchQueryBuilder().withQuery(new MatchAllQueryBuilder())
				.withFilter(new TermsQueryBuilder("tags", tags.iterator())).build();

		Iterable<ArticleBo> values = articleElasticRepo.search(sq);
		List<ArticleBo> results = new ArrayList<ArticleBo>();
		values.forEach(bo -> results.add(bo));

		return results;
	}

}
