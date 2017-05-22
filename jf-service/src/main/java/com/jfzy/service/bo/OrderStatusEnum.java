package com.jfzy.service.bo;

public enum OrderStatusEnum {

	NO_PAY(0), NO_PAY_NEED_COMPLETED(1), NOT_COMPLETED(2), NEED_PROCESS(3), 
	NEED_DISPATCH(4), DISPATCHED(5), FINISHED(6), CANCELED(7);
	private int id;

	private OrderStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
