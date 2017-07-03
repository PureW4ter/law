package com.jfzy.base;

import java.util.List;

public class AuthInfo {

	private int userId;

	private List<String> privileges;

	public List<String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
