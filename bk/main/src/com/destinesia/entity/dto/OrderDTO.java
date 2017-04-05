package com.destinesia.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.destinesia.entity.Customer;
import com.destinesia.entity.Order;
import com.destinesia.entity.OrderDetail;

public class OrderDTO {
	private String id;
	private String userId;
	private Customer customer;
	private Date createDate;
	private double totalPrice;
	private int state;
	private int payType;
	private String token;
	private List<OrderDetailDTO> orderDetails;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public static Order convert(OrderDTO dto){
		Order order = new Order();
		order.setCreateDate(dto.getCreateDate());
		order.setCustomer(dto.getCustomer());
		order.setId(dto.getId());
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		for(OrderDetailDTO orderDetailDto : dto.getOrderDetails()){
			list.add(OrderDetailDTO.convert(orderDetailDto));
		}
		order.setOrderDetails(list);
		order.setPayType(dto.getPayType());
		order.setState(dto.getState());
		order.setTotalPrice(dto.getTotalPrice());
		order.setUserId(dto.getUserId());
		return order;
	}
}
