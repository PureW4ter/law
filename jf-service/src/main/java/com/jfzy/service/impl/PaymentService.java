package com.jfzy.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jfzf.core.Constants;
import com.jfzf.core.HttpClientUtils;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.WxPayRequestDto;
import com.jfzy.service.bo.WxPayResponseDto;
import com.jfzy.service.exception.JfApplicationRuntimeException;

@Service
public class PaymentService {

	private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Value("${wechat.pay.unify.url}")
	private String PAY_URL;

	@Value("${wechat.pay.notify.url}")
	private String NOTIFY_URL;

	public static void main(String[] args) throws IOException {

	}

	public WxPayResponseDto unifiedOrder(OrderBo order, String ip, String openId) {
		WxPayRequestDto dto = new WxPayRequestDto();
		dto.setAppId(Constants.APP_ID);
		dto.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
		dto.setMchId(Constants.MCH_ID);
		dto.setBody(String.format("简法二手房-%s", order.getProductName()));
		dto.setOutTradeNo(order.getSn());
		dto.setTotalFee(getTotalFee(order.getRealPrice()));
		dto.setSpbillCreateIp(ip);
		dto.setNotifyUrl(NOTIFY_URL);
		dto.setTradeType(Constants.TRADE_TYPE);
		dto.setOpenId(openId);

		sign(dto, Constants.PAY_SECRET);

		String xmlStr = XmlUtil.parseXml(dto);
		logger.error(xmlStr);
		String response;
		try {
			response = HttpClientUtils.post(PAY_URL, xmlStr);
			logger.error(response);
			WxPayResponseDto responseDto = (WxPayResponseDto) XmlUtil.fromXml(response, WxPayResponseDto.class);
			if (!StringUtils.equals("SUCCESS", responseDto.getResultCode())
					|| !StringUtils.equals("SUCCESS", responseDto.getReturnCode())) {
				logger.error(String.format("Failed to do prepay request:%s", xmlStr));
				logger.error(String.format("Failed to do prepay response:%s", response));
			}
			return responseDto;
		} catch (IOException e) {
			throw new JfApplicationRuntimeException("获取支付单失败", e);
		}
	}

	private static void sign(WxPayRequestDto dto, String key) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", dto.getAppId());
		paramMap.put("mch_id", dto.getMchId());
		paramMap.put("nonce_str", dto.getNonceStr());
		paramMap.put("body", dto.getBody());
		paramMap.put("out_trade_no", dto.getOutTradeNo());
		paramMap.put("total_fee", String.valueOf(dto.getTotalFee()));
		paramMap.put("spbill_create_ip", dto.getSpbillCreateIp());
		paramMap.put("notify_url", dto.getNotifyUrl());
		paramMap.put("trade_type", dto.getTradeType());
		paramMap.put("openid", dto.getOpenId());
		paramMap.put("spbill_create_ip", dto.getSpbillCreateIp());
		paramMap.put("attach", dto.getAttach());

		String sign = Signature.getSign(paramMap, key);
		dto.setSign(sign);
	}

	private static int getTotalFee(double realPrice) {
		BigDecimal dec = BigDecimal.valueOf(realPrice * 100);
		return dec.intValue();
	}

}
