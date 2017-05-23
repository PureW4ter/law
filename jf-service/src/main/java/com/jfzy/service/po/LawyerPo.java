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

	private int cityId;

	private int status;
	
	@Column(name = "oss_user_id")
	private int ossUserId;

	private String name;

	@Column(name = "phone_num")
	private String phoneNum;

	private String memo;
	
	@Column(name = "creat_time")
	private Timestamp creatTime;
	
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	private double score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getOssUserId() {
		return ossUserId;
	}

	public void setOssUserId(int ossUserId) {
		this.ossUserId = ossUserId;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
