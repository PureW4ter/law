package com.jfzy.mweb.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

	@Autowired
	protected HttpSession session;

	@Autowired
	protected HttpServletRequest request;

	protected String getClientIp() {
		return request == null ? "" : request.getRemoteAddr();
	}

	protected UserSession getUserSession() {
		return session == null ? null : (UserSession) session.getAttribute(UserSession.SESSION_KEY);
	}

	protected void setUserSession(UserSession userSession) {
		session.setAttribute(UserSession.SESSION_KEY, userSession);
	}

}
