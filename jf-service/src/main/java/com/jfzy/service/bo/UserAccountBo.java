package com.jfzy.service.bo;

import java.sql.Timestamp;

public class UserAccountBo {

	private int id;
	private int userId;
	private UserAccountTypeEnum type;
	private String value;
	private StatusEnum status;
	private Timestamp createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserAccountTypeEnum getType() {
		return type;
	}
	public void setType(UserAccountTypeEnum type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	
}
