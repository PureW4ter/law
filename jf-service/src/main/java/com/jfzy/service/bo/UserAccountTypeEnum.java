package com.jfzy.service.bo;

public enum UserAccountTypeEnum {
	MOBILE(0), WECHAT_OPENID(1), WECHAT_UNIONID(2);
	
	private int id;

	private UserAccountTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
