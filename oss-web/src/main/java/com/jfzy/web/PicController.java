package com.jfzy.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jfzy.service.PicService;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.TagVo;

@RestController
public class PicController {

	private static final Logger logger = LoggerFactory.getLogger(PicController.class);

	@Autowired
	private PicService picService;

	// enctype="multipart/form-data"
	@ResponseBody
	@PostMapping("/api/pic/upload")
	public Object uploadPic(@RequestParam("filedata") MultipartFile file) {
		try {
			if (file == null || file.getBytes() == null || file.getBytes().length == 0) {
				return new ResponseVo<String>(ResponseStatusEnum.BAD_REQUEST.getCode(), "图片参数有误", null);
			}
		} catch (IOException e1) {
			logger.error("Failed in param check of uploadPic.", e1);
			return new ResponseVo<String>(ResponseStatusEnum.BAD_REQUEST.getCode(), "图片参数有误", null);
		}

		try {
			byte[] bytes = file.getBytes();
			String fileName = picService.uploadPic(bytes);
			Map<String, String> result = new HashMap<String, String>();
			result.put("err", "");
			result.put("msg", fileName);
			return result;
		} catch (IOException e) {
			logger.error("Failed in uploadPic.", e);
			return new ResponseVo<String>(ResponseStatusEnum.SERVER_ERROR.getCode(), "图片上传失败", null);
		}
	}
	
	@ResponseBody
	@GetMapping("/api/pic/token")
	public ResponseVo<String> getToken() {
		String token = picService.getToken();
		return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "图片上传成功", token);
	}
}
