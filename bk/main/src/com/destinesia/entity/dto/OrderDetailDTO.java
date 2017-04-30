package com.destinesia.entity.dto;

import java.util.Date;

import com.destinesia.entity.OrderDetail;

public class OrderDetailDTO {
	private String id;
	private String orderId;
	private ProductDTO product;
	private int count;
	private double price;
	private double totalPrice;
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
	public static OrderDetail convert(OrderDetailDTO dto){
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCount(dto.getCount());
		orderDetail.setCreateDate(dto.getCreateDate());
		orderDetail.setId(dto.getId());
		orderDetail.setOrderId(dto.getOrderId());
		orderDetail.setPrice(dto.getPrice());
		orderDetail.setTotalPrice(dto.getTotalPrice());
		orderDetail.setProduct(ProductDTO.convert(dto.getProduct()));
		return orderDetail;
	}
}
