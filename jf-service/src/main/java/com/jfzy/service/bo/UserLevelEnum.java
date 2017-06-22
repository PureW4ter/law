package com.jfzy.service.bo;

public enum UserLevelEnum {
	VISITOR(1), NORMAL(2), PAIED (3);
	
	private int id;

	private UserLevelEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
