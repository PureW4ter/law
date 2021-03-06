package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserBo;

public interface UserService {
	
	List<UserBo> getUsers(Pageable page);
	
	UserBo getUser(int id);
	
	public void create(UserBo bo);
	
	UserAccountBo getUserAccountByOpenid(String openid);
	
	UserAccountBo getUserAccountByUserId(int userId, int type);
	
	public void updateMemo(String memo, int id);
	
	UserBo createOrUpdateUser(UserBo user);

	void register(UserAccountBo ua);
	
	void unbind(int userAccountId);
	
	UserBo bind(String phone, int userId);
	
	List<UserBo> getUsersByLevel(int level, Pageable page);
}
