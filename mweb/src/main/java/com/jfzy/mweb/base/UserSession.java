package com.jfzy.mweb.base;

import java.io.Serializable;

public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519252133237683523L;
	public static final String SESSION_KEY = "USER";
	public static final int EMPTY_USER_ID = 0;

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
