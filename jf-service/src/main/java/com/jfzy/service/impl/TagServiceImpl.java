package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.TagService;
import com.jfzy.service.bo.TagBo;
import com.jfzy.service.po.TagPO;
import com.jfzy.service.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepo;

	@Override
	public List<TagBo> getAllTags() {
		List<TagPO> pos = tagRepo.findAll();
		List<TagBo> resultList = new ArrayList<TagBo>(pos.size());
		for (TagPO po : pos) {
			resultList.add(poToBo(po));
		}

		return resultList;
	}

	private static TagBo poToBo(TagPO po) {
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