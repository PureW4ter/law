package com.jfzy.base;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.SimpleResponseVo;

public class ExceptionFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
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
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

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
