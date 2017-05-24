package com.jfzy.service.impl;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfzf.core.Constants;
import com.jfzf.core.HttpClientUtils;
import com.jfzf.core.JsonUtils;
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
	

	
	@Autowired
	private UserService userService;
	
	 WechatTokenInfo getTokenByCode(String code) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(Constants.APP_ID);
		sb.append("&secret=").append(Constants.SECRET);
		sb.append("&code=").append(code);
		sb.append("&grant_type=authorization_code");
		String result = HttpClientUtils.getResponseText(sb.toString());
		JsonNode node = JsonUtils.fromJson(result, JsonNode.class);
		if (node.has("errcode")){
			return null;
		}
		return JsonUtils.fromJson(result, WechatTokenInfo.class);
	}
	
	public UserBo wxlogin(String code) throws IOException  {
		UserAccountBo userAccountBo = null;
		WechatTokenInfo wechatUserInfo = getTokenByCode(code);
		UserBo userBo = null;
		
		if (wechatUserInfo != null) {
			//获取用户账号信息，account 存在必然 user 存在
			userAccountBo = userService.getUserAccountByOpenid(wechatUserInfo.getOpenid());
			if(userAccountBo !=null){
				userBo = userService.getUser(userAccountBo.getUserId());
			}else{
				StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/userinfo");
				sb.append("?access_token=").append(wechatUserInfo.getAccess_token()).append("&openid=")
						.append(wechatUserInfo.getOpenid()).append("&lang=zh_CN");
				String result = HttpClientUtils.get(sb.toString());
				JsonNode node = JsonUtils.fromJson(result, JsonNode.class);
				if (node.has("errcode")) {
					return null;
				}
				WechatUser wechatUser = new WechatUser(result);
				
				//create user
				userBo = new UserBo();
				userBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				userBo.setHeadImg(wechatUser.getHeadimgurl());
				userBo.setName(wechatUser.getNickname());
				userBo.setRealName(wechatUser.getNickname());
				userBo.setCity(wechatUser.getCountry() + "," + wechatUser.getProvince() + "," + wechatUser.getCity());
				int sex = wechatUser.getSex();
				userBo.setGender(sex == 1 ? GenderEnum.MEN.getId() : (sex == 2 ? GenderEnum.WOMEN.getId() : null));
				userBo =  userService.createOrUpdateUser(userBo);

				//create user account
				userAccountBo = new UserAccountBo();
				userAccountBo.setStatus(StatusEnum.ENABLED.getId());
				userAccountBo.setType(UserAccountTypeEnum.WECHAT_OPENID.getId());
				userAccountBo.setValue(wechatUser.getOpenid());
				userAccountBo.setUserId(userBo.getId());
				userAccountBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
				userService.register(userAccountBo);
				
				if(wechatUser.getUnionid()!=null){
					userAccountBo = new UserAccountBo();
					userAccountBo.setStatus(StatusEnum.ENABLED.getId());
					userAccountBo.setType(UserAccountTypeEnum.WECHAT_UNIONID.getId());
					userAccountBo.setValue(wechatUser.getUnionid());
					userAccountBo.setUserId(userBo.getId());
					userAccountBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					userService.register(userAccountBo);
				}
			}
		}
		return userBo;
		/*return userService.getUser(new Integer(code));*/
	}
}
