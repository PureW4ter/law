package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.UserService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.bo.StatusEnum;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;
import com.jfzy.service.bo.UserLevelEnum;
import com.jfzy.service.po.ArticlePo;
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
	public UserBo createOrUpdateUser(UserBo user) {
		UserPo userPo = bo2PoForUser(user);
		UserPo po = userRepo.save(userPo);
		return po2BoForUser(po);
	}
	
	@Override
	public void register(UserAccountBo ua){
		UserAccountPo userAccountPo = bo2PoForUserAccount(ua);
		userAccountRepo.save(userAccountPo);
		UserBo bo = this.getUser(ua.getUserId());
		bo.setLevel(UserLevelEnum.NORMAL.getId());
		userRepo.save(bo2PoForUser(bo));
	}


	@Override
	public void bind(String phone, int userId) {
		UserAccountBo bo = new UserAccountBo();
		bo.setUserId(userId);
		bo.setStatus(StatusEnum.ENABLED.getId());
		bo.setType(UserAccountTypeEnum.MOBILE.getId());
		bo.setValue(phone);
		bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
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
		if(po!=null)
			return po2BoForUserAccount(po);
		return null;
	}
	
	@Override
	public UserAccountBo getUserAccountByUserId(int userId, int type) {
		UserAccountPo po = userAccountRepo.getByUserid(userId, type);
		if(po!=null)
			return po2BoForUserAccount(po);
		return null;
	}
	
	@Override
	public void create(UserBo bo) {
		UserPo po = bo2PoForUser(bo);
		userRepo.save(po);
	}

	@Override
	public void updateMemo(String memo, int id) {
		userRepo.updateMemo(memo, id);
	}
	
	private static UserPo bo2PoForUser(UserBo bo) {
		UserPo po = new UserPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static UserBo po2BoForUser(UserPo po) {
		UserBo bo = new UserBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}
	
	private static UserAccountPo bo2PoForUserAccount(UserAccountBo bo) {
		UserAccountPo po = new UserAccountPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static UserAccountBo po2BoForUserAccount(UserAccountPo po) {
		UserAccountBo bo = new UserAccountBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}
}
