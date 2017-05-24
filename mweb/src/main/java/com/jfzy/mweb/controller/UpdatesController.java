package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.UpdateVo;
import com.jfzy.service.UpdatesService;
import com.jfzy.service.bo.UpdateBo;

@RestController
public class UpdatesController {

	@Autowired
	private UpdatesService updatesService;

	@ResponseBody
	@GetMapping("/api/updates/{type}/{pageNum}")
	public ResponseVo<List<UpdateVo>> getTags(@PathVariable("type") int type, @PathVariable("pageNum") int pageNum) {
		Pageable page = new PageRequest(pageNum, 10);

		List<UpdateBo> bos = updatesService.getUpdates(type, page);
		List<UpdateVo> results = new ArrayList<UpdateVo>();
		bos.forEach(bo -> results.add(boToVo(bo)));
		return new ResponseVo<List<UpdateVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, results);
	}

	private static UpdateVo boToVo(UpdateBo bo) {
		UpdateVo vo = new UpdateVo();
		vo.setId(bo.getId());
		vo.setImageUrl(bo.getImageUrl());
		vo.setSubTitle(bo.getSubTitle());
		vo.setTitle(bo.getTitle());
		vo.setType(bo.getType());

		return vo;
	}

}
