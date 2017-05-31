package com.jfzy.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.OssUserPo;
import com.jfzy.service.repository.OssUserRepository;

@Service
public class OssUserServiceImpl implements OssUserService {

	@Autowired
	private OssUserRepository ossUserRepo;

	@Override
	public OssUserBo login(String loginName, String password) {
		String checksum = getMd5(password);

		List<OssUserPo> users = ossUserRepo.findByLoginNameAndPassword(loginName, checksum);
		if (users != null && users.size() == 1) {
			OssUserPo po = users.get(0);
			return poToBo(po);
		} else {
			return null;
		}
	}

	@Override
	public List<OssUserBo> getOssUsers(Pageable page) {
		Iterable<OssUserPo> values = ossUserRepo.findAll(page);
		List<OssUserBo> results = new ArrayList<OssUserBo>();
		values.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	private static OssUserBo poToBo(OssUserPo po) {
		OssUserBo bo = new OssUserBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static OssUserPo boToPo(OssUserBo bo) {
		OssUserPo po = new OssUserPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	@Override
	public void createUser(OssUserBo bo) {
		if (StringUtils.isBlank(bo.getLoginName()) || StringUtils.isBlank(bo.getPassword())) {
			throw new JfApplicationRuntimeException("用户名或密码不能为空");
		}

		List<OssUserPo> pos = ossUserRepo.findByLoginName(bo.getLoginName());
		if (pos != null && pos.size() > 0) {
			throw new JfApplicationRuntimeException("用户名已存在");
		}

		bo.setPassword(getMd5(bo.getPassword()));
		OssUserPo po = boToPo(bo);
		ossUserRepo.save(po);
	}

	private static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			return new String(new BigInteger(1, md.digest()).toString());
		} catch (NoSuchAlgorithmException e) {
			throw new JfApplicationRuntimeException(404, "Failed in getMd5");
		}
	}
}
