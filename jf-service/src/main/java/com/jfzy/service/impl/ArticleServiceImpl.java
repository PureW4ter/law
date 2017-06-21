package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jfzy.service.ArticleService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.bo.ArticleTypeEnum;
import com.jfzy.service.po.ArticlePo;
import com.jfzy.service.repository.ArticleElasticRepository;
import com.jfzy.service.repository.ArticleRepository;

@EnableScheduling
@Service
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
		List<ArticlePo> articlePos = articleRepo.findNotDeleted(lastUpdatedTime, ArticleTypeEnum.ARTICLE.getId());
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
	public List<ArticleBo> searchByTags(String tags, int pageIndex, int size) {

		Sort sort = new Sort(Direction.DESC, "_score");
		sort.and(new Sort(Direction.DESC, "createTime"));
		Pageable page = new PageRequest(pageIndex, size, sort);

		if (tags != null && tags.length() > 0) {
			QueryBuilder qb = QueryBuilders.termsQuery("tags", tags.split(","));
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
	public List<ArticleBo> getQAs(Pageable page) {
		Page<ArticlePo> poPage = articleRepo.findByType(ArticleTypeEnum.QA.getId(), page);
		List<ArticleBo> results = new ArrayList<ArticleBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(po2Bo(po)));
		}
		return results;
	}

	@Override
	public ArticleBo get(int id) {
		ArticlePo po = articleRepo.getById(id);
		return po2Bo(po);
	}

	@Override
	public void create(ArticleBo bo) {
		ArticlePo po = bo2Po(bo);
		po.setContent(po.getContent().
				replaceAll("http://read\\.html5\\.qq\\.com/image\\?src=forum&q=5&r=0&imgflag=7&imageUrl=", "").
				replaceAll("http://mmbiz\\.qpic\\.cn", "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=http://mmbiz.qpiwc.cn/"));
		articleRepo.save(po);
	}

	@Override
	public void delete(int id) {
		articleRepo.updateDeleted(id);
	}

	private static ArticlePo bo2Po(ArticleBo bo) {
		ArticlePo po = new ArticlePo();
		BeanUtils.copyProperties(bo, po);
		po.setTags(getTagString(bo.getTags()));
		return po;
	}

	private static ArticleBo po2Bo(ArticlePo po) {
		ArticleBo result = new ArticleBo();
		BeanUtils.copyProperties(po, result);
		result.setTags(getTags(po.getTags()));
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