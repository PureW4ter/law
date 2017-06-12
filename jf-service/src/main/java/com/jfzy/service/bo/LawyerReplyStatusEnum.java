package com.jfzy.service.bo;

public enum LawyerReplyStatusEnum {

	INIT(0), REPLYED(1), SCORED(2);

	private int id;

	private LawyerReplyStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
