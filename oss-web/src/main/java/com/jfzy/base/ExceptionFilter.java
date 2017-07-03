package com.jfzy.base;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.SimpleResponseVo;

public class ExceptionFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

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

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		try {
			chain.doFilter(req, resp);
		} catch (JfErrorCodeRuntimeException jecr) {
			logger.error("Error occur:", jecr);
			resp.getWriter().write(objToJsonString(new SimpleResponseVo(jecr.getCode(), jecr.getToastMessage())));
			resp.flushBuffer();
		} catch (RuntimeException jfe) {
			String code = UUID.randomUUID().toString();
			logger.error(String.format("ERROR CODE:%s", code), jfe);
			resp.getWriter().write(objToJsonString(
					new SimpleResponseVo(ResponseStatusEnum.SERVER_ERROR.getCode(), String.format("Code:", code))));
			resp.flushBuffer();
		} catch (NestedServletException nse) {
			Throwable cause = nse.getCause();
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json; charset=utf-8");
			if (cause != null && cause instanceof JfErrorCodeRuntimeException) {

				logger.error(String.format("Error occur: %s", cause.getMessage()));
				resp.getWriter()
						.write(objToJsonString(new SimpleResponseVo(((JfErrorCodeRuntimeException) cause).getCode(),
								((JfErrorCodeRuntimeException) cause).getToastMessage())));
				resp.flushBuffer();
			} else {
				String code = UUID.randomUUID().toString();
				logger.error(String.format("ERROR CODE:%s", code), cause);
				resp.getWriter().write(objToJsonString(
						new SimpleResponseVo(ResponseStatusEnum.SERVER_ERROR.getCode(), String.format("Code:", code))));
				resp.flushBuffer();
			}
		}
	}

}
