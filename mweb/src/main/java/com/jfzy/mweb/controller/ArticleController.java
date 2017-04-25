package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ArticleVO;
import com.jfzy.mweb.vo.ResponseVO;
import com.jfzy.mweb.vo.TagVO;
import com.jfzy.service.ArticleService;
import com.jfzy.service.TagService;
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
	public ResponseVO<List<ArticleVO>> getArticles() {
		articleService.searchByTags(null);
		
		return new ResponseVO<List<ArticleVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, new ArrayList<ArticleVO>());
	}

	private static TagVO boToVo(TagBo bo) {
		TagVO vo = new TagVO();
		vo.setId(bo.getId());
		vo.setName(bo.getName());
		vo.setWeight(bo.getWeight());

		return vo;
	}

}
