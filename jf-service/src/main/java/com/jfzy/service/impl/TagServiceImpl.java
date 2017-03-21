package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.TagService;
import com.jfzy.service.bo.TagBO;
import com.jfzy.service.po.TagPO;
import com.jfzy.service.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepo;

	@Override
	public List<TagBO> getAllTags() {
		List<TagPO> pos = tagRepo.findAll();
		List<TagBO> resultList = new ArrayList<TagBO>(pos.size());
		for (TagPO po : pos) {
			resultList.add(poToBO(po));
		}

		return resultList;
	}

	private static TagBO poToBO(TagPO po) {
		TagBO bo = new TagBO();
		bo.setId(po.getId());
		bo.setName(po.getName());
		bo.setWeight(po.getWeight());
		return bo;
	}

}