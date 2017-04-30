package com.destinesia.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.destinesia.base.LJConstants;
import com.destinesia.base.StatusMsg;
import com.destinesia.base.Utility;
import com.destinesia.dao.IUserDao;
import com.destinesia.entity.User;
import com.destinesia.entity.dto.SmsCodeDTO;
import com.destinesia.service.ISmsService;

import net.sf.json.JSONObject;

@Service
public class SmsServiceImpl implements ISmsService {
	
	
	@Autowired
	private IUserDao userDao;

	@Override
	public void regist(String phone) {
		User user = userDao.findUserByPhone(phone);
		if(user!=null){
			throw new StatusMsg(StatusMsg.CODE_ALREADY_EXISTS, "已存在关联此号码的账号，请尝试登录");
		}
		
		Map<String, String> messages = new HashMap<String, String>();
		SmsCodeDTO smsCodeDto = new SmsCodeDTO();
		smsCodeDto.setPhone(phone);
		smsCodeDto.setCreateTime(new Date().getTime());
		smsCodeDto.setCode(Utility.getSmsCode(4));
		Utility.smsMap.put(phone, smsCodeDto);
		
		messages.put("code", smsCodeDto.getCode());
		send(phone, "SMS_36100138", messages);
	}

	@Override
	public void login(String phone) {
		User user = userDao.findUserByPhone(phone);
		if(user==null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "没有关联此号码的账号，请尝试更换");
		}
		
		Map<String, String> messages = new HashMap<String, String>();
		SmsCodeDTO smsCodeDto = new SmsCodeDTO();
		smsCodeDto.setPhone(phone);
		smsCodeDto.setCreateTime(new Date().getTime());
		smsCodeDto.setCode(Utility.getSmsCode(4));
		Utility.smsMap.put(phone, smsCodeDto);
		
		messages.put("code", smsCodeDto.getCode());
		send(phone, "SMS_35855104", messages);
	}
	
	@Override
	public void reset(String phone) {
		User user = userDao.findUserByPhone(phone);
		if(user==null){
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "没有关联此号码的账号，请尝试更换");
		}
		
		Map<String, String> messages = new HashMap<String, String>();
		SmsCodeDTO smsCodeDto = new SmsCodeDTO();
		smsCodeDto.setPhone(phone);
		smsCodeDto.setCreateTime(new Date().getTime());
		smsCodeDto.setCode(Utility.getSmsCode(4));
		Utility.smsMap.put(phone, smsCodeDto);
		
		messages.put("code", smsCodeDto.getCode());
		send(phone, "SMS_35975064", messages);
	}
	
	private void send(String phone, String selfTemplateName, Map<String, String> messages){
		JSONObject object = JSONObject.fromObject(messages);
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", LJConstants.appkey, LJConstants.secret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName("映象机");
			request.setTemplateCode(selfTemplateName);
			request.setParamString(object.toString());
			request.setRecNum(phone);
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
		} catch (Exception e) {
			throw new StatusMsg(StatusMsg.CODE_BAD_REQUEST, "发送验证码失败");
		}
	}

	@Override
	public void valideCode(String phone, String passcode) {
		if(Utility.smsMap.get(phone) ==null || !Utility.smsMap.get(phone).getCode().equals(passcode)){
			throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码不正确，请重新输入");
		}
		long createTime = Utility.smsMap.get(phone).getCreateTime();
		if(new Date().getTime()-createTime>15*50*1000){
			throw new StatusMsg(StatusMsg.CODE_BAD_PASSCODE, "手机验证码输入超时，请重新输入");
		}
	}
}
