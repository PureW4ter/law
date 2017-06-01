package com.jfzy.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jfzf.core.Constants;
import com.jfzf.core.HttpClientUtils;
import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.bo.WxPayRequestDto;
import com.jfzy.service.exception.JfApplicationRuntimeException;

@Service
public class PaymentService {

	@Value("${wechat.pay.unify.url}")
	private String PAY_URL;

	@Value("${wechat.pay.notify.url}")
	private String NOTIFY_URL;

	private static String parseXml(Object obj) {
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			StringWriter sw = new StringWriter();

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(obj, sw);
			return sw.toString();
		} catch (JAXBException e) {
			throw new JfApplicationRuntimeException("Malformed vo of parseXml", e);
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println();
	}

	public void unifiedOrder(OrderBo order, String ip, String openId) {
		WxPayRequestDto dto = new WxPayRequestDto();
		dto.setAppId(Constants.WX_ID);
		dto.setNonceStr(UUID.randomUUID().toString().replace("-", ""));
		dto.setMchId(Constants.MCH_ID);
		dto.setDeviceInfo(Constants.DEVICE_INFO);
		dto.setSignType(Constants.SIGN_TYPE);
		dto.setBody(order.getProductName());
		dto.setOutTradeNo(order.getSn());
		dto.setTotalFee(getTotalFee(order.getRealPrice()));
		dto.setSpbillCreateIp(ip);
		dto.setTimeStart(getStartTime());
		dto.setTimeExpire(getExpireTime());
		dto.setNotifyUrl(NOTIFY_URL);
		dto.setTradeType(Constants.TRADE_TYPE);
		dto.setOpenId(openId);

		String xmlStr = parseXml(dto);
		String response;
		try {
			response = HttpClientUtils.post(PAY_URL, xmlStr);
		} catch (IOException e) {
			throw new JfApplicationRuntimeException("获取支付单失败", e);
		}
		System.out.println(response);

	}

	private static int getTotalFee(double realPrice) {
		BigDecimal dec = BigDecimal.valueOf(realPrice * 100);
		return dec.intValue();
	}

	private static String getStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	private static String getExpireTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date expireTime = new Date(new Date().getTime() + Constants.PAY_ORDER_EXPIRE_INTERVAL);
		return sdf.format(expireTime);
	}

}
