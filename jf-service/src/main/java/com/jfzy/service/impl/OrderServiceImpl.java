package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Service;

import com.jfzy.service.LawyerService;
import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.LawyerStatusEnum;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.OrderPo;
import com.jfzy.service.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private LawyerService lawyerSerivce;

	@Override
	public void createOrder(OrderBo bo) {
		orderRepo.save(boToPo(bo));
	}

	private static OrderPo boToPo(OrderBo bo) {
		OrderPo po = new OrderPo();
		BeanUtils.copyProperties(bo, po);
		return po;
	}

	private static OrderBo poToBo(OrderPo po) {
		OrderBo bo = new OrderBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	@Override
	public void assignOrder(int orderId, int lawyerId, int processorId, String processorName) {

		LawyerBo lawyer = lawyerSerivce.getLawyerById(lawyerId);
		if (lawyer != null && lawyer.getStatus() == LawyerStatusEnum.ACTIVE.getId()) {
			OrderPo po = orderRepo.getOne(orderId);
			if (po == null) {
				throw new JfApplicationRuntimeException(400, "无此订单");
			} else if (po.getCityId() != lawyer.getCityId()) {
				throw new JfApplicationRuntimeException(400, "城市不匹配");
			} else if (po.getStatus() == OrderStatusEnum.NEED_DISPATCH.getId()
					|| po.getStatus() == OrderStatusEnum.DISPATCHED.getId()) {
				po.setLawyerId(lawyerId);
				po.setLawyerName(lawyer.getName());
				po.setLawyerPhoneNum(lawyer.getPhoneNum());
				po.setProcessorId(processorId);
				po.setProcessorName(processorName);
				po.setStatus(OrderStatusEnum.DISPATCHED.getId());
				orderRepo.save(po);
			} else {
				throw new JfApplicationRuntimeException(400, "订单状态错误");
			}
		} else {
			throw new JfApplicationRuntimeException(400, "无此律师或非启用律师");
		}
	}

	@Override
	public OrderBo getOrderById(int id) {
		OrderPo po = orderRepo.findOne(id);
		if (po == null) {
			throw new JfApplicationRuntimeException(400, "此订单不存在");
		} else {
			return poToBo(po);
		}
	}

	@Override
	public OrderBo getOrderBySn(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OrderBo> getOrdersByCityIdAndStatus(int cityId, int status, Pageable page) {
		Page<OrderPo> poPage = orderRepo.findByCityIdAndStatus(cityId, status, page);
		List<OrderBo> bos = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> bos.add(poToBo(po)));
		}
		Page<OrderBo> resultPage = new AggregatedPageImpl<OrderBo>(bos, page, poPage.getTotalElements());

		return resultPage;
	}

	@Override
	public List<OrderBo> getOrdersByUser(int userId, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
