package com.jfzy.web.vo;

public class LawyerReplyVo {
	private int id;
	private int orderId;
	private int lawyerId;
	private String createTime;
	private String updateTime;
	private String productCode;
	private String simpleReply;
	private String replySummary;
	private String replySuggests;
	private String replyRules;
	private int hasHukou;
	private String[] picList;
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
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
	public String[] getPicList() {
		return picList;
	}
	public void setPicList(String[] picList) {
		this.picList = picList;
	}
}
