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
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	@Column(name = "product_code")
	private String productCode;
	
	//简单问的回复，以及房产，查封调查的回复
	@Column(name = "simple_reply")
	private String simpleReply;
	
	//实质
	@Column(name = "reply_summary")
	private String replySummary;
	
	//律师建议
	@Column(name = "reply_suggests")
	private String replySuggests;
	
	//法律法规
	@Column(name = "reply_rules")
	private String replyRules;
	
	@Column(name = "has_hukou")
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
