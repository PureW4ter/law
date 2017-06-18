package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OssUserBo;

public interface OssUserService {

	OssUserBo login(String phoneNum, String code);

	void updateStatus(int status, int id);

	void updateAuth(String role, int id);

	void create(OssUserBo bo);

	public List<OssUserBo> getOssUsers(Pageable page);

	OssUserBo getUserById(int id);

}
