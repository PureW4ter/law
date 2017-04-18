package com.jfzy.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.TagService;
import com.jfzy.service.bo.TagBo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVO;
import com.jfzy.web.vo.TagVO;

@RestController
public class TagController {

	@Autowired
	private TagService tagService;

	@ResponseBody
	@GetMapping("/tag")
	public ResponseVO<List<TagVO>> getTags() {

		List<TagBo> tags = tagService.getAllTags();
		List<TagVO> resultTags = new ArrayList<TagVO>(tags.size());
		for (TagBo bo : tags) {
			resultTags.add(boToVo(bo));
		}

		return new ResponseVO<List<TagVO>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultTags);
	}

	@ResponseBody
	@PutMapping("/tag")
	public ResponseVO addTag(TagVO tag) {

		return new ResponseVO(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@DeleteMapping("/tag/{id}")
	public ResponseVO deleteTag(@PathVariable("id") int id) {
		tagService.deleteTag(id);
		return new ResponseVO(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	private static TagVO boToVo(TagBo bo) {
		TagVO vo = new TagVO();
		vo.setId(bo.getId());
		vo.setName(bo.getName());
		vo.setWeight(bo.getWeight());

		return vo;
	}

	private static TagBo voToBo(TagVO vo) {
		TagBo bo = new TagBo();
		bo.setName(vo.getName());
		bo.setWeight(vo.getWeight());
		return bo;
	}
}
