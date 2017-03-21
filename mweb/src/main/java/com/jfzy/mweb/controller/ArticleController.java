package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ArticleVO;
import com.jfzy.mweb.vo.ResponseVO;
import com.jfzy.mweb.vo.TagVO;

@RestController
public class ArticleController {

	@ResponseBody
	@GetMapping("/article/tag")
	public ResponseVO<List<TagVO>> getTags() {

		return new ResponseVO<List<TagVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, new ArrayList<TagVO>());
	}

	@ResponseBody
	@GetMapping("/article")
	public ResponseVO<List<ArticleVO>> getArticles() {

		return new ResponseVO<List<ArticleVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, new ArrayList<ArticleVO>());
	}

}
