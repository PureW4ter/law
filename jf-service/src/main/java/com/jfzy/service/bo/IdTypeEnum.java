package com.jfzy.service.bo;

public enum IdTypeEnum {

	ORDER(1);

	private int id;

	private IdTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
