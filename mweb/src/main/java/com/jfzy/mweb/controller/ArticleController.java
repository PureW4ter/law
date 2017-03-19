package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.vo.TagVO;

@RestController
public class ArticleController {

	@GetMapping("/article/tags")
	public List<TagVO> getTags() {

		return new ArrayList<TagVO>();
	}

}
