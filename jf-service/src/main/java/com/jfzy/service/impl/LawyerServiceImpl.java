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

	private static LawyerPo boToPo(LawyerBo bo) {
		LawyerPo po = new LawyerPo();
		BeanUtils.copyProperties(bo, po);
		po.setId(0);
		return po;
	}

	@Override
	public List<LawyerBo> getLawyerByCity(int cityId) {

		List<LawyerPo> pos = lawyerRepo.findByCityId(cityId);
		List<LawyerBo> results = new ArrayList<LawyerBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;

	}

	@Override
	public void createLawyer(LawyerBo bo) {
		lawyerRepo.save(boToPo(bo));
	}

	@Override
	public void updateLawyer(LawyerBo bo) {
		LawyerPo po = lawyerRepo.findOne(bo.getId());
		if (po != null) {
			BeanUtils.copyProperties(bo, po);
			lawyerRepo.save(po);
		}
	}

	@Override
	public void updateLawyerStatus(int lawyerId, int status) {
		lawyerRepo.updateStatus(lawyerId, status);
	}

	@Override
	public LawyerBo getLawyerById(int id) {
		LawyerPo po = lawyerRepo.getOne(id);
		return poToBo(po);
	}

}
