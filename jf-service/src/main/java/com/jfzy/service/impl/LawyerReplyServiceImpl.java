package com.jfzy.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.LawyerReplyService;
import com.jfzy.service.bo.LawyerReplyBo;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.LawyerReplyPo;
import com.jfzy.service.repository.LawyerReplyRepository;

public class LawyerReplyServiceImpl implements LawyerReplyService {

	@Autowired
	private LawyerReplyRepository replyRepo;

	@Override
	public void createReply(LawyerReplyBo bo) {
		List<LawyerReplyPo> pos = replyRepo.findByOrderId(bo.getOrderId());
		if (pos != null && pos.size() > 0) {
			throw new JfApplicationRuntimeException("律师回复已经存在");
		} else {
			LawyerReplyPo po = boToPo(bo);
			replyRepo.save(po);
		}
	}

	@Override
	public void updateReply(LawyerReplyBo bo) {
		LawyerReplyPo po = replyRepo.getOne(bo.getId());
		if (po == null) {
			throw new JfApplicationRuntimeException("律师回复不存在");
		} else {
			if (StringUtils.isNoneBlank(bo.getBuzhou())) {
				po.setBuzhou(bo.getBuzhou());
			}
			if (StringUtils.isNotBlank(bo.getSimpleReply())) {
				po.setSimpleReply(bo.getSimpleReply());
			}
			if (StringUtils.isNotBlank(bo.getTishi())) {
				po.setTishi(bo.getTishi());
			}
			if (StringUtils.isNotBlank(bo.getXingwei())) {
				po.setXingwei(bo.getXingwei());
			}
			if (StringUtils.isNotBlank(bo.getYupan())) {
				po.setYupan(bo.getYupan());
			}

			replyRepo.save(po);
		}
	}

	@Override
	public LawyerReplyBo getReply(int orderId) {
		List<LawyerReplyPo> pos = replyRepo.findByOrderId(orderId);
		if (pos != null) {
			return poToBo(pos.get(0));
		} else {
			return null;
		}
	}

	private static LawyerReplyBo poToBo(LawyerReplyPo po) {
		LawyerReplyBo bo = new LawyerReplyBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	private static LawyerReplyPo boToPo(LawyerReplyBo bo) {
		LawyerReplyPo po = new LawyerReplyPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

}
