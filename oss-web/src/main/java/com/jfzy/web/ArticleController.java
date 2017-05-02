package com.jfzy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.ArticleService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.web.vo.ArticleVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@ResponseBody
	@PutMapping("/article")
	public ResponseVo createArticle(ArticleVo articleVo) {
		ArticleBo articleBo = voToBo(articleVo);
		articleService.createArticle(articleBo);
		return new ResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	private static ArticleBo voToBo(ArticleVo vo) {
		ArticleBo bo = new ArticleBo();
		bo.setContent(vo.getContent());
		bo.setTags(vo.getTags());
		bo.setTitle(vo.getTitle());
		bo.setTitleImgUrl(vo.getTitleImgUrl());
		return bo;
	}
}
