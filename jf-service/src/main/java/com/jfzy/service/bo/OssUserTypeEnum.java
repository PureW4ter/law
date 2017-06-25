package com.jfzy.service.bo;

public enum OssUserTypeEnum {

	USER(0), LAWYER(1);

	private int id;

	private OssUserTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

}
