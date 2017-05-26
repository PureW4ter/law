package com.jfzy.web;

import java.io.IOException;
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






import com.jfzy.web.vo.ArticleVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.UserVo;
import com.jfzy.service.UserService;
import com.jfzy.service.WechatService;
import com.jfzy.service.bo.ArticleBo;
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
	@GetMapping("/api/user/detail")
	public ResponseVo<UserVo> detail(int id) {
		UserBo bo = userService.getUser(id);
		UserVo vo = boToVoForUser(bo);
		UserAccountBo abo = userService.getUserAccountByUserId(bo.getId(), UserAccountTypeEnum.MOBILE.getId());
		if(abo != null)
			vo.setPhone(abo.getValue());
		return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
	}
	
	@ResponseBody
	@PostMapping(path="/api/user/memo", consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> memo(@RequestBody UserVo vo) {
		userService.updateMemo(vo.getMemo(), vo.getId());
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
	
	@ResponseBody
	@PostMapping(path="/api/user/create",consumes =MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseVo<Object> createArticle(@RequestBody UserVo vo) {
		UserBo bo = voToBoForUser(vo);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userService.create(bo);
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
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");      
		vo.setCreateTime(myFmt.format(bo.getCreateTime()));
		return vo;
	}
}
