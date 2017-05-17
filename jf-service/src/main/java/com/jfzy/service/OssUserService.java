package com.jfzy.service;

import com.jfzy.service.bo.OssUserBo;

public interface OssUserService {

	OssUserBo login(String userName, String password);
}
