package com.jfzy.service.bo;

public class UserAccountBo {

	private int id;
	private int userId;
	private UserAccountTypeEnum type;
	private String value;
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
	
	

}
