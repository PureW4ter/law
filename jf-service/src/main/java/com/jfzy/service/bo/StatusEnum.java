package com.jfzy.service.bo;

public enum StatusEnum {
	ENABLED(0), DISABLED(1);
	
	private int id;

	private StatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
