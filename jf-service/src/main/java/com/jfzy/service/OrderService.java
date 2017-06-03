package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.WxPayResponseDto;

public interface OrderService {

	OrderBo createSOrder(OrderBo bo);

	OrderBo createIOrder(OrderBo bo);

	void assignOrder(int orderId, int lawyerId, int processorId, String processorName);

	WxPayResponseDto pay(int id, int userId, String ip, String openId);

	void cancel(int id);

	void complete(int id, String comment, String[] picList);

	OrderBo getOrderById(int id);

	OrderBo getOrderBySn(String sn);

	Page<OrderBo> getOrdersByCityIdAndStatus(int cityId, int status, Pageable page);

	List<OrderBo> getOrdersByUser(int userId, Pageable page);

	List<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page);

	List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page);

	List<OrderBo> getSearchOrders(Pageable page);

	List<OrderBo> getInvestOrders(Pageable page);
}
