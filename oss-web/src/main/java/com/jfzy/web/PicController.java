package com.jfzy.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jfzy.service.PicService;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class PicController {

	private static final Logger logger = LoggerFactory.getLogger(PicController.class);

	@Autowired
	private PicService picService;

	// enctype="multipart/form-data"
	@ResponseBody
	@PostMapping("/api/pic/upload")
	public ResponseVo<String> uploadPic(@RequestParam("pic") MultipartFile file) {
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
			return new ResponseVo<String>(ResponseStatusEnum.SUCCESS.getCode(), "图片上传成功", fileName);
		} catch (IOException e) {
			logger.error("Failed in uploadPic.", e);
			return new ResponseVo<String>(ResponseStatusEnum.SERVER_ERROR.getCode(), "图片上传失败", null);
		}
	}

}
