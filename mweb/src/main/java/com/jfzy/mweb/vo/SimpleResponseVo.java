package com.jfzy.mweb.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleResponseVo {

	@JsonProperty("s")
	private int status;
	@JsonProperty("m")
	private String message;

	public SimpleResponseVo(int status, String message) {
		this.status = status;
		this.setMessage(message);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}