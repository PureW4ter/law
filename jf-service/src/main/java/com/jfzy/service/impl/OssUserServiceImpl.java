package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.bo.OssUserTypeEnum;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;
import com.jfzy.service.po.LawyerPo;
import com.jfzy.service.po.OssUserPo;
import com.jfzy.service.repository.LawyerRepository;
import com.jfzy.service.repository.OssUserRepository;

@Service
public class OssUserServiceImpl implements OssUserService {

	@Autowired
	private OssUserRepository ossUserRepo;

	@Autowired
	private LawyerRepository lawyerRepo;

	@Override
	public OssUserBo loginWithPassword(String loginName, String password) {
		String checksum = MD5.MD5Encode(password);

		List<OssUserPo> users = ossUserRepo.findByLoginNameAndPassword(loginName, checksum);
		if (users != null && users.size() == 1) {
			OssUserPo po = users.get(0);
			OssUserBo bo = poToBo(po);
			bo.setPassword("");
			return bo;
		} else {
			return null;
		}
	}

	@Override
	public OssUserBo login(String userName, String password) {
		String checksum = MD5.MD5Encode(password);

		List<OssUserPo> users = ossUserRepo.findByLoginNameAndPassword(userName, checksum);
		List<LawyerPo> lawyers = lawyerRepo.findByLoginNameAndPassword(userName, checksum);

		if (users != null && users.size() == 1) {
			OssUserPo po = users.get(0);
			OssUserBo bo = poToBo(po);
			bo.setType(OssUserTypeEnum.USER.getId());
			return bo;
		} else if (lawyers != null && lawyers.size() == 1) {
			LawyerPo po = lawyers.get(0);
			OssUserBo bo = poToBo(po);
			bo.setRole("lawyer");
			bo.setType(OssUserTypeEnum.LAWYER.getId());
			return bo;
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

	@Override
	public void updateStatus(int status, int id) {
		ossUserRepo.updateStatus(status, new Timestamp(System.currentTimeMillis()), id);

	}

	@Override
	public void updateAuth(String role, int id) {
		ossUserRepo.updateAuth(role, new Timestamp(System.currentTimeMillis()), id);
	}

	@Override
	public void create(OssUserBo bo) {
		List<OssUserPo> pos = ossUserRepo.findByPhoneNum(bo.getPhoneNum());

		if (pos != null && pos.size() > 0) {
			throw new JfErrorCodeRuntimeException(400, "用户手机已存在",
					String.format("OSSUSER-CREATE:PhoneNum exists:%s", bo.getPhoneNum()));
		}
		pos = ossUserRepo.findByLoginName(bo.getLoginName());
		if (pos != null && pos.size() > 0) {
			throw new JfErrorCodeRuntimeException(400, "用户名已经存在",
					String.format("OSSUSER-CREATE:Login name exists:%s", bo.getLoginName()));
		}

		bo.setPassword(MD5.MD5Encode(bo.getPassword()));
		OssUserPo po = boToPo(bo);
		ossUserRepo.save(po);
	}

	private static OssUserBo poToBo(OssUserPo po) {
		OssUserBo bo = new OssUserBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static OssUserBo poToBo(LawyerPo po) {
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
	public OssUserBo getUserById(int id) {
		OssUserPo po = ossUserRepo.findOne(id);
		OssUserBo bo = poToBo(po);
		return bo;
	}

	@Override
	public void resetPassword(int userId, boolean isLawyer, String oldPwd, String newPwd) {
		if (isLawyer) {
			resetLawyerPassword(userId, oldPwd, newPwd);
		} else {
			resetOssUserPassword(userId, oldPwd, newPwd);
		}
	}

	private void resetOssUserPassword(int userId, String oldPwd, String newPwd) {
		OssUserPo po = ossUserRepo.findOne(userId);
		if (po != null) {
			String oldChecksum = MD5.MD5Encode(oldPwd);
			String newChecksum = MD5.MD5Encode(newPwd);
			if (StringUtils.equals(oldChecksum, po.getPassword())) {
				ossUserRepo.updatePassword(userId, oldChecksum, newChecksum);
			} else {
				throw new JfErrorCodeRuntimeException(400, "旧密码不匹配",
						String.format("OSSUSER-RESETPWD:User old password not match for id:%s", userId));
			}
		} else {
			throw new JfErrorCodeRuntimeException(400, "员工账号不存在",
					String.format("OSSUSER-RESETPWD:User not exists with id:%s", userId));
		}
	}

	private void resetLawyerPassword(int userId, String oldPwd, String newPwd) {
		LawyerPo po = lawyerRepo.findOne(userId);
		if (po != null) {
			String oldChecksum = MD5.MD5Encode(oldPwd);
			String newChecksum = MD5.MD5Encode(newPwd);
			if (StringUtils.equals(oldChecksum, po.getPassword())) {
				lawyerRepo.updatePwd(userId, oldChecksum, newChecksum);
			} else {
				throw new JfErrorCodeRuntimeException(400, "旧密码不匹配",
						String.format("OSSUSER-RESETPWD:Lawyer old password not match for id:%s", userId));
			}
		} else {
			throw new JfErrorCodeRuntimeException(400, "律师账号不存在",
					String.format("OSSUSER-RESETPWD:Lawyer not exists with id:%s", userId));
		}

	}
}