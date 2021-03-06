package com.jfzy.mweb.util;

public enum ResponseStatusEnum {

	SUCCESS(200), BAD_REQUEST(400), SERVER_ERROR(500);

	private int code;

	private ResponseStatusEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
