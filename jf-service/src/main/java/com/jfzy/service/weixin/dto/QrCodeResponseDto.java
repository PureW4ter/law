package com.jfzy.service.weixin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QrCodeResponseDto {

	@JsonProperty("ticket")
	private String ticket;

	@JsonProperty("expire_seconds")
	private int expireSeconds;

	@JsonProperty("url")
	private String url;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
