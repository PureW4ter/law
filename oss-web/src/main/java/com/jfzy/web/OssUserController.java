package com.jfzy.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

import com.jfzy.base.AuthCheck;
import com.jfzy.service.OssRoleService;
import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.bo.RoleBo;
import com.jfzy.web.vo.OssRoleVo;
import com.jfzy.web.vo.OssUserVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@AuthCheck(privileges = { "admin" })
@RestController
public class OssUserController {

	@Autowired
	private OssUserService ossUserService;

	@Autowired
	private OssRoleService ossRoleService;

	@ResponseBody
	@GetMapping("/api/ossuser/roles")
	public ResponseVo<List<OssRoleVo>> roles() {
		List<RoleBo> values = ossRoleService.getRoles();
		List<OssRoleVo> resultUsers = new ArrayList<OssRoleVo>(values.size());
		for (RoleBo bo : values) {
			resultUsers.add(boToVo(bo));
		}

		return new ResponseVo<List<OssRoleVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultUsers);
	}
	
	@ResponseBody
	@GetMapping("/api/ossuser/list")
	public ResponseVo<List<OssUserVo>> list(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OssUserBo> values = ossUserService.getOssUsers(new PageRequest(page, size, sort));
		List<OssUserVo> resultUsers = new ArrayList<OssUserVo>(values.size());
		for (OssUserBo bo : values) {
			resultUsers.add(boToVo(bo));
		}
		return new ResponseVo<List<OssUserVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultUsers);
	}

	@ResponseBody
	@GetMapping("/api/ossuser/ustatus")
	public ResponseVo<Object> updateStatus(int id, int status) {
		ossUserService.updateStatus(status, id);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@GetMapping("/api/ossuser/urole")
	public ResponseVo<Object> updateRole(int id, String role) {
		ossUserService.updateAuth(role, id);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	@ResponseBody
	@PostMapping(path = "/api/ossuser/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> create(HttpServletRequest request, HttpServletResponse response,
			@RequestBody OssUserVo vo) {

		OssUserBo bo = voToBo(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		ossUserService.create(bo);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	private static OssUserVo boToVo(OssUserBo bo) {
		OssUserVo vo = new OssUserVo();
		BeanUtils.copyProperties(bo, vo);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
		vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		return vo;
	}

	private static OssUserBo voToBo(OssUserVo vo) {
		OssUserBo bo = new OssUserBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}

	private static OssRoleVo boToVo(RoleBo bo) {
		OssRoleVo vo = new OssRoleVo();
		BeanUtils.copyProperties(bo, vo);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
		vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		return vo;
	}
}
