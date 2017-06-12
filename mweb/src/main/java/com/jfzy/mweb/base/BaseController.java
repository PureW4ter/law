package com.jfzy.mweb.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.repository.RedisRepository;

public class BaseController {

	@Autowired
	protected HttpSession session;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private RedisRepository<UserSession> redisRepo;

	protected String getClientIp() {
		return request == null ? "" : request.getRemoteAddr();
	}

	protected UserSession getUserSession() {
		if (session == null) {
			return null;
		} else {
			if (session.getAttribute(UserSession.SESSION_KEY) == null) {
				UserSession tmpSession = redisRepo.get(String.format("US|%s", request.getRequestedSessionId()));
				if (tmpSession != null) {
					session.setAttribute(UserSession.SESSION_KEY, tmpSession);
				}
				return tmpSession;
			} else {
				return (UserSession) session.getAttribute(UserSession.SESSION_KEY);
			}

		}
	}

	protected void setUserSession(UserSession userSession) {
		session.setAttribute(UserSession.SESSION_KEY, userSession);
		redisRepo.set(String.format("US|%s", request.getRequestedSessionId()), userSession);
	}

}
