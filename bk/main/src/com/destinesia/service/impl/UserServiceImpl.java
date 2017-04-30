package com.destinesia.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destinesia.base.StatusMsg;
import com.destinesia.base.Utility;
import com.destinesia.dao.IUserDao;
import com.destinesia.entity.Code;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.UserAlbumCount;
import com.destinesia.entity.dto.LoginReturnDTO;
import com.destinesia.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;
	
	@Override
	public TokenInfo regist(User user, String passcode) {
		/*if(user.getMail()!=null){
			User existUser = userDao.findUserByEmail(user.getMail());
			if(existUser != null){
				throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "mail");
			}
		}
		if(user.getNickName()!=null){
			User existUser = userDao.findUserByNickName(user.getNickName());
			if(existUser != null){
				throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "nickName");
			}
		}*/
		if(user.getPhone()!=null){
			User existUser = userDao.findUserByPhone(user.getPhone());
			if(existUser != null){
				throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "已存在关联此号码的账号，请尝试");
			}
		}
		
		if(Utility.smsMap.get(user.getPhone()) ==null || !Utility.smsMap.get(user.getPhone()).getCode().equals(passcode)){
			throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码不正确，请重新输入");
		}else{
			Utility.smsMap.remove(user.getPhone());
			long createTime = Utility.smsMap.get(user.getPhone()).getCreateTime();
			if(new Date().getTime()-createTime>15*50*1000){
				throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码输入超时，请重新输入");
			}
		}
		
		//等级
		Code code = null;
		if(user.getRecommend()!=null){
			code = userDao.getCodebyCode(user.getRecommend());
			//激活码无效
			userDao.disableCode(user.getRecommend());
		}
		if(code != null){
			user.setGrade(code.getGrade());
		}
		userDao.regist(user);
		//创建token
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setId(Utility.generageId());
		tokenInfo.setUserId(user.getId());
		tokenInfo.setValue(Utility.generageId());
		userDao.createToken(tokenInfo);
		return tokenInfo;
	}
	
	@Override
	public TokenInfo wxregist(User user) {
		User existUser = null;
		if(user.getOpenid()!=null){
			existUser = userDao.findUserByOpenid(user.getOpenid());
			if(existUser == null){
				userDao.regist(user);
			}
		}
		
		//创建token
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setId(Utility.generageId());
		tokenInfo.setUserId(existUser==null?user.getId():existUser.getId());
		tokenInfo.setValue(Utility.generageId());
		if(existUser == null){
			tokenInfo.setNew(true);
		}
		userDao.createToken(tokenInfo);
		return tokenInfo;
	}
	
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resetPassword(String phone, String newPassword, String passcode) {
		User existUser = userDao.findUserByPhone(phone);
		if(existUser == null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "没有关联此号码的账号，请尝试更换");
		}
		if(Utility.smsMap.get(phone) !=null && !Utility.smsMap.get(phone).getCode().equals(passcode)){
			Utility.smsMap.remove(phone);
			long createTime = Utility.smsMap.get(phone).getCreateTime();
			if(new Date().getTime()-createTime>15*50*1000){
				throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码输入超时，请重新输入");
			}
			userDao.updatePassword(phone, newPassword);
		}else{
			throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码不正确，请重新输入");
		}
		
	}
	
	@Override
	public LoginReturnDTO login(String key, String password) {
		User user;
		TokenInfo tokenInfo = null;
		if(key.indexOf('@')>0){
			user = userDao.findUserByEmail(key);
		}else{
			user = userDao.findUserByPhone(key);
		}
		if(user!=null && user.getPassword().equals(password)){
			tokenInfo = userDao.getTokenByUserId(user.getId());
			if(tokenInfo==null){
				tokenInfo = new TokenInfo();
				tokenInfo.setId(Utility.generageId());
				tokenInfo.setUserId(user.getId());
				tokenInfo.setValue(Utility.generageId());
				userDao.createToken(tokenInfo);
			}
		}
		LoginReturnDTO loginReturnDTO = null;
		if(user !=null && tokenInfo!=null){
			loginReturnDTO = new LoginReturnDTO();
			loginReturnDTO.setGrade(user.getGrade());
			loginReturnDTO.setNickName(user.getNickName());
			loginReturnDTO.setToken(tokenInfo.getValue());
		}
		return loginReturnDTO;
	}

	@Override
	public LoginReturnDTO loginbyphone(String phone, String passcode) {
		User user = userDao.findUserByPhone(phone);
		TokenInfo tokenInfo = null;
		tokenInfo = userDao.getTokenByUserId(user.getId());
		if(tokenInfo==null){
			tokenInfo = new TokenInfo();
			tokenInfo.setId(Utility.generageId());
			tokenInfo.setUserId(user.getId());
			tokenInfo.setValue(Utility.generageId());
			userDao.createToken(tokenInfo);
		}
		LoginReturnDTO loginReturnDTO = null;
		if(user !=null && tokenInfo!=null){
			loginReturnDTO = new LoginReturnDTO();
			loginReturnDTO.setGrade(user.getGrade());
			loginReturnDTO.setNickName(user.getNickName());
			loginReturnDTO.setToken(tokenInfo.getValue());
		}
		return loginReturnDTO;
	}
	
	@Override
	public int getGrade(String codeS) {
		Code code = userDao.getCodebyCode(codeS);
		if(code !=null){
			if(code.isValidate())
				return code.getGrade();
			else
				throw new StatusMsg(StatusMsg.CODE_USED_RECOMMEND, "该邀请码已使用，请尝试更换一个");
		}else{
			throw new StatusMsg(StatusMsg.CODE_NOT_EXSIT_RECOMMEND, "邀请码似乎不正确，请尝试重新输入");
		}
	}

	@Override
	public List<Code> generateCode(int count, int grade, String identity) {
		List<Code> codeList = new ArrayList<Code>();
		for(int i=0; i<count; i++){
			Code code = new Code();
			code.setId(Utility.generageId());
			code.setIdentity(identity);
			code.setGrade(grade);
			code.setCode(Utility.getRandomString(8));
			code.setFromTime(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.YEAR, 1);
			code.setToTime(cal.getTime());
			codeList.add(code);
			userDao.addCode(code);
		}
		return codeList;
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public User findUserByPhone(String phone) {
		return userDao.findUserByPhone(phone);
	}
	
	@Override
	public User findUserByNickName(String nickName) {
		return userDao.findUserByNickName(nickName);
	}

	@Override
	public List<User> list(String token) {
		return userDao.list();
	}

	@Override
	public void disableUser(String id, String token) {
		userDao.disableUser(id);
	}

	@Override
	public void enableUser(String id, String token) {
		userDao.enableUser(id);
	}

	@Override
	public void increaseGrade(String id) {
		userDao.increaseGrade(id);
	}

	@Override
	public int updateUserGrade(String token, String recommend) {
		Code code = userDao.getCodebyCode(recommend);
		if(code == null){
			throw new StatusMsg(StatusMsg.CODE_FIELD_INVALID, "邀请码似乎不正确，请尝试重新输入");
		}
		TokenInfo tokenu = userDao.getTokenByToken(token);
		User user = userDao.findUserById((tokenu.getUserId()));
		if(code.getGrade()<user.getGrade()){
			return user.getGrade();
		}
		userDao.updateGrade(user.getId(), code.getGrade());
		userDao.disableCode(recommend);
		user.setGrade(code.getGrade());
		return code.getGrade();
	}

	@Override
	public void updateHeader(String token, String header) {
		TokenInfo tokenu = userDao.getTokenByToken(token);
		User user = userDao.findUserById((tokenu.getUserId()));
		userDao.updateHeader(user.getId(), header);
	}

	@Override
	public UserAlbumCount albumCounts(String token) {
		TokenInfo tokenu = userDao.getTokenByToken(token);
		User user = userDao.findUserById((tokenu.getUserId()));
		return userDao.albumCounts(user.getId());
	}
}
