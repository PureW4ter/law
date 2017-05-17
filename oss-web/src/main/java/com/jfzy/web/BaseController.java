package com.jfzy.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.base.SessionConstants;
import com.jfzy.service.bo.OssUserBo;

public class BaseController {

	@Autowired
	private HttpSession session;

	public OssUserBo getOssUser() {
		return session == null ? null : (OssUserBo) session.getAttribute(SessionConstants.SESSION_KEY_USER);
	}

}
