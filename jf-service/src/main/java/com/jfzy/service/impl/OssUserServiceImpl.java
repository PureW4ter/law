package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.exception.JfApplicationRuntimeException;
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
	public OssUserBo login(String phoneNum, String code) {
		//FIXME 
		// code check
		List<OssUserPo> users = ossUserRepo.findByPhoneNum(phoneNum);
		List<LawyerPo> lawyers = lawyerRepo.findByPhoneNum(phoneNum);
		
		if (users != null && users.size() == 1) {
			OssUserPo po = users.get(0);
			OssUserBo bo = poToBo(po);
			return bo;
		} else if(lawyers != null && lawyers.size() == 1){
			LawyerPo po = lawyers.get(0);
			OssUserBo bo = poToBo(po);
			return bo;
		}else {
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
			throw new JfApplicationRuntimeException("用户手机已存在");
		}
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

	public static void main(String[] args) {
		System.out.println(MD5.MD5Encode("123456789."));
	}
}
