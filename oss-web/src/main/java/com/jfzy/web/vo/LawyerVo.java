package com.jfzy.web.vo;


public class LawyerVo {

	private int id;
	private int cityId;
	private int status;
	private String name;
	private String phoneNum;
	private String memo;
	private String createTime;
	private String updateTime;
	private double score;
	private int onProcessTask;
	private int finishedTask;
	private double totalMoney;
	private double finishedMoney;
	private String openId;
	private String loginName;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
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
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getOnProcessTask() {
		return onProcessTask;
	}
	public void setOnProcessTask(int onProcessTask) {
		this.onProcessTask = onProcessTask;
	}
	public int getFinishedTask() {
		return finishedTask;
	}
	public void setFinishedTask(int finishedTask) {
		this.finishedTask = finishedTask;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public double getFinishedMoney() {
		return finishedMoney;
	}
	public void setFinishedMoney(double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
