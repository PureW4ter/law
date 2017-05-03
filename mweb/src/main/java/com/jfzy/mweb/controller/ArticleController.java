package com.jfzy.mweb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ArticleVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.SimpleArticleVo;
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
	@GetMapping("/article/list")
	public ResponseVo<List<SimpleArticleVo>> getArticles(String[] tags, int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<ArticleBo> values = articleService.searchByTags(tags, new PageRequest(page, size, sort));
		List<SimpleArticleVo> resultArticles = new ArrayList<SimpleArticleVo>(values.size());
		for (ArticleBo bo : values) {
			resultArticles.add(boToSVo(bo));
		}
		return new ResponseVo<List<SimpleArticleVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultArticles);
	}

	@ResponseBody
	@GetMapping("/article/create")
	public void createArticle(String[] tags, int page) {
		return;
	}
	
	@ResponseBody
	@GetMapping(value = "/article/detail")
	public ResponseVo<ArticleVo> articleDetail(int id) {
		ArticleBo bo = articleService.getArticle(id);
		if(bo!=null){
			return new ResponseVo<ArticleVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVo(bo));
		}else{
			return new ResponseVo<ArticleVo>(ResponseStatusEnum.SERVER_ERROR.getCode(), null, null);
		}
	}
	
	private static ArticleVo boToVo(ArticleBo bo) {
		ArticleVo vo = new ArticleVo();
		vo.setContent(bo.getContent());
		vo.setSummary(bo.getSummary());
		vo.setId(bo.getId());
		vo.setTags(bo.getTags());
		vo.setTitle(bo.getTitle());
		vo.setTitleImgUrl(bo.getTitleImgUrl());
		vo.setShareIconUrl(bo.getShareIconUrl());
		vo.setCreateTime(bo.getCreateTime());
		return vo;
	}

	private static SimpleArticleVo boToSVo(ArticleBo bo) {
		SimpleArticleVo vo = new SimpleArticleVo();
		vo.setId(bo.getId());
		vo.setTags(bo.getTags());
		vo.setTitle(bo.getTitle());
		vo.setTitleImgUrl(bo.getTitleImgUrl());
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");      
		vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		vo.setSummary(bo.getSummary());
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
