package com.jfzy.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.base.AuthCheck;
import com.jfzy.service.LawyerService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.web.vo.ArticleVo;
import com.jfzy.web.vo.LawyerVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleLawyerVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class LawyerController {

	@Autowired
	private LawyerService lawyerService;

	@ResponseBody
	@GetMapping("/api/{city}/lawyerselection")
	@AuthCheck(privileges = { "super_admin", "admin", "order_admin" })
	public ResponseVo<List<SimpleLawyerVo>> getActiveLawyer(@PathVariable("city") int cityId) {
		List<LawyerBo> lawyerBos = lawyerService.getActiveLawyerByCity(cityId);
		List<SimpleLawyerVo> results = new ArrayList<SimpleLawyerVo>(lawyerBos.size());
		lawyerBos.forEach(bo -> results.add(boToSimpleVo(bo)));
		return new ResponseVo<List<SimpleLawyerVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, results);
	}

	@ResponseBody
	@GetMapping("/api/{city}/lawyer")
	@AuthCheck(privileges = { "super_admin", "admin" })
	public ResponseVo<List<LawyerVo>> getLawyerListByCity(@PathVariable("city") int cityId) {
		List<LawyerBo> lawyerBos = lawyerService.getActiveLawyerByCity(cityId);
		List<LawyerVo> results = new ArrayList<LawyerVo>(lawyerBos.size());
		lawyerBos.forEach(bo -> results.add(boToVo(bo)));
		return new ResponseVo<List<LawyerVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, results);
	}

	private static SimpleLawyerVo boToSimpleVo(LawyerBo bo) {
		SimpleLawyerVo vo = new SimpleLawyerVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
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

	@PostMapping(path="/api/lawyer/list", consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	@AuthCheck(privileges = { "super_admin", "admin" })
	public SimpleResponseVo createLawyer(HttpServletRequest request, HttpServletResponse response, @RequestBody LawyerVo vo) {
		LawyerBo bo = voToBo(vo);
		lawyerService.createLawyer(bo);

		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	@PutMapping("/api/lawyer/{id}")
	@AuthCheck(privileges = { "super_admin", "admin" })
	public SimpleResponseVo updateLawyer(LawyerVo vo) {
		LawyerBo bo = voToBo(vo);
		bo.setId(0);
		lawyerService.createLawyer(bo);

		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}
}
