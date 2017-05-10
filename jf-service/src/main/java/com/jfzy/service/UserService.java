package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserBo;

public interface UserService {
	
	List<UserBo> getUsers(Pageable page);
	
	UserBo getUser(int id);
	
	UserAccountBo getUserAccountByOpenid(String openid);
	
	int createOrUpdateUser(UserBo user);

	void register(UserAccountBo ua, int userId);
	
	void unbind(int userAccountId);
	
}
