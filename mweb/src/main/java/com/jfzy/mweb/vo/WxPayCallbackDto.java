package com.jfzy.mweb.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxPayCallbackDto {

	private String returnCode;
	private String returnMsg;
	private String appId;
	private String attach;
	private String bankType;
	private String feeType;
	private String isSubscribe;
	private String mchId;
	private String nonceStr;
	private String openId;
	private String outTradeNo;
	private String resultCode;
	private String sign;
	private String subMchId;
	private String timeEnd;
	private String totalFee;
	private String tradeType;
	private String transactionId;
	private String deviceInfo;
	private String signType;
	private String errCode;
	private String settlementTotalFee;
	private String cashFee;

	@XmlElement(name = "return_code")
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	@XmlElement(name = "return_msg")
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@XmlElement(name = "appid")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@XmlElement(name = "mch_id")
	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	@XmlElement(name = "nonce_str")
	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	@XmlElement(name = "attach")
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@XmlElement(name = "bank_type")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@XmlElement(name = "fee_type")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@XmlElement(name = "is_subscribe")
	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	@XmlElement(name = "openid")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@XmlElement(name = "out_trade_no")
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@XmlElement(name = "result_code")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@XmlElement(name = "sign")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@XmlElement(name = "sub_mch_id")
	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	@XmlElement(name = "time_end")
	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	@XmlElement(name = "total_fee")
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	@XmlElement(name = "trade_type")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@XmlElement(name = "transaction_id")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@XmlElement(name = "device_info")
	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	@XmlElement(name = "sign_type")
	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@XmlElement(name = "err_code")
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	@XmlElement(name = "settlement_total_fee")
	public String getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(String settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	@XmlElement(name = "cash_fee")
	public String getCashFee() {
		return cashFee;
	}

	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}

}
