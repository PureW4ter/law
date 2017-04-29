package com.jfzy.mweb.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseVo<T> {

	@JsonProperty("s")
	private int status;
	@JsonProperty("m")
	private String message;
	@JsonProperty("r")
	private T response;

	public ResponseVo(int status, String message, T response) {
		this.status = status;
		this.setMessage(message);
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}