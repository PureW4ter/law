package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OssUserBo;

public interface OssUserService {

	OssUserBo login(String userName, String password);

	public List<OssUserBo> getOssUsers(Pageable page);

	void createUser(OssUserBo bo);
}
