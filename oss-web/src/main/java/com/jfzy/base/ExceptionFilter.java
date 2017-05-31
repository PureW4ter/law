package com.jfzy.base;

import java.io.IOException;

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
import com.jfzy.service.exception.JfApplicationRuntimeException;
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
		} catch (JfApplicationRuntimeException jfe) {
			logger.error("Error occur in chain", jfe);
			resp.getWriter().write(
					objToJsonString(new SimpleResponseVo(ResponseStatusEnum.SERVER_ERROR.getCode(), jfe.getMessage())));
			resp.flushBuffer();
		} catch (RuntimeException e) {
			logger.error("Error occur in chain", e);
			resp.getWriter()
					.write(objToJsonString(new SimpleResponseVo(ResponseStatusEnum.SERVER_ERROR.getCode(), "请求失败")));
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
