package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderPhotoBo;
import com.jfzy.service.bo.WxPayEventBo;
import com.jfzy.service.bo.WxPayResponseDto;

public interface OrderService {

	OrderBo createSOrder(OrderBo bo);

	OrderBo createIOrder(OrderBo bo);

	void assignOrder(int orderId, int lawyerId, int processorId, String processorName);

	WxPayResponseDto pay(int id, int userId, String ip, String openId);

	void markPayed(WxPayEventBo bo, int userId);

	void cancel(int id);

	void complete(int id, String comment, String[] picList);

	OrderBo getOrderById(int id);

	OrderBo getOrderByOrderNum(String orderNum);

	Page<OrderBo> getOrdersByCityIdAndStatus(int cityId, int status, Pageable page);

	Page<OrderBo> getCompletedOrdersByCityIdAndStatus(int cityId, Integer status, Pageable page);

	Page<OrderBo> getUncompletedOrdersByCityIdAndStatus(int cityId, Integer status, Pageable page);

	List<OrderBo> getOrdersByUser(int userId, Pageable page);

	Page<OrderBo> getOrdresByLawyer(int lawyerId, Pageable page);

	List<OrderBo> getUnprocessedOrdersByLawyer(int lawyerId, Pageable page);

	List<OrderBo> getSearchOrders(Pageable page);

	List<OrderBo> getInvestOrders(Pageable page);

	List<OrderBo> getSearchOrdersByUser(Pageable page, int userId);

	List<OrderBo> getInvestOrdersByUser(Pageable page, int userId);

	List<OrderBo> getSearchOrdersByLawyer(Pageable page, int lawyerId, int status);

	List<OrderBo> getInvestOrdersByLawyer(Pageable page, int lawyerId, int status);
	
	Page<OrderBo> getOrdersByType(Pageable page, String productCode);
	
	void acceptorOrder(int lawyerId, int orderId);

	List<OrderPhotoBo> getOrderPhotos(int orderId);

	List<OrderPhotoBo> getReplyPhotos(int orderId);

	int getNumbersOfUnAssignedOrdersByCity(int city);

	List<OrderBo> getUnconfirmedOrders(int size);

	void updateOrderStatus(int orderId, int previousStatus, int newStatus);

	void updateOrderStatus(int orderId, int newStatus);

	int getTotal(int userId);

}
