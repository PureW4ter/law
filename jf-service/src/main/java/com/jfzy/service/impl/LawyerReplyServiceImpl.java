package com.jfzy.service.impl;

import java.sql.Timestamp;
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
import com.jfzy.service.bo.OrderPhotoBo;
import com.jfzy.service.bo.OrderPhotoTypeEnum;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.LawyerReplyPo;
import com.jfzy.service.po.OrderPhotoPo;
import com.jfzy.service.repository.LawyerReplyRepository;
import com.jfzy.service.repository.OrderPhotoRepository;

@Service
public class LawyerReplyServiceImpl implements LawyerReplyService {

	@Autowired
	private LawyerReplyRepository replyRepo;

	@Autowired
	private OrderPhotoRepository orderPhotoRepo;

	@Autowired
	private LawyerService lawyerSerivce;

	@Autowired
	private OrderService orderSerivce;

	@Override
	public void createReply(LawyerReplyBo bo) {
		LawyerReplyPo po = boToPo(bo);
		replyRepo.save(po);
		orderSerivce.updateOrderStatus(bo.getOrderId(), OrderStatusEnum.FINISHED_NEEDCONFIRM.getId());
	}

	@Override
	public void confirmReply(int orderId) {
		OrderBo obo = orderSerivce.getOrderById(orderId);
		orderSerivce.updateOrderStatus(orderId, OrderStatusEnum.FINISHED.getId());
		lawyerSerivce.updateFinishedTask(1, obo.getLawyerId());
		lawyerSerivce.updateOnProcessTask(-1, obo.getLawyerId());
		lawyerSerivce.updateFinishedMoney(obo.getOriginPrice(), obo.getLawyerId());
	}

	@Override
	public LawyerReplyBo getReply(int orderId) {
		List<LawyerReplyPo> pos = replyRepo.findByOrderId(orderId);
		if (pos != null && pos.size() > 0) {
			return poToBo(pos.get(0));
		} else {
			return null;
		}
	}

	@Override
	public void scoreReply(int replyId, int score) {
		replyRepo.updateScore(LawyerReplyStatusEnum.SCORED.getId(), score, replyId);
	}

	@Override
	public void addReplyPhotos(String[] picList, int orderId) {
		OrderPhotoBo bo = new OrderPhotoBo();
		if (picList != null && picList.length > 0) {
			for (int i = 0; i < picList.length; i++) {
				bo.setOrderId(orderId);
				bo.setPhotoPath(picList[i]);
				bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				bo.setType(OrderPhotoTypeEnum.REPLY.getId());
				orderPhotoRepo.save(boToPo(bo));
			}
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

	private static OrderPhotoPo boToPo(OrderPhotoBo bo) {
		OrderPhotoPo po = new OrderPhotoPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static OrderPhotoBo poToBo(OrderPhotoPo po) {
		OrderPhotoBo bo = new OrderPhotoBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}
}
