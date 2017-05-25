package com.jfzy.web;

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



import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.UserVo;
import com.jfzy.service.UserService;
import com.jfzy.service.WechatService;
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
	@GetMapping("/api/user/detail")
	public ResponseVo<UserVo> detail(int id) {
		UserBo bo = userService.getUser(id);
		return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVoForUser(bo));
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
