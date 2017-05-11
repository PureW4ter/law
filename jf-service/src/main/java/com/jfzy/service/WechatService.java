package com.jfzy.service;

import java.io.IOException;

import com.jfzy.service.bo.UserAccountBo;

public interface WechatService {
	public UserAccountBo getWXUserAccount(String code, Integer userId) throws IOException;
}
