package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.UpdatesService;
import com.jfzy.service.bo.UpdateBo;
import com.jfzy.service.po.UpdatePo;
import com.jfzy.service.repository.UpdatesRepository;

@Service
public class UpdatesServiceImpl implements UpdatesService {

	@Autowired
	private UpdatesRepository updatesRepo;

	@Override
	public List<UpdateBo> getUpdates(int type, Pageable page) {
		Page<UpdatePo> resultPos = updatesRepo.findAll(type, page);
		List<UpdateBo> results = new ArrayList<UpdateBo>();
		if (resultPos != null && resultPos.hasContent()) {
			resultPos.forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	private UpdateBo poToBo(UpdatePo po) {
		UpdateBo bo = new UpdateBo();
		bo.setId(po.getId());
		bo.setImageUrl(po.getImageUrl());
		bo.setSubTitle(po.getSubTitle());
		bo.setTitle(po.getTitle());
		return bo;
	}

}
