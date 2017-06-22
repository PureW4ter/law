package com.jfzy.service.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jf_lawyer")
public class LawyerPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "city_id")
	private int cityId;

	private int status;

	private String name;

	@Column(name = "phone_num")
	private String phoneNum;

	private String memo;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "update_time")
	private Timestamp updateTime;
	
	@Column(name = "login_name")
	private String loginName;
	
	private String password;

	private double score;

	@Column(name = "on_process_task")
	private int onProcessTask;
	
	@Column(name = "finished_task")
	private int finishedTask;
	
	@Column(name = "total_money")
	private double totalMoney;
	
	@Column(name = "finished_money")
	private double finishedMoney;

	@Column(name = "open_id")
	private String openId;

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
}
