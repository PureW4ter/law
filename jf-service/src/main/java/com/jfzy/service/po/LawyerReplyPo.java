package com.jfzy.service.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jf_lawyer_reply")
public class LawyerReplyPo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "order_id")
	private int orderId;

	@Column(name = "lawyer_id")
	private int lawyerId;

	@Column(name = "createTime")
	private Timestamp create_time;

	@Column(name = "updateTime")
	private Timestamp update_time;

	@Column(name = "product_code")
	private String productCode;

	// 简单问的回复，以及房产，查封调查的回复
	@Column(name = "simple_reply")
	private String simpleReply;

	// 行为指引
	private String xingwei;
	// 结果预判
	private String yupan;
	// 具体步骤
	private String buzhou;
	// 特别提示
	private String tishi;

	private double score;

	private int status;

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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
