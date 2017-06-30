package com.jfzy.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfzf.core.HttpClientUtils;
import com.jfzy.service.AccessTokenService;
import com.jfzy.service.LawyerService;
import com.jfzy.service.NotificationService;
import com.jfzy.service.OrderService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.LawyerBo;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.OrderStatusEnum;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.UserAccountTypeEnum;
import com.jfzy.service.weixin.dto.MessageRequestDto;
import com.jfzy.service.weixin.dto.MessageRequestDto.MessageData;
import com.jfzy.service.weixin.dto.MessageRequestDto.MessageLine;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final String TEMPLATE_NOTIFY_COMPLETE = "IDIlLV4zDpjXlK7dhmto_uZaug4ruTysvZ3L76oQBPDxA";
	private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private static final int PAGE_SIZE = 20;

	@Autowired
	private OrderService orderService;

	@Autowired
	private LawyerService lawyerService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccessTokenService tokenService;

	private static final int[] CITIES = { 1, 2, 3 };

	private void orderAssignmentNotify(OrderBo order) {
		if (order != null && order.getStatus() == OrderStatusEnum.DISPATCHED.getId()) {
			LawyerBo lawyer = lawyerService.getLawyerById(order.getLawyerId());
			if (lawyer != null && StringUtils.isNotBlank(lawyer.getOpenId())
					&& StringUtils.isNotBlank(lawyer.getPhoneNum())) {
				notifyByWechat(order, lawyer.getOpenId());
				// finally update status
				orderService.updateOrderStatus(order.getId(), OrderStatusEnum.DISPATCHED.getId(),
						OrderStatusEnum.DISPATCHED_NOTIFIED.getId());
			} else {
				// log
			}

		}
	}

	private void notifyByWechat(OrderBo order, String openId) {
		// TODO
	}

	@Override
	public void notifyForAssignment() {
		for (int i = 0; i < CITIES.length; ++i) {
			int count = orderService.getNumbersOfUnAssignedOrdersByCity(CITIES[i]);
			if (count > 0) {

			}
		}
	}

	@Override
	public void notifyForConfirm() {
		List<OrderBo> orders = orderService.getUnconfirmedOrders(PAGE_SIZE);

		do {
			if (orders != null) {
				orders.forEach(order -> orderAssignmentNotify(order));
			}
		} while (orders != null && orders.size() == PAGE_SIZE);

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

	private void notifyOrderCompleteByWechat(String openId, OrderBo bo) {
		MessageRequestDto dto = new MessageRequestDto();
		dto.setTemplate_id(TEMPLATE_NOTIFY_COMPLETE);
		dto.setTouser(openId);
		MessageData data = new MessageData();
		dto.setData(data);
		MessageLine first = new MessageLine();
		first.setValue(String.format("您关于的咨询订单已完成"));
		MessageLine keyword1 = new MessageLine();
		keyword1.setValue(bo.getOrderNum());
		MessageLine keyword2 = new MessageLine();
		keyword2.setValue(String.valueOf(bo.getRealPrice()));
		MessageLine keyword3 = new MessageLine();
		keyword3.setValue(bo.getProductName());
		MessageLine keyword4 = new MessageLine();
		keyword4.setValue("");
		MessageLine remark = new MessageLine();
		remark.setValue("请至\"我的订单\"中查看");
		data.setFirst(first);
		data.setKeyword1(keyword1);
		data.setKeyword2(keyword2);
		data.setKeyword3(keyword3);
		data.setKeyword4(keyword4);
		data.setRemark(remark);

		ObjectMapper mapper = new ObjectMapper();

		String token = tokenService.getAccessToken();
		logger.info(token);
		if (StringUtils.isNotBlank(token)) {
			try {
				String messageBody = mapper.writeValueAsString(dto);
				logger.info(messageBody);
				String response = HttpClientUtils.post(String.format(MESSAGE_URL, token), messageBody);
				logger.error(response);
			} catch (IOException e) {
				logger.error("通知失败", e);
			}
		}
	}
}