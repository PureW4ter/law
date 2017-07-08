package com.jfzy.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzf.core.HttpClientUtils;
import com.jfzy.service.AccessTokenService;
import com.jfzy.service.NotificationService;
import com.jfzy.service.OrderService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.weixin.dto.MessageRequestDto;
import com.jfzy.service.weixin.dto.MessageRequestDto.MessageData;
import com.jfzy.service.weixin.dto.MessageRequestDto.MessageLine;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final String TEMPLATE_NOTIFY_COMPLETE = "IlLV4zDpjXlK7dhmto_uZaug4ruTysvZ3L76oQBPDxA";
	private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	private static final String TIME_FORMAT = "MM月dd日HH时mm分";

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccessTokenService tokenService;

	private static final int[] CITIES = { 1, 2, 3 };

	@Override
	public void notifyForAssignment() {
		for (int i = 0; i < CITIES.length; ++i) {
			int count = orderService.getNumbersOfUnAssignedOrdersByCity(CITIES[i]);
			if (count > 0) {

			}
		}
	}

	@Override
	public void completeNotify(int orderId) {
		OrderBo order = orderService.getOrderById(orderId);
		if (order != null) {
			UserAccountBo bo = userService.getUserAccountByUserId(order.getUserId(),
					UserAccountTypeEnum.WECHAT_OPENID.getId());
			if (bo != null && StringUtils.isNotBlank(bo.getValue())) {
				notifyOrderCompleteByWechat(bo.getValue(), order);
			}
		}
	}

	private String getFirstContent(OrderBo bo) {
		if (StringUtils.isNotBlank(bo.getRole()) && StringUtils.isNotBlank(bo.getTradeSubphase())) {
			return String.format("您关于[%s-%s]的订单已回复", bo.getRole(), bo.getTradeSubphase());
		} else {
			return String.format("您的[%s]的订单已回复", bo.getProductName());
		}
	}

	private String getUpdateTime(Timestamp updateTime) {
		Date updateDate = null;
		if (updateTime != null) {
			updateDate = new Date(updateTime.getTime());
		} else {
			updateDate = new Date();
		}

		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(updateDate);
	}

	private void notifyOrderCompleteByWechat(String openId, OrderBo bo) {
		MessageRequestDto dto = new MessageRequestDto();
		dto.setTemplate_id(TEMPLATE_NOTIFY_COMPLETE);
		dto.setTouser(openId);
		MessageData data = new MessageData();
		dto.setData(data);
		MessageLine first = new MessageLine();
		first.setValue(getFirstContent(bo));
		MessageLine keyword1 = new MessageLine();
		keyword1.setValue(bo.getOrderNum());
		MessageLine keyword2 = new MessageLine();
		keyword2.setValue(String.valueOf(bo.getRealPrice()));
		MessageLine keyword3 = new MessageLine();
		keyword3.setValue("已完成");
		MessageLine keyword4 = new MessageLine();
		keyword4.setValue(getUpdateTime(bo.getUpdateTime()));
		MessageLine remark = new MessageLine();
		remark.setValue("请至\"我的订单\"中查看回复");
		data.setFirst(first);
		data.setKeyword1(keyword1);
		data.setKeyword2(keyword2);
		data.setKeyword3(keyword3);
		data.setKeyword4(keyword4);
		data.setRemark(remark);

		ObjectMapper mapper = new ObjectMapper();

		String token = tokenService.getAccessToken();
		if (StringUtils.isNotBlank(token)) {
			try {
				String messageBody = mapper.writeValueAsString(dto);
				String response = HttpClientUtils.post(String.format(MESSAGE_URL, token), messageBody);
			} catch (IOException e) {
				logger.error("通知失败", e);
			}
		}
	}
}