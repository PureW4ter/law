package com.jfzy.service.bo;

public enum UserLevelEnum {
	VISITOR(0), NORMAL(1), PLATINUM (2);
	
	private int id;

	private UserLevelEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
