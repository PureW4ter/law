package com.jfzy.service.bo;

public enum PayWayEnum {
	NO_PAY(0), WEIXIN(1), FREE(2);
	private int id;

	private PayWayEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
