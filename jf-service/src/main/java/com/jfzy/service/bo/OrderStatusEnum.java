package com.jfzy.service.bo;

public enum OrderStatusEnum {

	INIT(0), NEED_DISPATCH(1), DISPATCHED(2);

	private int id;

	private OrderStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
