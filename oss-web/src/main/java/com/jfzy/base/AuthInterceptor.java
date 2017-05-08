package com.jfzy.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzy.web.vo.SimpleResponseVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

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
				AuthInfo info = getInfo(request);
				if (info != null) {
					String[] privileges = authCheck.privileges();

					// 按照权限验证权限
					if (privileges != null) {
						for (int i = 0; i < privileges.length; i++) {
							if (info.getPrivileges().contains(privileges[i])) {
								return true;
							}
						}
					}

					response.getWriter().write(objToJsonString(new SimpleResponseVo(402, "权限不够")));
					response.flushBuffer();
				} else {// 未登陆

					response.getWriter().write(objToJsonString(new SimpleResponseVo(401, "未登录")));
					response.flushBuffer();

				}

				return false;
			}
		}

		return true;

	}

	private AuthInfo getInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			AuthInfo info = (AuthInfo) session.getAttribute(SessionConstants.SESSION_KEY_AUTH_INFO);
			return info;
		} else {

			return null;
		}
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
