package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.QAService;
import com.jfzy.service.bo.QABo;
import com.jfzy.service.bo.QAStatusEnum;
import com.jfzy.service.po.QAPo;
import com.jfzy.service.repository.QARepository;

public class QAServiceImpl implements QAService {

	@Autowired
	private QARepository qaRepo;

	@Override
	public void createQA(QABo bo) {
		qaRepo.save(boToPo(bo));
	}

	@Override
	public List<QABo> getUnprocessedQA(int cityId) {
		List<QAPo> pos = qaRepo.findByCityIdAndStatus(cityId, QAStatusEnum.UNPROCESSED.getId());
		return posToBos(pos);
	}

	@Override
	public void processQA(int[] qaIds) {
		qaRepo.updateStatus(QAStatusEnum.PROCESSED.getId(), qaIds);
	}

	private static QAPo boToPo(QABo bo) {
		QAPo po = new QAPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static QABo poToBo(QAPo po) {
		QABo bo = new QABo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static List<QABo> posToBos(List<QAPo> pos) {
		List<QABo> bos = new ArrayList<QABo>(pos == null ? 0 : pos.size());
		if (pos != null) {
			pos.forEach(po -> bos.add(poToBo(po)));
		}

		return bos;
	}

	@Override
	public List<QABo> getQAByUserId(int userId) {
		List<QAPo> pos = qaRepo.findByUserId(userId);
		return posToBos(pos);
	}

}
