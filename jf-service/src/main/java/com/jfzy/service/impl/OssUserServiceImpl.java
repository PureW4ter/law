package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.bo.UserBo;
import com.jfzy.service.po.OssUserPo;
import com.jfzy.service.po.UserPo;
import com.jfzy.service.repository.OssUserRepository;

@Service
public class OssUserServiceImpl implements OssUserService {

	@Autowired
	private OssUserRepository ossUserPo;

	@Override
	public OssUserBo login(String loginName, String password) {
		List<OssUserPo> users = ossUserPo.findByLoginNameAndPassword(loginName, password);
		if (users != null && users.size() == 1) {
			OssUserPo po = users.get(0);
			return poToBo(po);
		} else {
			return null;
		}
	}

	@Override
	public List<OssUserBo> getOssUsers(Pageable page) {
		Iterable<OssUserPo> values = ossUserPo.findAll(page);
		List<OssUserBo> results = new ArrayList<OssUserBo>();
		values.forEach(po -> results.add(poToBo(po)));
		return results;
	}
	
	private static OssUserBo poToBo(OssUserPo po) {
		OssUserBo bo = new OssUserBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}
}
