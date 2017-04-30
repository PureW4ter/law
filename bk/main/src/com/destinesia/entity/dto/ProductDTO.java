package com.destinesia.entity.dto;

import java.util.Date;

import com.destinesia.entity.Product;

public class ProductDTO {
	private String id;
	private String name;
	private double price;
	private int state;
	private String detail;
	private String picturePath;
	private boolean online;
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public static Product convert(ProductDTO dto){
		Product product = new Product();
		product.setCreateDate(dto.getCreateDate());
		product.setDetail(dto.getDetail());
		product.setId(dto.getId());
		product.setName(dto.getName());
		product.setOnline(dto.isOnline());
		product.setPicturePath(dto.getPicturePath());
		product.setPrice(dto.getPrice());
		product.setState(dto.getState());
		return product;
	}
}
