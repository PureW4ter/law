package com.jfzy.service.bo;

public enum OrderStatusEnum {

	//已回复待审核FINISHED_NEEDCONFIRM, 
	NO_PAY(0), NO_PAY_NEED_COMPLETED(1), NOT_COMPLETED(2), NEED_DISPATCH(3), DISPATCHED(4), FINISHED(5), CANCELED(
			6), FINISHED_NEEDCONFIRM(7), DISPATCHED_NOTIFIED(8);
	private int id;
	private String desc;

	private OrderStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
}
