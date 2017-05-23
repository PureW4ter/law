package com.jfzy.service.bo;

public enum OrderStatusEnum {

	//简单问问 就是process，其他的是dispatch
	NO_PAY(0), NO_PAY_NEED_COMPLETED(1), NOT_COMPLETED(2), 
	NEED_DISPATCH(3), DISPATCHED(4), FINISHED(5), CANCELED(6);
	private int id;

	private OrderStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
