package com.jfzy.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.jfzy.service.SmsService;
import com.jfzy.service.exception.JfApplicationRuntimeException;
import com.jfzy.service.exception.JfErrorCodeRuntimeException;

@Service
public class SmsServiceImpl implements SmsService {

	private static final String TEMPLATE_REGISTER = "SMS_69015764";
	private static final String TEMPLATE_NOTIFY = "SMS_70620533";
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

	public void sendLawyerNotify(String lawyerPhone, String productType, String price, String time, String content) {
		if (StringUtils.isNotBlank(lawyerPhone)) {
			BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
			smsReceiverParams.setParam("productType", productType);
			smsReceiverParams.setParam("price", price);
			smsReceiverParams.setParam("time", time);
			smsReceiverParams.setParam("content", "回复报告");
			smsReceiverParams.setParam("tel", "17602105663");
			send(smsReceiverParams, lawyerPhone, TEMPLATE_NOTIFY);
		} else {
			throw new JfApplicationRuntimeException("Sms phone num is empty");
		}
	}

	private void send(BatchSmsAttributes.SmsReceiverParams smsReceiverParams, String phoneNum, String template) {

		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		batchSmsAttributes.setFreeSignName(SIGN_NAME);
		batchSmsAttributes.setTemplateCode(template);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
		batchSmsAttributes.addSmsReceiver(phoneNum, smsReceiverParams);

		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef(TOPIC_NAME);
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		try {
			/**
			 * Step 4. 发布SMS消息
			 */
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);

		} catch (RuntimeException e) {
			throw new JfErrorCodeRuntimeException(400, "短信验证码发送失败", "SMS-SEND failed", e);
		}

	}

	@Override
	public void sendRegisterCode(String phoneNum, String code) {
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam(PARAM_CODE, code);
		smsReceiverParams.setParam(PARAM_PRODUCT, VALUE_PRODUCT);
		send(smsReceiverParams, phoneNum, TEMPLATE_REGISTER);
	}
}
