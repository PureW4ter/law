package com.jfzy.service.weixin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QrCodeRequestDto {

	public static class ActionInfo {
		@JsonProperty("scene")
		private Scene scene = new Scene();
	}

	public static class Scene {
		@JsonProperty("scene_str")
		private String sceneStr;
	}

	@JsonProperty("expire_seconds")
	private int expireSeconds;

	@JsonProperty("action_name")
	private String actionName;

	@JsonProperty("action_info")
	private ActionInfo actionInfo = new ActionInfo();

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setSceneStr(String sceneStr) {
		this.actionInfo.scene.sceneStr = sceneStr;
	}

	public static void main(String[] args) throws JsonProcessingException {
		QrCodeRequestDto dto = new QrCodeRequestDto();
		dto.setSceneStr("1111");
		dto.setActionName("aaa");
		dto.setExpireSeconds(123);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(dto));
	}

}