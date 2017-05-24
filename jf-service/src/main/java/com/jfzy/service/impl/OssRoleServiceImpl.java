package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.OssRoleService;
import com.jfzy.service.bo.RoleBo;
import com.jfzy.service.po.RolePo;
import com.jfzy.service.repository.OssRoleRepository;

public class OssRoleServiceImpl implements OssRoleService {

	@Autowired
	private OssRoleRepository roleRepo;

	@Override
	public List<RoleBo> getRoles() {
		List<RolePo> pos = roleRepo.findAll();
		List<RoleBo> results = new ArrayList<RoleBo>();
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	private static RoleBo poToBo(RolePo po) {
		RoleBo bo = new RoleBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

}
