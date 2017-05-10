package com.jfzy.mweb.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeiXinController {
	public UserInfo userInfo(final HttpServletRequest request, @RequestParam(required = true) String code) {
		if (StringUtils.isBlank(code))
			throw RestStatus.valueOf(RestStatus.CODE_FIELD_INVALID, "code 不能为空!");
		Customer customer = null;
		try {
			customer = voteService.getCustomerInfoByCode(code);
		} catch (Exception e) {
			throw RestStatus.valueOf(RestStatus.CODE_NOT_FOUND, "获取用户信息失败 !");
		}
		if (customer == null)
			throw RestStatus.valueOf(RestStatus.CODE_NOT_FOUND, "获取用户信息不存在 !");
		logger.info(" PhotographyController userInfo ----code:" + code + " id:" + customer.getId() + " openid:" + customer.getOpenid()
		 	+ " nikeName:"+ customer.getNickname() + " mobile:" + customer.getMobile());
		
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyPropertiesIfNotNull(customer, userInfo, "id", "openid","nickname", "headImgUrl");
		userInfo.setVoted(voteService.isVotedByCustomerId(customer.getId()));

		// String callback = request.getParameter("callback");
		// return callback + "(" + JsonUtils.toJson(userInfo) + ")";
		return userInfo;
	}
}
