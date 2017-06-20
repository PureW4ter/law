package com.jfzy.service.bo;

import java.sql.Timestamp;
import java.util.List;

public class RoleBo {

	private int id;
	private String name;
	private String desc;
	private int status;
	private List<String> permissons;
	private Timestamp createTime;
	private Timestamp updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<String> getPermissons() {
		return permissons;
	}

	public void setPermissons(List<String> permissons) {
		this.permissons = permissons;
	}

}
