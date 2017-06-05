package com.jfzy.mweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzy.mweb.base.BaseController;
import com.jfzy.mweb.base.MWebConstants;
import com.jfzy.mweb.base.Token;
import com.jfzy.mweb.base.TokenUtil;
import com.jfzy.mweb.base.UserSession;
import com.jfzy.mweb.util.ResponseStatusEnum;
import com.jfzy.mweb.vo.AuthUserVo;
import com.jfzy.mweb.vo.ResponseVo;
import com.jfzy.mweb.vo.UserVo;
import com.jfzy.service.UserService;
import com.jfzy.service.WechatService;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;

@RestController
public class APPUserController extends BaseController {
	@Autowired
	private UserService userService;

	@Autowired
	private WechatService wechatService;

	@ResponseBody
	@GetMapping("/api/user/list")
	public ResponseVo<List<UserVo>> list(int page, int size) {
		if (page < 0) {
			page = 0;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<UserBo> values = userService.getUsers(new PageRequest(page, size, sort));
		List<UserVo> resultUsers = new ArrayList<UserVo>(values.size());
		for (UserBo bo : values) {
			resultUsers.add(boToVoForUser(bo));
		}
		return new ResponseVo<List<UserVo>>(ResponseStatusEnum.SUCCESS.getCode(), null, resultUsers);
	}

	@ResponseBody
	@GetMapping(path = "/api/user/wxlogin")
	public ResponseVo<AuthUserVo> wxlogin(String code) {
		try {
			UserBo bo = wechatService.wxlogin(code);
			UserAccountBo abo = userService.getUserAccountByUserId(bo.getId(), UserAccountTypeEnum.MOBILE.getId());

			// init session
			UserAccountBo wxBo = userService.getUserAccountByUserId(bo.getId(),
					UserAccountTypeEnum.WECHAT_OPENID.getId());
			Token t = new Token();
			t.setUserId(bo.getId());
			t.setOpenId(wxBo.getValue());
			t.setTimestamp(System.currentTimeMillis());
			String token = TokenUtil.generateTokenString(t);
			AuthUserVo vo = boToAuthUserVo(bo, token);
			if (abo != null)
				vo.setPhone(abo.getValue());

			UserSession session = new UserSession();
			session.setOpenId(wxBo.getValue());
			session.setUserId(bo.getId());
			this.setUserSession(session);

			return new ResponseVo<AuthUserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
		} catch (IOException e) {
			return new ResponseVo<AuthUserVo>(ResponseStatusEnum.SERVER_ERROR.getCode(), null, null);
		}
	}

	@ResponseBody
	@GetMapping("/api/user/bind")
	public ResponseVo<UserVo> bind(String phone, String code, int userId) {
		if (!checkSmsCode(code)) {
			return new ResponseVo<UserVo>(ResponseStatusEnum.BAD_REQUEST.getCode(), "短信验证码错误", null);
		} else {
			UserBo bo = userService.bind(phone, userId);
			UserVo vo = null;
			if (bo != null) {
				vo = boToVoForUser(bo);
				vo.setPhone(phone);
			}
			return new ResponseVo<UserVo>(ResponseStatusEnum.SUCCESS.getCode(), null, vo);
		}
	}

	@ResponseBody
	@GetMapping("/api/user/unbind")
	public ResponseVo<Object> unbind(int userAccountId) {
		userService.unbind(userAccountId);
		return new ResponseVo<Object>(ResponseStatusEnum.SUCCESS.getCode(), null, null);
	}

	private static UserVo boToVoForUser(UserBo bo) {
		UserVo vo = new UserVo();
		BeanUtils.copyProperties(bo, vo);
		return vo;
	}

	private static AuthUserVo boToAuthUserVo(UserBo bo, String token) {
		AuthUserVo vo = new AuthUserVo();
		BeanUtils.copyProperties(bo, vo);
		vo.setToken(token);
		return vo;
	}

	private boolean checkSmsCode(String smsCode) {
		String sessionSms = (String) session.getAttribute(MWebConstants.SESSION_KEY_SMS);
		return StringUtils.equals(smsCode, sessionSms);
	}

}