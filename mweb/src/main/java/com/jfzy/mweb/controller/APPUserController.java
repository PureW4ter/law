package com.jfzy.mweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.TagVo;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.TagBo;

@RestController
public class APPUserController {
	@Autowired
	//private UserService userService;
	
	@ResponseBody
	@GetMapping("/user/bind")
	public ResponseVo<Object> getTags() {

	

		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}
}
