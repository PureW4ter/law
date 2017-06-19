package com.jfzy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.stereotype.Service;

import com.jfzf.core.Constants;
import com.jfzy.service.LawyerService;
import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.LawyerStatusEnum;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderPayStatusEnum;
import com.jfzy.service.bo.OrderPhotoBo;
import com.jfzy.service.bo.OrderPhotoTypeEnum;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.PayWayEnum;
import com.jfzy.service.bo.WxPayEventBo;
import com.jfzy.service.bo.WxPayResponseDto;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.po.OrderPhotoPo;
import com.jfzy.service.po.OrderPo;
import com.jfzy.service.repository.OrderPhotoRepository;
import com.jfzy.service.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderPhotoRepository orderPhotoRepo;

	@Autowired
	private LawyerService lawyerSerivce;

	@Autowired
	private PaymentService paymentService;

	@Override
	public OrderBo createSOrder(OrderBo bo) {
		if (Constants.PRODUCT_CODE_JIANDANWEN.equals(bo.getProductCode())) {
			bo.setStatus(OrderStatusEnum.NO_PAY.getId());
		} else {
			bo.setStatus(OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId());
		}
		bo.setPayWay(PayWayEnum.NO_PAY.getId());
		OrderPo po = orderRepo.save(boToPo(bo));
		return poToBo(po);
	}

	@Override
	public OrderBo createIOrder(OrderBo bo) {
		if (Constants.PRODUCT_CODE_HUKOU.equals(bo.getProductCode())) {
			bo.setStatus(OrderStatusEnum.NO_PAY.getId());
		} else {
			bo.setStatus(OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId());
		}
		bo.setPayWay(PayWayEnum.NO_PAY.getId());
		OrderPo po = orderRepo.save(boToPo(bo));
		return poToBo(po);
	}

	@Override
	public WxPayResponseDto pay(int id, int userId, String ip, String openId) {
		// FIXED ME
		OrderPo po = orderRepo.findByUserIdAndId(userId, id);
		if (po == null) {
			logger.error(String.format("Order not found : userId %s, orderId %s", userId, id));
			throw new JfApplicationRuntimeException("订单不存在");
		} else if (po.getPayStatus() == OrderPayStatusEnum.PAYED.getId()) {
			throw new JfApplicationRuntimeException("订单已支付");
		} else if (po.getPayStatus() == OrderPayStatusEnum.REFUND.getId()) {
			throw new JfApplicationRuntimeException("订单已退款");
		} else {
			return paymentService.unifiedOrder(poToBo(po), ip, openId);
		}
	}

	@Override
	public void cancel(int id) {
		orderRepo.updateStatus(OrderStatusEnum.CANCELED.getId(), new Timestamp(System.currentTimeMillis()), id);
	}

	@Override
	public void complete(int id, String memo, String[] picList) {
		OrderPhotoBo bo = new OrderPhotoBo();
		for (int i = 0; i < picList.length; i++) {
			bo.setOrderId(id);
			bo.setPhotoPath(picList[i]);
			bo.setCreateTime(new Timestamp(System.currentTimeMillis()));
			bo.setType(OrderPhotoTypeEnum.ORDER.getId());
			orderPhotoRepo.save(boToPo(bo));
		}
		orderRepo.updateMemo(memo, new Timestamp(System.currentTimeMillis()), id);
		orderRepo.updateStatus(OrderStatusEnum.NEED_DISPATCH.getId(), new Timestamp(System.currentTimeMillis()), id);

		OrderBo obo = getOrderById(id);
		if (Constants.PRODUCT_CODE_ZIXUNP.equals(obo.getProductCode())) {
			orderRepo.setStartAndEndTime(new Timestamp(System.currentTimeMillis()),
					new Timestamp(System.currentTimeMillis() + 2 * 60 * 60 * 1000),
					new Timestamp(System.currentTimeMillis()), id);
		} else if (Constants.PRODUCT_CODE_ZIXUN.equals(obo.getProductCode())) {
			orderRepo.setStartAndEndTime(new Timestamp(System.currentTimeMillis()),
					new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
					new Timestamp(System.currentTimeMillis()), id);
		}
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
		Page<OrderPo> poPage = orderRepo.findByUserId(userId, page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getSearchOrders(Pageable page) {
		Page<OrderPo> poPage = orderRepo.getSearchOrders(page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public List<OrderBo> getInvestOrders(Pageable page) {
		Page<OrderPo> poPage = orderRepo.getInvestOrders(page);
		List<OrderBo> results = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> results.add(poToBo(po)));
		}
		return results;
	}

	@Override
	public Page<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page) {
		Page<OrderPo> poPage = orderRepo.findByLawyerId(lawyerId, page);
		List<OrderBo> bos = new ArrayList<OrderBo>();
		if (poPage.getContent() != null) {
			poPage.getContent().forEach(po -> bos.add(poToBo(po)));
		}
		Page<OrderBo> resultPage = new AggregatedPageImpl<OrderBo>(bos, page, poPage.getTotalElements());

		return resultPage;
	}

	@Override
	public List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void markPayed(WxPayEventBo bo, int userId) {
		if (StringUtils.isNumeric(bo.getOutTradeNo())) {
			OrderPo po = orderRepo.findOne(Integer.valueOf(bo.getOutTradeNo()));
			if (po != null && userId == po.getUserId()) {
				if (po.getPayStatus() == OrderPayStatusEnum.NOT_PAYED.getId()) {
					
					if(po.getStatus() == OrderStatusEnum.NO_PAY_NEED_COMPLETED.getId()){
						orderRepo.updatePayStatusAndStatus(OrderPayStatusEnum.PAYED.getId(),
								OrderStatusEnum.NOT_COMPLETED.getId(), po.getId());
					}else{
						orderRepo.updatePayStatusAndStatus(OrderPayStatusEnum.PAYED.getId(),
								OrderStatusEnum.NEED_DISPATCH.getId(), po.getId());
					}
					
				}
			} else if (po == null) {

			} else {
				// userid not match
			}
		} else {
			// order id error

		}
	}

	@Override
	public void acceptorOrder(int lawyerId, int orderId) {
		LawyerBo lawyer = lawyerSerivce.getLawyerById(lawyerId);
		if (lawyer == null) {
			logger.error(String.format("AcceptorOrder failed, unable to find lawyer with id %s", lawyerId));
			throw new JfApplicationRuntimeException("律师不存在");
		}
		OrderPo order = orderRepo.getOne(orderId);
		if (order == null) {
			logger.error(String.format("AcceptorOrder failed, unable to find order with id %s", orderId));
			throw new JfApplicationRuntimeException("订单不存在");
		}
	}

	@Override
	public List<OrderPhotoBo> getOrderPhotos(int orderId) {
		List<OrderPhotoPo> pos = orderPhotoRepo.findByOrderIdAndType(orderId, OrderPhotoTypeEnum.ORDER.getId());
		List<OrderPhotoBo> results = new ArrayList<OrderPhotoBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}

	@Override
	public List<OrderPhotoBo> getReplyPhotos(int orderId) {
		List<OrderPhotoPo> pos = orderPhotoRepo.findByOrderIdAndType(orderId, OrderPhotoTypeEnum.REPLY.getId());
		List<OrderPhotoBo> results = new ArrayList<OrderPhotoBo>(pos.size());
		pos.forEach(po -> results.add(poToBo(po)));
		return results;
	}
	
	@Override
	public void updateOrderStatus(int orderId, int previousStatus, int newStatus) {
		orderRepo.updateStatus(orderId, previousStatus, newStatus);
	}
	
	@Override
	public List<OrderBo> getUnconfirmedOrders(int size) {
		PageRequest page = new PageRequest(1, size);
		Page<OrderPo> pos = orderRepo.findByStatus(OrderStatusEnum.DISPATCHED.getId(), page);
		
	 	List<OrderBo> results = new ArrayList<OrderBo>(20);
	 	if (pos != null) {
	 		pos.forEach(po -> results.add(poToBo(po)));
	 	}
	 	return results;
	}
	
	@Override
	 public int getNumbersOfUnAssignedOrdersByCity(int city) {
		return orderRepo.countByCityIdAndStatus(city, OrderStatusEnum.NEED_DISPATCH.getId());
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
