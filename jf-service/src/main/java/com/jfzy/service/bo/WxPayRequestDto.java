package com.jfzy.service.bo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxPayRequestDto {

	// 公众账号ID
	@XmlElement(name = "appid")
	private String appId;
	// 商户号
	@XmlElement(name = "mch_id")
	private String mchId;
	// 设备号
	@XmlElement(name = "device_info")
	private String deviceInfo;

	// 随机字符串
	@XmlElement(name = "nonce_str")
	private String nonceStr;

	// 签名
	@XmlElement(name = "sign")
	private String sign;

	// 签名类型
	@XmlElement(name = "sign_type")
	private String signType;

	// 商品描述
	@XmlElement(name = "body")
	private String body;

	// 商品详情
	@XmlElement(name = "detail")
	private String detail;

	// 附加数据
	@XmlElement(name = "attach")
	private String attach;

	// 商户订单号
	@XmlElement(name = "out_trade_no")
	private String outTradeNo;

	// 订单总金额，单位为分
	@XmlElement(name = "total_fee")
	private int totalFee;

	@XmlElement(name = "spbill_create_ip")
	private String spbillCreateIp;

	// yyyyMMddHHmmss
	@XmlElement(name = "time_start")
	private String timeStart;

	//
	@XmlElement(name = "time_expire")
	private String timeExpire;

	// 订单优惠标记
	@XmlElement(name = "goods_tag")
	private String goodsTag;

	@XmlElement(name = "notify_url")
	private String notifyUrl;

	// 交易类型
	@XmlElement(name = "trade_type")
	private String tradeType = "JSAPI";

	// 商品ID
	@XmlElement(name = "product_id")
	private String productId;

	// 指定支付方式
	@XmlElement(name = "limit_pay")
	private String limitPay;

	// 用户标识
	@XmlElement(name = "openid")
	private String openId;

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
