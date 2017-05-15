package com.jfzy.service.bo;

public enum QAStatusEnum {
	UNPROCESSED(0), PROCESSED(1);

	private int id;

	private QAStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
