package com.jfzy.mweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.base.BaseController;
import com.jfzy.service.ShortUrlService;

@RestController
public class ShortUrlController extends BaseController {

	@Autowired
	private ShortUrlService suService;

	@GetMapping("/s/{code}")
	public String getCode(@PathVariable String code) {
		if (StringUtils.isNotEmpty(code)) {
			String realUrl = suService.getRealUrl(code);
			return realUrl;
		}

		return "";
	}

}
