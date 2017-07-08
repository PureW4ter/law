package com.jfzy.service.bo;

public enum OrderStatusEnum {

	// 已回复待审核FINISHED_NEEDCONFIRM,
	NO_PAY(0, "待支付"), NO_PAY_NEED_COMPLETED(1, "待支付"), NOT_COMPLETED(2, "待补充"), NEED_DISPATCH(3, "待指派"), DISPATCHED(4,
			"已指派"), FINISHED_NEEDCONFIRM(7, "已回复待审核"), FINISHED(98, "已完成"), CANCELED(99, "已取消");
	private int id;
	private String desc;

	private OrderStatusEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static OrderStatusEnum fromId(int id) {
		if (id == NO_PAY.id) {
			return NO_PAY;
		} else if (id == NO_PAY_NEED_COMPLETED.id) {
			return NO_PAY_NEED_COMPLETED;
		} else if (id == NOT_COMPLETED.id) {
			return NOT_COMPLETED;
		} else if (id == NEED_DISPATCH.id) {
			return NEED_DISPATCH;
		} else if (id == DISPATCHED.id) {
			return DISPATCHED;
		} else if (id == FINISHED_NEEDCONFIRM.id) {
			return FINISHED_NEEDCONFIRM;
		} else if (id == FINISHED.id) {
			return FINISHED;
		} else if (id == CANCELED.id) {
			return CANCELED;
		} else {
			return null;
		}
	}

}
