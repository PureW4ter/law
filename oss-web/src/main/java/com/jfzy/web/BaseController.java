package com.jfzy.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.base.SessionConstants;
import com.jfzy.service.bo.OssUserBo;

public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private HttpSession session;

	public OssUserBo getOssUser() {
		return session == null ? null : (OssUserBo) session.getAttribute(SessionConstants.SESSION_KEY_USER);
	}

}
