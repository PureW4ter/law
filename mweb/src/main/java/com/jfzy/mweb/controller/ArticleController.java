package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ArticleVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.TagVo;
import com.jfzy.service.ArticleService;
import com.jfzy.service.TagService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.bo.TagBo;

@RestController
public class ArticleController {

	@Autowired
	private TagService tagService;

	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@GetMapping("/article/tag")
	public ResponseVo<List<TagVo>> getTags() {

		List<TagBo> tags = tagService.getAllTags();
		List<TagVo> resultTags = new ArrayList<TagVo>(tags.size());
		for (TagBo bo : tags) {
			resultTags.add(boToVo(bo));
		}

		return new ResponseVo<List<TagVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultTags);
	}

	@ResponseBody
	@GetMapping("/article")
	public ResponseVo<List<ArticleVo>> getArticles(String[] tags, int page) {
		if (page < 0) {
			page = 0;
		}
		List<ArticleBo> values = articleService.searchByTags(tags, new PageRequest(page, 10));
		List<ArticleVo> resultArticles = new ArrayList<ArticleVo>(values.size());
		for (ArticleBo bo : values) {
			resultArticles.add(boToVo(bo));
		}

		return new ResponseVo<List<ArticleVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultArticles);
	}

	private static ArticleVo boToVo(ArticleBo bo) {
		ArticleVo vo = new ArticleVo();
		vo.setContent(bo.getContent());
		vo.setId(bo.getId());
		vo.setTags(bo.getTags());
		vo.setTitle(bo.getTitle());
		vo.setTitleImgUrl(bo.getTitleImgUrl());
		vo.setCreateTime(bo.getCreateTime());
		return vo;
	}

	private static TagVo boToVo(TagBo bo) {
		TagVo vo = new TagVo();
		vo.setId(bo.getId());
		vo.setName(bo.getName());
		vo.setWeight(bo.getWeight());

		return vo;
	}

}
