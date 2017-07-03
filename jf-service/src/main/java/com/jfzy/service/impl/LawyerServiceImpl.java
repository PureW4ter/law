package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jfzy.service.LawyerService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.LawyerStatusEnum;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;
import com.jfzy.service.po.LawyerPo;
import com.jfzy.service.po.OssUserPo;
import com.jfzy.service.repository.LawyerRepository;
import com.jfzy.service.repository.OssUserRepository;

@Service
public class LawyerServiceImpl implements LawyerService {

	@Autowired
	private LawyerRepository lawyerRepo;

	@Autowired
	private OssUserRepository userRepo;

	@Override
	public List<LawyerBo> getActiveLawyerByCity(int cityId) {
		List<LawyerPo> pos = lawyerRepo.findByCityIdAndStatus(cityId, LawyerStatusEnum.ACTIVE.getId());
		List<LawyerBo> results = new ArrayList<LawyerBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;

	}

	@Override
	public LawyerBo loginWithPassword(String loginName, String password) {
		String checksum = MD5.MD5Encode(password);

		List<LawyerPo> lawyers = lawyerRepo.findByLoginNameAndPassword(loginName, checksum);
		if (lawyers != null && lawyers.size() == 1) {
			LawyerPo po = lawyers.get(0);
			LawyerBo bo = poToBo(po);
			bo.setPassword("");
			return bo;
		} else {
			return null;
		}
	}

	@Override
	public List<LawyerBo> getLawyerByCity(int cityId) {
		List<LawyerPo> pos = null;
		if (cityId > 0) {
			pos = lawyerRepo.findByCityIdAndStatus(cityId, LawyerStatusEnum.ACTIVE.getId());
		} else {
			pos = lawyerRepo.findByStatus(LawyerStatusEnum.ACTIVE.getId());
		}
		List<LawyerBo> results = new ArrayList<LawyerBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	@Override
	public void create(LawyerBo bo) {
		List<LawyerPo> pos = lawyerRepo.findByPhoneNum(bo.getPhoneNum());
		if (pos != null && pos.size() > 0) {
			throw new JfErrorCodeRuntimeException(400, "律师手机已存在",
					String.format("LAWYER-CREATE:Lawyer phoneNum exist:%s", bo.getPhoneNum()));
		}

		if (StringUtils.isNotBlank(bo.getLoginName())) {
			List<OssUserPo> tmpPos = userRepo.findByLoginName(bo.getLoginName());
			if (tmpPos != null && tmpPos.size() > 0) {
				throw new JfErrorCodeRuntimeException(400, "登录账号已存在",
						String.format("LAWYER-CREATE:Lawyer login name exist in OssUser:%s", bo.getLoginName()));
			}

			pos = lawyerRepo.findByLoginName(bo.getLoginName());
			if (pos != null && pos.size() > 0) {
				throw new JfErrorCodeRuntimeException(400, "登录账号已存在",
						String.format("LAWYER-CREATE:Lawyer login name exist:%s", bo.getLoginName()));
			}
		}

		bo.setPassword(MD5.MD5Encode(bo.getPassword()));
		lawyerRepo.save(boToPo(bo));
	}

	@Override
	public void updateLawyerStatus(int id, int status) {
		lawyerRepo.updateStatus(status, new Timestamp(System.currentTimeMillis()), id);
	}

	@Override
	public LawyerBo getLawyerById(int id) {
		LawyerPo po = lawyerRepo.findOne(id);
		return poToBo(po);
	}

	@Override
	public List<LawyerBo> getLawyers(Pageable page) {
		Iterable<LawyerPo> values = lawyerRepo.findAll(page);
		List<LawyerBo> results = new ArrayList<LawyerBo>();
		values.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	@Override
	public void updateFinishedTask(int count, int id) {
		lawyerRepo.updateFinishedTask(count, id);

	}

	@Override
	public void updateOnProcessTask(int count, int id) {
		lawyerRepo.updateOnProcessTask(count, id);

	}

	@Override
	public void updateTotalMoney(double money, int id) {
		lawyerRepo.updateTotalMoney(money, id);

	}

	@Override
	public void updateFinishedMoney(double money, int id) {
		lawyerRepo.updateFinishedMoney(money, id);

	}

	@Override
	public void addLaywerTask(double money, int id) {
		lawyerRepo.updateTotalMoney(money, id);
	}

	private static LawyerBo poToBo(LawyerPo po) {
		LawyerBo bo = new LawyerBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static LawyerPo boToPo(LawyerBo bo) {
		LawyerPo po = new LawyerPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	@Override
	public void updateLawyerScore(int lawyerId, double score) {
		lawyerRepo.updateLawyerScore(score, lawyerId);
	}

	@Override
	public void update(LawyerBo bo) {
		LawyerPo po = lawyerRepo.findOne(bo.getId());
		if (po != null) {
			if (!StringUtils.equals(bo.getPhoneNum(), po.getPhoneNum())) {
				checkLawyerPhoneNum(bo.getId(), bo.getPhoneNum());
			}

			if (!StringUtils.equals(bo.getLoginName(), po.getLoginName())) {
				checkLoginName(bo.getId(), bo.getLoginName());
			}

			if (!StringUtils.isBlank(bo.getPassword())) {
				String checkSum = MD5.MD5Encode(bo.getPassword());
				po.setPassword(checkSum);
			}

			po.setCityId(bo.getCityId());
			po.setLoginName(bo.getLoginName());
			po.setMemo(bo.getMemo());
			po.setName(bo.getName());

			lawyerRepo.save(po);
		}
	}

	private void checkLoginName(int id, String loginName) {
		List<LawyerPo> pos = lawyerRepo.findByLoginName(loginName);
		if (pos == null) {

		} else if (pos.size() == 1) {
			if (pos.get(0).getId() != id) {
				throw new JfErrorCodeRuntimeException(400, "律师登录名已存在",
						String.format("LAWYER-CREATE:Lawyer loginName exist:%s", loginName));
			}
		} else if (pos.size() > 1) {
			throw new JfErrorCodeRuntimeException(400, "律师登录名已存在",
					String.format("LAWYER-CREATE:Lawyer loginName exist:%s", loginName));
		}

		List<OssUserPo> userPos = userRepo.findByLoginName(loginName);
		if (userPos != null && userPos.size() > 0) {

			throw new JfErrorCodeRuntimeException(400, "律师登录名已存在",
					String.format("LAWYER-CREATE:Lawyer loginName exist:%s", loginName));
		}
	}

	private void checkLawyerPhoneNum(int id, String phoneNum) {
		List<LawyerPo> pos = lawyerRepo.findByPhoneNum(phoneNum);
		if (pos == null) {

		} else if (pos.size() == 1) {
			if (pos.get(0).getId() != id) {
				throw new JfErrorCodeRuntimeException(400, "律师手机已存在",
						String.format("LAWYER-CREATE:Lawyer phoneNum exist:%s", phoneNum));
			}
		} else if (pos.size() > 1) {
			throw new JfErrorCodeRuntimeException(400, "律师手机已存在",
					String.format("LAWYER-CREATE:Lawyer phoneNum exist:%s", phoneNum));
		}
	}
}
