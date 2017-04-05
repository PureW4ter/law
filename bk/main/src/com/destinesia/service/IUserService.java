package com.destinesia.service;

import java.util.List;

import com.destinesia.entity.Code;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.UserAlbumCount;
import com.destinesia.entity.dto.LoginReturnDTO;

public interface IUserService {
	public TokenInfo regist(User user, String passcode);
	public TokenInfo wxregist(User user);
	public void updateUser(User user);
	public void resetPassword(String phone, String newPassword, String passcode);
	public LoginReturnDTO login(String key, String password);
	public LoginReturnDTO loginbyphone(String phone, String passcode);
	public int getGrade(String code);
	public List<Code> generateCode(int count, int grade, String identity);
	public User findUserByPhone(String phone);
	public User findUserByEmail(String email);
	public User findUserByNickName(String nickName);
	public List<User>list(String token);
	public void disableUser(String id, String token);
	public void enableUser(String id, String token);
	public void increaseGrade(String id);
	public int updateUserGrade(String token, String code);
	public void updateHeader(String token, String header);
	public UserAlbumCount albumCounts(String token);
}
