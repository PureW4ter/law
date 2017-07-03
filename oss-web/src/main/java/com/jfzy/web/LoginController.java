package com.jfzy.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.base.AuthCheck;
import com.jfzy.base.AuthInfo;
import com.jfzy.base.CookieUtil;
import com.jfzy.base.SessionConstants;
import com.jfzy.base.Token;
import com.jfzy.service.OssRoleService;
import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.web.vo.OssUserVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;
import com.jfzy.web.vo.SimpleResponseVo;

@RestController
public class LoginController extends BaseController {

	@Autowired
	private HttpSession session;

	@Autowired
	private OssUserService ossUserService;

	@Autowired
	private OssRoleService ossRoleService;

	@ResponseBody
	@GetMapping("/api/user/login")
	public ResponseVo<OssUserVo> login(HttpServletRequest request, HttpServletResponse response, String userName,
			String password) {
		// do some check
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户名或密码不能为空", null);
		}

		OssUserBo user = ossUserService.login(userName, password);
		if (user != null) {
			session.setAttribute(SessionConstants.SESSION_KEY_USER, user);
			AuthInfo authInfo = new AuthInfo();
			authInfo.setUserId(user.getId());
			authInfo.setPrivileges(Arrays.asList(new String[] { user.getRole() }));
			session.setAttribute(SessionConstants.SESSION_KEY_AUTH_INFO, authInfo);

			injectCookie(user.getId(), user.getType(), response);

			List<String> permissions = ossRoleService.getPermissionsByRoleName(user.getRole());
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVo(user, permissions));
		}
		return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户不存在", null);
	}

	@AuthCheck(privileges = {})
	@ResponseBody
	@GetMapping("/api/user/resetpwd")
	public SimpleResponseVo resetPassword(String oldPass, String newPass) {
		if (StringUtils.isBlank(oldPass)) {
			return new SimpleResponseVo(ResponseStatusEnum.BAD_REQUEST.getCode(), "旧密码不能为空");
		}
		if (StringUtils.isBlank(newPass)) {
			return new SimpleResponseVo(ResponseStatusEnum.BAD_REQUEST.getCode(), "新密码不能为空");
		}

		AuthInfo info = getAuthInfo();
		if (info != null) {
			ossUserService.resetPassword(info.getUserId(), this.isLawyer(), oldPass, newPass);
			return new SimpleResponseVo(ResponseStatusEnum.SUCCESS.getCode(), "密码修改成功");
		} else {
			return new SimpleResponseVo(ResponseStatusEnum.NOT_LOGIN.getCode(), "未登录");
		}

	}

	private void injectCookie(int userId, int type, HttpServletResponse response) {
		// 埋cookie
		Token t = new Token();
		t.setUserId(userId);
		t.setType(type);
		CookieUtil.addAuthCookie(t, response);
	}

	private static OssUserVo boToVo(OssUserBo bo, List<String> permissions) {
		OssUserVo vo = new OssUserVo();
		BeanUtils.copyProperties(bo, vo);
		vo.setPermissions(permissions);
		vo.setPassword("");
		return vo;
	}

}
