package com.jfzy.web;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.base.AuthInfo;
import com.jfzy.base.SessionConstants;
import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.ArticleBo;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.OssUserVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleArticleVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class LoginController {

	@Autowired
	private HttpSession session;

	@Autowired
	private OssUserService ossUserService;

	@ResponseBody
	@GetMapping("/api/user/login")
	public ResponseVo<OssUserVo> login(String userName, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String abstracts = encoder.encode(password.trim());
		OssUserBo user = ossUserService.login(userName, abstracts);
		if (user != null) {
			session.setAttribute(SessionConstants.SESSION_KEY_USER, user);
			AuthInfo authInfo = new AuthInfo();
			authInfo.setPrivileges(Arrays.asList(new String[] { user.getRole() }));
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVo(user));
		} 
		return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), null, null);
	}
	
	private static OssUserVo boToVo(OssUserBo bo) {
		OssUserVo vo = new OssUserVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}
}
