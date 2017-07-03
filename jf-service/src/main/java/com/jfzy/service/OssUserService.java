package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OssUserBo;

public interface OssUserService {

	OssUserBo login(String phoneNum, String code);

	public OssUserBo loginWithPassword(String loginName, String password);

	void updateStatus(int status, int id);

	void updateAuth(String role, int id);

	void create(OssUserBo bo);

	List<OssUserBo> getOssUsers(Pageable page);

	OssUserBo getUserById(int id);

	void resetPassword(int userId, boolean isLawyer, String oldPwd, String newPwd);

}
