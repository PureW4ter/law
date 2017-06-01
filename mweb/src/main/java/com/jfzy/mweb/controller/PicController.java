package com.jfzy.mweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.service.PicService;

@RestController
public class PicController {
	@Autowired
	private PicService picService;

	@ResponseBody
	@GetMapping("/api/pic/token")
	public ResponseVo<String> getToken() {
		String token = picService.getToken();
		return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "图片上传成功", token);
	}
}
