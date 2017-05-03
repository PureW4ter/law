package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfzy.service.ArticleService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.po.ArticlePo;
import com.jfzy.service.repository.ArticleElasticRepository;
import com.jfzy.service.repository.ArticleRepository;

@Component
@EnableScheduling
public class ArticleServiceImpl implements ArticleService {

	private static final char TAG_SEPARATOR = ',';

	@Autowired
	private ArticleRepository articleRepo;

	@Autowired
	private ArticleElasticRepository articleElasticRepo;

	private Timestamp lastUpdatedTime = new Timestamp(0);

	@PostConstruct
	public void init() {
		retriveArticles();
	}

	@Scheduled(fixedRate = 60000)
	public void retriveArticles() {
		List<ArticlePo> articlePos = articleRepo.findNotDeleted(lastUpdatedTime);
		Timestamp tmpTimestamp = lastUpdatedTime;
		for (ArticlePo po : articlePos) {
			if (po.getUpdateTime() != null && po.getUpdateTime().after(tmpTimestamp)) {
				tmpTimestamp = po.getUpdateTime();
			}
			articleElasticRepo.save(po2Bo(po));
		}
		lastUpdatedTime = tmpTimestamp;
	}

	@Override
	public List<ArticleBo> searchByTags(String[] tags, Pageable page) {

		if (tags != null && tags.length > 0) {
			QueryBuilder qb = QueryBuilders.termsQuery("tags", tags);
			Iterable<ArticleBo> values = articleElasticRepo.search(qb, page);
			List<ArticleBo> results = new ArrayList<ArticleBo>();
			values.forEach(bo -> results.add(bo));

			return results;
		} else {
			Iterable<ArticleBo> values = articleElasticRepo.findAll(page);
			List<ArticleBo> results = new ArrayList<ArticleBo>();
			values.forEach(bo -> results.add(bo));
			return results;
		}
	}

	@Override
	public ArticleBo getArticle(int id) {
		ArticlePo po = articleRepo.getById(id);
		return po2Bo(po);
	}
	
	@Override
	public void createArticle(ArticleBo bo) {
		ArticlePo po = bo2Po(bo);
		articleRepo.save(po);
	}

	private static ArticlePo bo2Po(ArticleBo bo) {
		ArticlePo po = new ArticlePo();
		po.setContent(bo.getContent());
		po.setTags(getTagString(bo.getTags()));
		po.setTitle(bo.getTitle());
		po.setTitleImgUrl(bo.getTitleImgUrl());
		po.setShareIconUrl(bo.getShareIconUrl());
		po.setSummary(bo.getSummary());
		return po;
	}

	private static ArticleBo po2Bo(ArticlePo po) {
		ArticleBo result = new ArticleBo();
		result.setId(po.getId());
		result.setTitle(po.getTitle());
		result.setTitleImgUrl(po.getTitleImgUrl());
		result.setShareIconUrl(po.getShareIconUrl());
		result.setContent(po.getContent());
		result.setTags(getTags(po.getTags()));
		result.setCreateTime(po.getCreateTime());
		result.setSummary(po.getSummary());
		return result;
	}

	private static String[] getTags(String tagsString) {
		if (StringUtils.isNotEmpty(tagsString)) {
			String[] tags = StringUtils.split(tagsString, TAG_SEPARATOR);
			return tags;
		} else {
			return new String[0];
		}
	}

	private static String getTagString(String[] tags) {
		return StringUtils.join(tags, TAG_SEPARATOR);
	}

}