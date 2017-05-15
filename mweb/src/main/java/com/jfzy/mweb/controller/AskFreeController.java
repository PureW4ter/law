package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.PropertyVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.SimpleResponseVo;
import com.jfzy.service.PropertyService;
import com.jfzy.service.QAService;
import com.jfzy.service.bo.PropertyBo;
import com.jfzy.service.bo.PropertyTypeEnum;
import com.jfzy.service.bo.QABo;
import com.jfzy.service.bo.QAStatusEnum;

@RestController
public class AskFreeController {

	@Autowired
	private PropertyService propService;

	@Autowired
	private QAService qaService;

	@ResponseBody
	@GetMapping("/askfree/props")
	public ResponseVo<List<List<PropertyVo>>> getProps() {
		List<PropertyBo> roleProps = propService.getPropertyByType(PropertyTypeEnum.ROLE.getId());
		List<PropertyBo> phaseProps = propService.getPropertyByType(PropertyTypeEnum.PHASE.getId());

		List<PropertyVo> roleResult = new ArrayList<PropertyVo>(roleProps.size());
		for (PropertyBo bo : roleProps) {
			roleResult.add(boToVo(bo));
		}
		List<PropertyVo> phaseResult = new ArrayList<PropertyVo>(phaseProps.size());
		for (PropertyBo bo : phaseProps) {
			phaseResult.add(boToVo(bo));
		}

		List<List<PropertyVo>> results = new ArrayList<List<PropertyVo>>(2);
		results.add(roleResult);
		results.add(phaseResult);

		return new ResponseVo<List<List<PropertyVo>>>(ResponseStatusEnum.SUCCESS.getCode(), null, results);

	}

	@ResponseBody
	@PostMapping("/askfree")
	public SimpleResponseVo createQA(String role, String phase, String questionDetail) {
		QABo bo = new QABo();
		bo.setCityId(0);// FIXME
		bo.setContent(questionDetail);
		bo.setPhase(phase);
		bo.setRole(role);
		bo.setStatus(QAStatusEnum.UNPROCESSED.getId());
		bo.setUserId(0);// FIXME
		bo.setUserRealName("");// FIXME
		qaService.createQA(bo);

		return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), null);
	}

	private static PropertyVo boToVo(PropertyBo bo) {
		PropertyVo vo = new PropertyVo();
		vo.setId(bo.getId());
		vo.setType(bo.getType());
		vo.setName(bo.getName());
		return vo;
	}

}
