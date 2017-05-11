package com.jfzy.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfzf.core.HttpClientUtils;
import com.jfzf.core.JsonUtils;
import com.jfzy.service.TagService;
import com.jfzy.service.UserService;
import com.jfzy.service.WechatService;
import com.jfzy.service.bo.GenderEnum;
import com.jfzy.service.bo.StatusEnum;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.bo.UserBo;
import com.jfzy.service.bo.WechatTokenInfo;
import com.jfzy.service.po.WechatUser;

@Service
public class WechatServiceImpl implements WechatService{
	
	private static String SECRET = "9476fc7b509bbd7b58ff530d429a7046";
	private static String APP_ID = "wx79ad71f12e49251b";
	
	@Autowired
	private UserService userService;
	
	 WechatTokenInfo getTokenByCode(String code) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(APP_ID);
		sb.append("&secret=").append(SECRET);
		sb.append("&code=").append(code);
		sb.append("&grant_type=authorization_code");
		String result = HttpClientUtils.getResponseText(sb.toString());
		JsonNode node = JsonUtils.fromJson(result, JsonNode.class);
		if (node.has("errcode")){
			return null;
		}
		return JsonUtils.fromJson(result, WechatTokenInfo.class);
	}
	
	public UserAccountBo getWXUserAccount(String code, Integer userId) throws IOException  {
		UserAccountBo userAccountBo = null;
		WechatTokenInfo wechatUserInfo = getTokenByCode(code);
		if (wechatUserInfo != null) {
			//获取用户账号信息
			userAccountBo = userService.getUserAccountByOpenid(wechatUserInfo.getOpenid());
			if (userAccountBo == null) {
				StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/userinfo");
				sb.append("?access_token=").append(wechatUserInfo.getAccess_token()).append("&openid=")
						.append(wechatUserInfo.getOpenid()).append("&lang=zh_CN");
				String result = HttpClientUtils.get(sb.toString());
				JsonNode node = JsonUtils.fromJson(result, JsonNode.class);
				if (node.has("errcode")) {
					return null;
				}
				WechatUser wechatUser = new WechatUser(result);
				//upate user
				UserBo userBo = null;
				if(userId!=null){
					userBo = userService.getUser(userId);
				}else{
					userBo = new UserBo();
				}
				userBo.setHeadImg(wechatUser.getHeadimgurl());
				userBo.setName(wechatUser.getNickname());
				userBo.setCity(wechatUser.getCity());
				int sex = wechatUser.getSex();
				userBo.setGender(sex == 1 ? GenderEnum.MEN.getId() : (sex == 2 ? GenderEnum.WOMEN.getId() : null));
				userId = userService.createOrUpdateUser(userBo);

				//create user account
				userAccountBo = new UserAccountBo();
				userAccountBo.setStatus(StatusEnum.ENABLED.getId());
				userAccountBo.setType(UserAccountTypeEnum.WECHAT_OPENID.getId());
				userAccountBo.setValue(wechatUser.getOpenid());
				userAccountBo.setUserId(userId);
				userService.register(userAccountBo);
				
				if(wechatUser.getUnionid()!=null){
					userAccountBo = new UserAccountBo();
					userAccountBo.setStatus(StatusEnum.ENABLED.getId());
					userAccountBo.setType(UserAccountTypeEnum.WECHAT_UNIONID.getId());
					userAccountBo.setValue(wechatUser.getUnionid());
					userAccountBo.setUserId(userId);
					userService.register(userAccountBo);
				}
			}
		}
		return userAccountBo;
	}
}
