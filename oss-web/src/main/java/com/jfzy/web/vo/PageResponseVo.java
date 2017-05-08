package com.jfzy.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResponseVo<T> {

	@JsonProperty("s")
	private int status;
	@JsonProperty("m")
	private String message;
	@JsonProperty("r")
	private T response;

	private int pageNo;
	private long totalNum;
	private int pageSize;

	public PageResponseVo(int status, String message, T response, int pageNo, int pageSize, long total) {
		this.status = status;
		this.setMessage(message);
		this.response = response;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalNum = total;
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

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}