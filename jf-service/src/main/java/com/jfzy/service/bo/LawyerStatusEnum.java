package com.jfzy.service.bo;

public enum LawyerStatusEnum {

	ACTIVE(0), INACTIVE(1), DELETED(2);

	private int id;

	private LawyerStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
