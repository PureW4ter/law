package com.jfzy.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.service.LawyerService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.web.vo.LawyerVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class LawyerController {

	@Autowired
	private LawyerService lawyerService;

	@ResponseBody
	@GetMapping("/api/lawyer/list")
	public ResponseVo<List<LawyerVo>> list(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<LawyerBo> values = lawyerService.getLawyers(new PageRequest(page, size, sort));
		List<LawyerVo> resultUsers = new ArrayList<LawyerVo>(values.size());
		for (LawyerBo bo : values) {
			resultUsers.add(boToVo(bo));
		}
		return new ResponseVo<List<LawyerVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultUsers);
	}
	
	@ResponseBody
	@GetMapping("/api/lawyer/listbycity")
	public ResponseVo<List<LawyerVo>> listByCity(int cityId) {
		List<LawyerBo> values = lawyerService.getLawyerByCity(cityId);
		List<LawyerVo> result = new ArrayList<LawyerVo>(values.size());
		for (LawyerBo bo : values) {
			result.add(boToVo(bo));
		}
		return new ResponseVo<List<LawyerVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, result);
	}
	

	@ResponseBody
	@GetMapping("/api/lawyer/ustatus")
	public ResponseVo<Object> updateStatus(int id, int status) {
		lawyerService.updateLawyerStatus(id, status);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@PostMapping(path="/api/lawyer/create",consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> create(HttpServletRequest request, HttpServletResponse response, @RequestBody LawyerVo vo) {
		LawyerBo bo = voToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		lawyerService.create(bo);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@GetMapping("/api/lawyer/detail")
	public ResponseVo<LawyerVo> detail(int id) {
		LawyerBo bo = lawyerService.getLawyerById(id);
		LawyerVo vo = boToVo(bo);
		return new ResponseVo<LawyerVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
	}
	
	private static LawyerVo boToVo(LawyerBo bo) {
		LawyerVo vo = new LawyerVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private static LawyerBo voToBo(LawyerVo vo) {
		LawyerBo bo = new LawyerBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}

}
