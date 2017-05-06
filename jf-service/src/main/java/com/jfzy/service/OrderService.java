package com.jfzy.service;

import com.jfzy.service.bo.OrderBo;

public interface OrderService {

	void createOrder(OrderBo bo);

	void assignOrder(int orderId, int lawyerId, int processorId, String processorName);

}
