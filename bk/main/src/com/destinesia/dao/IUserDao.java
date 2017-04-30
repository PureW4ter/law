package com.destinesia.dao;

import java.util.List;

import com.destinesia.entity.Code;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.UserAlbumCount;

public interface IUserDao {
	public void regist(User user);
	public void updateUser(User user);
	public void updatePassword(String phone, String password);
	public User findUserByEmail(String email);
	public User findUserByNickName(String nickName);
	public User findUserById(String id);
	public User findUserByPhone(String phone);
	public User findUserByOpenid(String openid);
	public Code getCodebyCode(String code);
	public void disableCode(String code);
	public void createToken(TokenInfo info);
	public TokenInfo getTokenByUserId(String userId);
	public TokenInfo getTokenByToken(String token);
	public void addCode(Code code);
	public List<User> list();
	public void disableUser(String id);
	public void enableUser(String id);
	public void increaseGrade(String id);
	public void updateGrade(String id, int grade);
	public void updateHeader(String id, String header);
	public UserAlbumCount albumCounts(String id) ;
}
