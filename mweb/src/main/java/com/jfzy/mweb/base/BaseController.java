package com.jfzy.mweb.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.repository.RedisRepository;

public class BaseController {

	@Autowired
	protected HttpSession session;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private RedisRepository redisRepo;

	protected String getClientIp() {
		return request == null ? "" : request.getRemoteAddr();
	}

	protected UserSession getUserSession() {
		if (session == null) {
			return null;
		} else {
			if (session.getAttribute(UserSession.SESSION_KEY) == null) {
				String sessionString = redisRepo.get(String.format("US|%s", request.getRequestedSessionId()));
				UserSession tmpSession = (UserSession) fromJson(sessionString);

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
		redisRepo.set(String.format("US|%s", request.getRequestedSessionId()), toJson(userSession));
	}

	private static UserSession fromJson(String jsonString) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (UserSession) mapper.readValue(jsonString, UserSession.class);
		} catch (JsonParseException e) {
			throw new JfApplicationRuntimeException("Session获取失败", e);
		} catch (JsonMappingException e) {
			throw new JfApplicationRuntimeException("Session获取失败", e);
		} catch (IOException e) {
			throw new JfApplicationRuntimeException("Session获取失败", e);
		}
	}

	private static String toJson(UserSession userSession) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(userSession);
		} catch (JsonProcessingException e) {
			throw new JfApplicationRuntimeException("Session保存失败", e);
		}
	}

}
