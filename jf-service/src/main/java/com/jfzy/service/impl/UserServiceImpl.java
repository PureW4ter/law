package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.UserService;
import com.jfzy.service.bo.StatusEnum;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;
import com.jfzy.service.po.UserAccountPo;
import com.jfzy.service.po.UserPo;
import com.jfzy.service.repository.UserAccountRepository;
import com.jfzy.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserAccountRepository userAccountRepo;

	@Override
	public int createOrUpdateUser(UserBo user) {
		UserPo userPo = bo2PoForUser(user);
		UserPo po = userRepo.save(userPo);
		return po.getId();
	}
	
	@Override
	public void register(UserAccountBo ua){
		UserAccountPo userAccountPo = bo2PoForUserAccount(ua);
		userAccountRepo.save(userAccountPo);
	}


	@Override
	public void bind(String phone, int userId) {
		UserAccountBo bo = new UserAccountBo();
		bo.setUserId(userId);
		bo.setStatus(StatusEnum.ENABLED.getId());
		bo.setType(UserAccountTypeEnum.MOBILE.getId());
		bo.setValue(phone);
		this.register(bo);
	}
	
	@Override
	public void unbind(int userAccountId) {
		userAccountRepo.updateDeleted(userAccountId);
	}
	
	@Override
	public List<UserBo> getUsers(Pageable page) {
		Iterable<UserPo> values = userRepo.findAll(page);
		List<UserBo> results = new ArrayList<UserBo>();
		values.forEach(po -> results.add(po2BoForUser(po)));
		return results;
	}

	@Override
	public UserBo getUser(int id) {
		UserPo po = userRepo.getOne(id);
		return po2BoForUser(po);
	}
	
	@Override
	public UserAccountBo getUserAccountByOpenid(String openid) {
		UserAccountPo po = userAccountRepo.getByOpenid(openid);
		return po2BoForUserAccount(po);
	}
	
	private static UserPo bo2PoForUser(UserBo bo) {
		UserPo po = new UserPo();
		po.setId(bo.getId());
		po.setAddress(bo.getAddress());
		po.setCreateTime(bo.getCreateTime());
		po.setHeadImg(bo.getHeadImg());
		po.setMemo(bo.getMemo());
		po.setName(bo.getName());
		po.setPostcode(bo.getPostcode());
		po.setRealName(bo.getRealName());
		po.setStatus(bo.getStatus());
		po.setGender(bo.getGender());
		po.setCity(bo.getCity());
		return po;
	}

	private static UserBo po2BoForUser(UserPo po) {
		UserBo result = new UserBo();
		result.setId(po.getId());
		result.setAddress(po.getAddress());
		result.setCreateTime(po.getCreateTime());
		result.setHeadImg(po.getHeadImg());
		result.setMemo(po.getMemo());
		result.setName(po.getName());
		result.setPostcode(po.getPostcode());
		result.setRealName(po.getRealName());
		result.setStatus(po.getStatus());
		result.setGender(po.getGender());
		result.setCity(po.getCity());
		return result;
	}
	
	private static UserAccountPo bo2PoForUserAccount(UserAccountBo bo) {
		UserAccountPo po = new UserAccountPo();
		po.setCreateTime(bo.getCreateTime());
		po.setStatus(bo.getStatus());
		po.setType(bo.getType());
		po.setUserId(bo.getUserId());
		po.setValue(bo.getValue());
		return po;
	}

	private static UserAccountBo po2BoForUserAccount(UserAccountPo po) {
		UserAccountBo bo = new UserAccountBo();
		bo.setCreateTime(po.getCreateTime());
		bo.setStatus(po.getStatus());
		bo.setType(po.getType());
		bo.setUserId(po.getUserId());
		bo.setValue(po.getValue());
		return bo;
	}
}
