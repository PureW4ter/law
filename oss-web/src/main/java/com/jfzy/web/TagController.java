package com.jfzy.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.TagService;
import com.jfzy.service.bo.TagBo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleResponseVo;
import com.jfzy.web.vo.TagVo;

@RestController
public class TagController {

	@Autowired
	private TagService tagService;

	@ResponseBody
	@GetMapping("/api/tag")
	public ResponseVo<List<TagVo>> getTags() {

		List<TagBo> tags = tagService.getAllTags();
		List<TagVo> resultTags = new ArrayList<TagVo>(tags.size());
		for (TagBo bo : tags) {
			resultTags.add(boToVo(bo));
		}

		return new ResponseVo<List<TagVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultTags);
	}

	@ResponseBody
	@DeleteMapping("/api/tag/{id}")
	public SimpleResponseVo deleteTag(@PathVariable("id") int id) {
		tagService.deleteTag(id);
		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), "");
	}

	private static TagVo boToVo(TagBo bo) {
		TagVo vo = new TagVo();
		vo.setId(bo.getId());
		vo.setName(bo.getName());
		vo.setWeight(bo.getWeight());

		return vo;
	}

	private static TagBo voToBo(TagVo vo) {
		TagBo bo = new TagBo();
		bo.setName(vo.getName());
		bo.setWeight(vo.getWeight());
		return bo;
	}
}
