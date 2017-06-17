package com.jfzy.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.ShortUrlService;
import com.jfzy.service.repository.RedisRepository;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

	@Autowired
	private RedisRepository redisRepo;

	@Override
	public String getRealUrl(String code) {
		return "";
	}

	@Override
	public String getShortUrl(String realUrl) {
		Base64 encoder = new Base64(true);

		return "";
	}

}
