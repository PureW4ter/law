package com.jfzy.mweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzf.core.Utils;
import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.service.SmsService;

@RestController
public class SSMController {
	
	@Autowired
	private SmsService smsService;
	
	@ResponseBody
	@GetMapping("/api/ssm/code")
	public ResponseVo<String> getCode(String phoneNum) {
		smsService.sendRegisterCode(phoneNum, Utils.getSmsCode(4));
		return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "发送成功", null);
	}
}
