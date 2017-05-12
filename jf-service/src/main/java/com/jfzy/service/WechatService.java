package com.jfzy.service;

import java.io.IOException;

import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserBo;

public interface WechatService {
	public UserBo wxlogin(String code) throws IOException;
}
