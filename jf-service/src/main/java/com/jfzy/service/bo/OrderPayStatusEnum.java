package com.jfzy.service.bo;

public enum OrderPayStatusEnum {

	NOT_PAYED(0), PAYED(1), REFUND(2);

	private int id;

	private OrderPayStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
