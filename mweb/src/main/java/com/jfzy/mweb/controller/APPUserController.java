package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.UserAccountVo;
import com.jfzy.mweb.vo.UserVo;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserBo;

@RestController
public class APPUserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@GetMapping("/user/list")
	public ResponseVo<List<UserVo>> list(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<UserBo> values = userService.getUsers(new PageRequest(page, size, sort));
		List<UserVo> resultUsers = new ArrayList<UserVo>(values.size());
		for (UserBo bo : values) {
			resultUsers.add(boToVoForUser(bo));
		}
		return new ResponseVo<List<UserVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultUsers);
	}
	
	@ResponseBody
	@PostMapping(path="/user/register" ,consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> register(UserVo vo, UserAccountVo avo) {
		UserAccountBo abo = voToBoForUserAccount(avo);
		UserBo bo = voToBoForUser(vo);
		int userId = userService.createOrUpdateUser(bo);
		userService.register(abo, userId);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@GetMapping("/user/detail")
	public ResponseVo<UserVo> detail(int id) {
		UserBo bo = userService.getUser(id);
		return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVoForUser(bo));
	}
	
	@ResponseBody
	@GetMapping("/user/unbind")
	public ResponseVo<Object> unbind(int userAccountId) {
		userService.unbind(userAccountId);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	private static UserBo voToBoForUser(UserVo vo) {
		UserBo bo = new UserBo();
		bo.setId(vo.getId());
		bo.setAddress(vo.getAddress());
		bo.setCreateTime(vo.getCreateTime());
		bo.setHeadImg(vo.getHeadImg());
		bo.setMemo(vo.getMemo());
		bo.setName(vo.getName());
		bo.setPostcode(vo.getPostcode());
		bo.setRealName(vo.getRealName());
		bo.setStatus(vo.getStatus());
		return bo;
	}
	
	private static UserVo boToVoForUser(UserBo bo) {
		UserVo vo = new UserVo();
		vo.setId(bo.getId());
		vo.setAddress(bo.getAddress());
		vo.setCreateTime(bo.getCreateTime());
		vo.setHeadImg(bo.getHeadImg());
		vo.setMemo(bo.getMemo());
		vo.setName(bo.getName());
		vo.setPostcode(bo.getPostcode());
		vo.setRealName(bo.getRealName());
		vo.setStatus(bo.getStatus());
		return vo;
	}
	
	private static UserAccountBo voToBoForUserAccount(UserAccountVo vo) {
		UserAccountBo bo = new UserAccountBo();
		bo.setCreateTime(vo.getCreateTime());
		bo.setStatus(vo.getStatus());
		bo.setType(vo.getType());
		bo.setUserId(vo.getUserId());
		bo.setValue(vo.getValue());
		return bo;
	}
	
	private static UserAccountVo voToBoForUserAccount(UserAccountBo bo) {
		UserAccountVo vo = new UserAccountVo();
		vo.setCreateTime(bo.getCreateTime());
		vo.setStatus(bo.getStatus());
		vo.setType(bo.getType());
		vo.setUserId(bo.getUserId());
		vo.setValue(bo.getValue());
		return vo;
	}
}
