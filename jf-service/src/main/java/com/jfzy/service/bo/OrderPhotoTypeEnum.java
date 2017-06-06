package com.jfzy.service.bo;

public enum OrderPhotoTypeEnum {
	ORDER(0), REPLY(1);

	private int id;

	private OrderPhotoTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
