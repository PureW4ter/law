package com.jfzy.mweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfzf.core.Constants;
import com.jfzy.mweb.vo.WxPayCallbackDto;
import com.jfzy.mweb.vo.WxPayCallbackRespDto;
import com.jfzy.service.OrderService;
import com.jfzy.service.UserService;
import com.jfzy.service.bo.UserAccountBo;
import com.jfzy.service.bo.WxPayEventBo;
import com.jfzy.service.impl.Signature;
import com.jfzy.service.impl.XmlUtil;

@RestController
public class WexinPayController {

	private static Logger logger = LoggerFactory.getLogger(WexinPayController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@ResponseBody
	@PostMapping(value = "/api/wxpay/callback", produces = { "application/xml" })
	public WxPayCallbackRespDto callback(@RequestBody String reqStr) {
		logger.info(reqStr);
		WxPayCallbackDto dto = (WxPayCallbackDto) XmlUtil.fromXml(reqStr, WxPayCallbackDto.class);
		WxPayEventBo event = dtoToBo(dto);
		// check sign
		if (checkSign(dto)) {
			UserAccountBo user = userService.getUserAccountByOpenid(event.getOpenId());
			if (user == null) {
				logger.error("could not find openid");
			} else {
				orderService.markPayed(event, user.getUserId());
			}
		} else {
			logger.error("check sign failed");
		}

		WxPayCallbackRespDto result = new WxPayCallbackRespDto();
		result.setReturnCode("SUCCESS");
		result.setReturnMsg("OK");
		return result;
	}

	private static WxPayEventBo dtoToBo(WxPayCallbackDto dto) {
		WxPayEventBo bo = new WxPayEventBo();
		BeanUtils.copyProperties(dto, bo);
		return bo;
	}

	private static boolean checkSign(WxPayCallbackDto dto) {
		Map<String, String> paramMap = toCallbackParamMap(dto);
		return Signature.checkSign(paramMap, Constants.PAY_SECRET, dto.getSign());
	}

	private static Map<String, String> toCallbackParamMap(WxPayCallbackDto dto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("return_code", dto.getReturnCode());
		paramMap.put("return_msg", dto.getReturnMsg());
		paramMap.put("appid", dto.getAppId());
		paramMap.put("mch_id", dto.getMchId());
		paramMap.put("device_info", dto.getDeviceInfo());
		paramMap.put("nonce_str", dto.getNonceStr());
		paramMap.put("sign_type", dto.getSignType());
		paramMap.put("result_code", dto.getResultCode());
		paramMap.put("err_code", dto.getErrCode());
		paramMap.put("err_code_des", dto.getReturnMsg());
		paramMap.put("openid", dto.getOpenId());
		paramMap.put("is_subscribe", dto.getIsSubscribe());
		paramMap.put("trade_type", dto.getTradeType());
		paramMap.put("bank_type", dto.getBankType());
		paramMap.put("total_fee", dto.getTotalFee());
		paramMap.put("settlement_total_fee", dto.getSettlementTotalFee());
		paramMap.put("fee_type", dto.getFeeType());
		paramMap.put("transaction_id", dto.getTransactionId());
		paramMap.put("out_trade_no", dto.getOutTradeNo());
		paramMap.put("attach", dto.getAttach());
		paramMap.put("time_end", dto.getTimeEnd());
		paramMap.put("cash_fee", dto.getCashFee());

		return paramMap;
	}
}
