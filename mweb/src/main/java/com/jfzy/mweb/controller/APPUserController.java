package com.jfzy.mweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.UserVo;
import com.jfzy.service.UserService;
import com.jfzy.service.WechatService;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;

@RestController
public class APPUserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private WechatService wechatService;
	
	
	@ResponseBody
	@GetMapping("/api/user/list")
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
	@GetMapping(path="/api/user/wxlogin")
	public ResponseVo<UserVo> wxlogin(String code) {
		try {
			UserBo bo = wechatService.wxlogin(code);
			UserAccountBo abo = userService.getUserAccountByUserId(bo.getId(), UserAccountTypeEnum.MOBILE.getId());
			UserVo vo = boToVoForUser(bo);
			if(abo != null)
				vo.setPhone(abo.getValue());
			return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
		} catch (IOException e) {
			return new ResponseVo<UserVo>(ResponseStatusEnum.SERVER_ERROR.getCode(), null, null);
		}
	}
	
	@ResponseBody
	@GetMapping("/api/user/detail")
	public ResponseVo<UserVo> detail(int id) {
		UserBo bo = userService.getUser(id);
		return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVoForUser(bo));
	}
	
	@ResponseBody
	@GetMapping("/api/user/bind")
	public ResponseVo<Object> bind(String phone, String code, int userId) {
		userService.bind(phone, userId);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@GetMapping("/api/user/unbind")
	public ResponseVo<Object> unbind(int userAccountId) {
		userService.unbind(userAccountId);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	private static UserBo voToBoForUser(UserVo vo) {
		UserBo bo = new UserBo();
		BeanUtils.copyProperties(vo, bo);
		return bo;
	}
	
	private static UserVo boToVoForUser(UserBo bo) {
		UserVo vo = new UserVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}
}
