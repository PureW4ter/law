package com.jfzy.service.bo;

import java.sql.Timestamp;

public class LawyerReplyBo {

	private int id;
	private int orderId;
	private int lawyerId;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String productCode;
	private String simpleReply;
	private String replySummary;
	private String replySuggests;
	private String replyRules;
	private int hasHukou;
	private int status;
	private double score;
	
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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
	public String getReplySummary() {
		return replySummary;
	}
	public void setReplySummary(String replySummary) {
		this.replySummary = replySummary;
	}
	public String getReplySuggests() {
		return replySuggests;
	}
	public void setReplySuggests(String replySuggests) {
		this.replySuggests = replySuggests;
	}
	public String getReplyRules() {
		return replyRules;
	}
	public void setReplyRules(String replyRules) {
		this.replyRules = replyRules;
	}
	public int getHasHukou() {
		return hasHukou;
	}
	public void setHasHukou(int hasHukou) {
		this.hasHukou = hasHukou;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}

