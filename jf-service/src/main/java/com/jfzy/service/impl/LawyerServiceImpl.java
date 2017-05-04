package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.LawyerService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.LawyerStatusEnum;
import com.jfzy.service.po.LawyerPo;
import com.jfzy.service.repository.LawyerRepository;

@Service
public class LawyerServiceImpl implements LawyerService {

	@Autowired
	private LawyerRepository lawyerRepo;

	@Override
	public List<LawyerBo> getActiveLawyerByCity(int cityId) {
		List<LawyerPo> pos = lawyerRepo.findByCityIdAndStatus(cityId, LawyerStatusEnum.ACTIVE.getId());
		List<LawyerBo> results = new ArrayList<LawyerBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;

	}

	private static LawyerBo poToBo(LawyerPo po) {
		LawyerBo bo = new LawyerBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

}
