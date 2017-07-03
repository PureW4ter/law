package com.jfzy.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.base.AuthInfo;
import com.jfzy.base.SessionConstants;

public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private HttpSession session;

	protected AuthInfo getAuthInfo() {
		return (AuthInfo) session.getAttribute(SessionConstants.SESSION_KEY_AUTH_INFO);
	}

	protected boolean isAdmin() {
		AuthInfo info = getAuthInfo();
		return info == null ? false : info.getPrivileges().contains("admin");
	}

	protected boolean isLawyer() {
		AuthInfo info = getAuthInfo();
		return info == null ? false : info.getPrivileges().contains("lawyer");
	}

}
