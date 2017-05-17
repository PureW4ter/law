package com.jfzy.web;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.base.AuthInfo;
import com.jfzy.base.SessionConstants;
import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class LoginController {

	@Autowired
	private HttpSession session;

	@Autowired
	private OssUserService ossUserService;

	@ResponseBody
	@GetMapping("/login")
	public SimpleResponseVo login(String userName, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String abstracts = encoder.encode(password);
		OssUserBo user = ossUserService.login(userName, abstracts);
		if (user != null) {
			session.setAttribute(SessionConstants.SESSION_KEY_USER, user);
			AuthInfo authInfo = new AuthInfo();
			authInfo.setPrivileges(Arrays.asList(new String[] { user.getRole() }));
			session.setAttribute(SessionConstants.SESSION_KEY_AUTH_INFO, authInfo);
		} else {

		}
		return null;
	}

}
