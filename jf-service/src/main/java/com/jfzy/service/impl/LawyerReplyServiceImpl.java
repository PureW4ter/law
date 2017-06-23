package com.jfzy.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.LawyerReplyService;
import com.jfzy.service.LawyerService;
import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerReplyBo;
import com.jfzy.service.bo.LawyerReplyStatusEnum;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.LawyerReplyPo;
import com.jfzy.service.repository.LawyerReplyRepository;

@Service
public class LawyerReplyServiceImpl implements LawyerReplyService {

	@Autowired
	private LawyerReplyRepository replyRepo;
	
	@Autowired
	private LawyerService lawyerSerivce;
	
	@Autowired
	private OrderService orderSerivce;

	@Override
	public void createReply(LawyerReplyBo bo) {
		LawyerReplyPo po = boToPo(bo);
		replyRepo.save(po);
	}

	@Override
	public void confirmReply(int orderId) {
		OrderBo obo = orderSerivce.getOrderById(orderId);
		orderSerivce.updateOrderStatus(orderId, OrderStatusEnum.FINISHED_NEEDCONFIRM.getId(), OrderStatusEnum.FINISHED.getId());
		lawyerSerivce.updateFinishedTask(1, obo.getLawyerId());
		lawyerSerivce.updateOnProcessTask(-1, obo.getLawyerId());
		lawyerSerivce.updateFinishedMoney(obo.getOriginPrice(), obo.getLawyerId());
	}
	
	@Override
	public LawyerReplyBo getReply(int orderId) {
		List<LawyerReplyPo> pos = replyRepo.findByOrderId(orderId);
		if (pos != null && pos.size()>0) {
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

	@Override
	public void scoreReply(int replyId, int score) {
		replyRepo.updateScore(LawyerReplyStatusEnum.SCORED.getId(), score, replyId);
	}

}
