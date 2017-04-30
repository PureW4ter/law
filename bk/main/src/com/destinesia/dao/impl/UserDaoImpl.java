package com.destinesia.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.destinesia.dao.IUserDao;
import com.destinesia.entity.Code;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.UserAlbumCount;

@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements IUserDao{

	@Override
	public void regist(User user) {
		this.getSqlSession().insert("UserSpace.addUser", user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(String phone, String password) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("phone", phone);
		param.put("password", password);
		this.getSqlSession().update("UserSpace.updatePassword", param);
	}
	
	@Override
	public User findUserById(String id) {
		return this.getSqlSession().selectOne("UserSpace.findUserById", id);
	}
	
	@Override
	public User findUserByPhone(String phone) {
		return  this.getSqlSession().selectOne("UserSpace.findUserByPhone", phone);
	}

	@Override
	public User findUserByOpenid(String openid) {
		return  this.getSqlSession().selectOne("UserSpace.findUserByOpenid", openid);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return  this.getSqlSession().selectOne("UserSpace.findUserByEmail", email);
	}

	@Override
	public User findUserByNickName(String nickName) {
		return this.getSqlSession().selectOne("UserSpace.findUserByNickname", nickName);
	}

	@Override
	public Code getCodebyCode(String code) {
		return this.getSqlSession().selectOne("UserSpace.getCodeByCode", code);
	}
	
	@Override
	public void disableCode(String code) {
		 this.getSqlSession().update("UserSpace.disableCode", code);
	}

	@Override
	public void createToken(TokenInfo info) {
		this.getSqlSession().insert("UserSpace.addTokeninfo", info);
	}
	
	@Override
	public TokenInfo getTokenByUserId(String userId) {
		return this.getSqlSession().selectOne("UserSpace.getTokenByUserId", userId);
	}
	
	@Override
	public TokenInfo getTokenByToken(String token) {
		return this.getSqlSession().selectOne("UserSpace.getTokenByToken", token);
	}
	
	@Override
	public void addCode(Code code) {
		this.getSqlSession().insert("UserSpace.addCode", code);
	}
	
	@Override
	public List<User> list() {
		return this.getSqlSession().selectList("UserSpace.list");
	}
	
	@Override
	public void disableUser(String id) {
		this.getSqlSession().selectList("UserSpace.disableUser", id);
		
	}

	@Override
	public void enableUser(String id) {
		this.getSqlSession().selectList("UserSpace.enableUser", id);
	}

	@Override
	public void increaseGrade(String id) {
		this.getSqlSession().selectList("UserSpace.increaseGrade", id);
	}

	@Override
	public void updateGrade(String id, int grade) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("grade", grade);
		this.getSqlSession().selectList("UserSpace.updateGrade", param);
	}

	@Override
	public void updateHeader(String id, String header) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("header", header);
		this.getSqlSession().update("UserSpace.updateHeader", param);
	}
	
	@Override
	public UserAlbumCount albumCounts(String id) {
		return this.getSqlSession().selectOne("AlbumSpace.albumCounts", id);
	}
	
	@Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
        super.setSqlSessionFactory(sqlSessionFactory);  
    }
}
