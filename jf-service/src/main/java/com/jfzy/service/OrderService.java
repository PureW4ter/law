package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OrderBo;

public interface OrderService {

	void createOrder(OrderBo bo);

	void assignOrder(int orderId, int lawyerId, int processorId, String processorName);

	OrderBo getOrderById(int id);

	OrderBo getOrderBySn(String sn);

	Page<OrderBo> getOrdersByCityIdAndStatus(int cityId, int status, Pageable page);

	List<OrderBo> getOrdersByUser(int userId, Pageable page);

	List<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page);

	List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page);

}
