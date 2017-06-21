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

import com.jfzy.base.AuthInfo;
import com.jfzy.base.CookieUtil;
import com.jfzy.base.OssWebConstants;
import com.jfzy.base.SessionConstants;
import com.jfzy.base.Token;
import com.jfzy.service.OssRoleService;
import com.jfzy.service.OssUserService;
import com.jfzy.service.bo.OssUserBo;
import com.jfzy.service.repository.RedisRepository;
import com.jfzy.web.vo.OssUserVo;
import com.jfzy.web.vo.ResponseStatusEnum;
import com.jfzy.web.vo.ResponseVo;

@RestController
public class LoginController extends BaseController {

	@Autowired
	private HttpSession session;

	@Autowired
	private OssUserService ossUserService;

	@Autowired
	private OssRoleService ossRoleService;

	@Autowired
	private RedisRepository redisRepo;

	@ResponseBody
	@GetMapping("/api/user/login")
	public ResponseVo<OssUserVo> login(HttpServletRequest request, HttpServletResponse response, String phoneNum,
			String code) {
		// do some check
		if (StringUtils.isBlank(phoneNum) || StringUtils.isBlank(code)) {
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "电话或验证码不能为空", null);
		}

		String codeInRedis = redisRepo.get(String.format(OssWebConstants.REDIS_PREFIX_VERIFY_CODE, phoneNum));

		if (!StringUtils.equals(code, codeInRedis)) {
			logger.info(String.format("Code stored %s, code given %s", codeInRedis, code));
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "验证码错误", null);
		}

		OssUserBo user = ossUserService.login(phoneNum, code);
		if (user != null) {
			session.setAttribute(SessionConstants.SESSION_KEY_USER, user);
			AuthInfo authInfo = new AuthInfo();
			authInfo.setPrivileges(Arrays.asList(new String[] { user.getRole() }));
			session.setAttribute(SessionConstants.SESSION_KEY_AUTH_INFO, authInfo);

			injectCookie(user.getId(), response);

			List<String> permissions = ossRoleService.getPermissionsByRoleName(user.getRole());
			return new ResponseVo<OssUserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, boToVo(user, permissions));
		}
		return new ResponseVo<OssUserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "用户不存在", null);
	}

	private void injectCookie(int userId, HttpServletResponse response) {
		// 埋cookie
		Token t = new Token();
		t.setUserId(userId);
		CookieUtil.addAuthCookie(t, response);
	}

	private static OssUserVo boToVo(OssUserBo bo, List<String> permissions) {
		OssUserVo vo = new OssUserVo();
		BeanUtils.copyProperties(bo, vo);
		vo.setPermissions(permissions);
		return vo;
	}

}
