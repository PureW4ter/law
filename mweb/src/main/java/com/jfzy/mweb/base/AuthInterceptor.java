package com.jfzy.mweb.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzy.mweb.vo.SimpleResponseVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;

			AuthCheck authCheck = handlerMethod.getMethodAnnotation(AuthCheck.class);

			if (authCheck == null) {
				authCheck = handlerMethod.getBean().getClass().getAnnotation(AuthCheck.class);
			}

			if (authCheck == null) {
				return true;
			} else {
				UserSession session = processUserSession(request);
				if (session == null) {
					response.getWriter().write(objToJsonString(new SimpleResponseVo(401, "未登录")));
					response.flushBuffer();
					return false;
				}
			}
		}

		return true;

	}

	private UserSession processUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			UserSession info = (UserSession) session.getAttribute(UserSession.SESSION_KEY);
			if (info == null) {
				info = buildSessionFromRequest(request);
				session.setAttribute(UserSession.SESSION_KEY, info);
			}

			return info;
		} else {
			return null;
		}
	}

	private static UserSession buildSessionFromRequest(HttpServletRequest request) {
		String tokenStr = (String) request.getParameter("tk");
		if (StringUtils.isNotBlank(tokenStr)) {
			try {
				logger.info("Recover token");
				Token t = TokenUtil.extractToken(tokenStr);
				if (t != null) {
					UserSession s = new UserSession();
					s.setUserId(t.getUserId());
					return s;
				} else {
					return null;
				}
			} catch (RuntimeException e) {
				logger.error("Failed in extract token", e);
				return null;
			}
		}
		return null;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	private static String objToJsonString(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {

		} catch (JsonMappingException e) {

		} catch (IOException e) {

		}
		return json;
	}
}
