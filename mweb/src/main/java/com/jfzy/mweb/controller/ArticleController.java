package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ArticleVO;
import com.jfzy.mweb.vo.ResponseVO;
import com.jfzy.mweb.vo.TagVO;
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
	public ResponseVO<List<TagVO>> getTags() {

		List<TagBo> tags = tagService.getAllTags();
		List<TagVO> resultTags = new ArrayList<TagVO>(tags.size());
		for (TagBo bo : tags) {
			resultTags.add(boToVo(bo));
		}

		return new ResponseVO<List<TagVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultTags);
	}

	@ResponseBody
	@GetMapping("/article")
	public ResponseVO<List<ArticleVO>> getArticles(String[] tags, int page) {
		if (page < 0) {
			page = 0;
		}
		List<ArticleBo> values = articleService.searchByTags(tags, new PageRequest(page, 10));
		List<ArticleVO> resultArticles = new ArrayList<ArticleVO>(values.size());
		for (ArticleBo bo : values) {
			resultArticles.add(boToVo(bo));
		}

		return new ResponseVO<List<ArticleVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultArticles);
	}

	private static ArticleVO boToVo(ArticleBo bo) {
		ArticleVO vo = new ArticleVO();
		vo.setContent(bo.getContent());
		vo.setId(bo.getId());
		vo.setTags(bo.getTags());
		vo.setTitle(bo.getTitle());
		vo.setTitleImgUrl(bo.getTitleImgUrl());

		return vo;
	}

	private static TagVO boToVo(TagBo bo) {
		TagVO vo = new TagVO();
		vo.setId(bo.getId());
		vo.setName(bo.getName());
		vo.setWeight(bo.getWeight());

		return vo;
	}

}
