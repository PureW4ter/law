package com.jfzy.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.LawyerService;
import com.jfzy.service.NotificationService;
import com.jfzy.service.OrderService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final int PAGE_SIZE = 20;

	@Autowired
	private OrderService orderService;

	@Autowired
	private LawyerService lawyerService;

	private static final int[] CITIES = { 1, 2, 3 };

	private void orderAssignmentNotify(OrderBo order) {
		if (order != null && order.getStatus() == OrderStatusEnum.DISPATCHED.getId()) {
			LawyerBo lawyer = lawyerService.getLawyerById(order.getLawyerId());
			if (lawyer != null && StringUtils.isNotBlank(lawyer.getOpenId())
					&& StringUtils.isNotBlank(lawyer.getPhoneNum())) {
				notifyByWechat(order, lawyer.getOpenId());
				// finally update status
				orderService.updateOrderStatus(order.getId(), OrderStatusEnum.DISPATCHED.getId(),
						OrderStatusEnum.DISPATCHED_NOTIFIED.getId());
			} else {
				// log
			}

		}
	}

	private void notifyByWechat(OrderBo order, String openId) {
		// TODO
	}

	@Override
	public void notifyForAssignment() {
		for (int i = 0; i < CITIES.length; ++i) {
			int count = orderService.getNumbersOfUnAssignedOrdersByCity(CITIES[i]);
			if (count > 0) {

			}
		}
	}

	@Override
	public void notifyForConfirm() {
		List<OrderBo> orders = orderService.getUnconfirmedOrders(PAGE_SIZE);

		do {
			if (orders != null) {
				orders.forEach(order -> orderAssignmentNotify(order));
			}
		} while (orders != null && orders.size() == PAGE_SIZE);

	}

}
