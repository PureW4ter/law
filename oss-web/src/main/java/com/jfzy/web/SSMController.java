package com.jfzy.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class SSMController {
	@ResponseBody
	@GetMapping("/api/ssm/code")
	public ResponseVo<String> getCode(String phone) {
		//String token = picService.getToken();
		return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "发送成功", null);
	}
}
