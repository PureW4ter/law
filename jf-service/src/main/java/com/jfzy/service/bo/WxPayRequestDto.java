package com.jfzy.service.bo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxPayRequestDto {

	private String appId;
	private String attach;
	private String body;
	private String mchId;
	private String nonceStr;
	private String notifyUrl;
	private String openId;

	private String outTradeNo;
	private String spbillCreateIp;
	private int totalFee;
	private String tradeType = "JSAPI";

	private String sign;

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	// 公众账号ID
	@XmlElement(name = "appid")
	public String getAppId() {
		return appId;
	}

	// 商户号
	@XmlElement(name = "mch_id")
	public String getMchId() {
		return mchId;
	}

	// 随机字符串
	@XmlElement(name = "nonce_str")
	public String getNonceStr() {
		return nonceStr;
	}

	// 签名
	@XmlElement(name = "sign")
	public String getSign() {
		return sign;
	}

	// 商品描述
	@XmlElement(name = "body")
	public String getBody() {
		return body;
	}

	// 附加数据
	@XmlElement(name = "attach")
	public String getAttach() {
		return attach;
	}

	// 商户订单号
	@XmlElement(name = "out_trade_no")
	public String getOutTradeNo() {
		return outTradeNo;
	}

	// 订单总金额，单位为分
	@XmlElement(name = "total_fee")
	public int getTotalFee() {
		return totalFee;
	}

	@XmlElement(name = "spbill_create_ip")
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	@XmlElement(name = "notify_url")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	// 交易类型
	@XmlElement(name = "trade_type")
	public String getTradeType() {
		return tradeType;
	}

	// 用户标识
	@XmlElement(name = "openid")
	public String getOpenId() {
		return openId;
	}

}
