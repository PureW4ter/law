package com.jfzy.service.bo;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.LawyerService;
import com.jfzy.service.NotificationService;
import com.jfzy.service.OrderService;
import com.jfzy.service.exception.JfApplicationRuntimeException;

public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private LawyerService lawyerService;

	@Override
	public void orderAssignmentNotify(int lawyerId, int orderId) {
		OrderBo order = orderService.getOrderById(orderId);
		if (order == null || order.getLawyerId() != lawyerId) {
			throw new JfApplicationRuntimeException("通知人非订单指派律师");
		}

		LawyerBo lawyer = lawyerService.getLawyerById(lawyerId);
		if (lawyer == null) {
			throw new JfApplicationRuntimeException("律师不存在");
		}
		
		
	}
	
	

}