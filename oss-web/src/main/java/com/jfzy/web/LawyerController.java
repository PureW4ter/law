package com.jfzy.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.LawyerService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleLawyerVo;

@RestController
public class LawyerController {

	@Autowired
	private LawyerService lawyerService;

	@ResponseBody
	@GetMapping("/{city}/lawyer")
	public ResponseVo<List<SimpleLawyerVo>> getActiveLawyer(@PathVariable("city") int cityId) {
		List<LawyerBo> lawyerBos = lawyerService.getActiveLawyerByCity(cityId);
		List<SimpleLawyerVo> results = new ArrayList<SimpleLawyerVo>(lawyerBos.size());
		lawyerBos.forEach(bo -> results.add(boToVo(bo)));
		return new ResponseVo<List<SimpleLawyerVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, results);

	}

	private static SimpleLawyerVo boToVo(LawyerBo bo) {
		SimpleLawyerVo vo = new SimpleLawyerVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}
}
