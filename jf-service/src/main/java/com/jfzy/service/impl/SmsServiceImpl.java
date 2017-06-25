package com.jfzy.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.jfzy.service.SmsService;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;

@Service
public class SmsServiceImpl implements SmsService {

	private static final String TEMPLATE_REGISTER = "SMS_69015764";
	private static final String PARAM_CODE = "code";
	private static final String PARAM_PRODUCT = "product";
	private static final String VALUE_PRODUCT = "简法二手房";
	private static final String TOPIC_NAME = "sms.topic-cn-hangzhou";
	private static final String AK = "LTAIN0qPhmFNCzmN";
	private static final String AS = "IYCtzSGmdcFDHiweelCPyfUnvhjlcz";
	private static final String SIGN_NAME = "简法";
	private static final String END_POINT = "https://1852665807732754.mns.cn-hangzhou.aliyuncs.com/";

	private CloudAccount account;

	@PostConstruct
	public void init() {
		account = new CloudAccount(AK, AS, END_POINT);
	}

	public static void main(String[] args) {
		SmsServiceImpl s = new SmsServiceImpl();
		s.init();
		s.sendRegisterCode("18616800563", "1234");
	}

	@Override
	public void sendRegisterCode(String phoneNum, String code) {

		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(TOPIC_NAME);
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName(SIGN_NAME);
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode(TEMPLATE_REGISTER);
		// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam(PARAM_CODE, code);
		smsReceiverParams.setParam(PARAM_PRODUCT, VALUE_PRODUCT);
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(phoneNum, smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);

		} catch (RuntimeException e) {
			throw new JfErrorCodeRuntimeException(400, "短信验证码发送失败", "SMS-SEND failed", e);
		}
	}
}
