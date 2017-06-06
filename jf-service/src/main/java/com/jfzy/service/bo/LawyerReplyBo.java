package com.jfzy.service.bo;

import java.sql.Timestamp;

public class LawyerReplyBo {
	private int id;
	private int orderId;
	private int lawyerId;
	private Timestamp create_time;
	private Timestamp update_time;
	private String productCode;
	private String simpleReply;
	private String xingwei;
	private String yupan;
	private String buzhou;
	private String tishi;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(int lawyerId) {
		this.lawyerId = lawyerId;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSimpleReply() {
		return simpleReply;
	}
	public void setSimpleReply(String simpleReply) {
		this.simpleReply = simpleReply;
	}
	public String getXingwei() {
		return xingwei;
	}
	public void setXingwei(String xingwei) {
		this.xingwei = xingwei;
	}
	public String getYupan() {
		return yupan;
	}
	public void setYupan(String yupan) {
		this.yupan = yupan;
	}
	public String getBuzhou() {
		return buzhou;
	}
	public void setBuzhou(String buzhou) {
		this.buzhou = buzhou;
	}
	public String getTishi() {
		return tishi;
	}
	public void setTishi(String tishi) {
		this.tishi = tishi;
	}
}
