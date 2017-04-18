package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.TagService;
import com.jfzy.service.bo.TagBo;
import com.jfzy.service.po.TagPo;
import com.jfzy.service.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepo;

	@Override
	public List<TagBo> getAllTags() {
		List<TagPo> pos = tagRepo.findAll();
		List<TagBo> resultList = new ArrayList<TagBo>(pos.size());
		for (TagPo po : pos) {
			resultList.add(poToBo(po));
		}

		return resultList;
	}

	public void deleteTag(int id) {
		tagRepo.updateDeleted(id);
	}

	private static TagBo poToBo(TagPo po) {
		TagBo bo = new TagBo();
		bo.setId(po.getId());
		bo.setName(po.getName());
		bo.setWeight(po.getWeight());
		return bo;
	}

	@Override
	public void addTag(TagBo bo) {

	}

	@Override
	public void updateTag(TagBo bo) {

	}

}