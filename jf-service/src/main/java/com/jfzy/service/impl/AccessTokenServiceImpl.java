package com.jfzy.service.impl;

import org.springframework.scheduling.annotation.Scheduled;

import com.jfzy.service.AccessTokenService;

public class AccessTokenServiceImpl implements AccessTokenService {
	
	
	
	@Scheduled(fixedRate = 7000000)
	public void refreshAccessToken() {

	}

	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
