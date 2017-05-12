package com.jfzy.service.bo;

public enum GenderEnum {
	MEN(0), WOMEN(1);
	
	private int id;

	private GenderEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
