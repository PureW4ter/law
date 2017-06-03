package com.jfzy.service.bo;

import java.sql.Timestamp;

public class OrderBo {

	private int id;
	private String sn;
	private int cityId;
	private int userId;
	private String userName;
	private String userPhoneNum;
	private int lawyerId;
	private String lawyerName;
	private String lawyerPhoneNum;
	private int processorId;
	private String processorName;
	private int productId;
	private String productName;
	private String productCode;
	private Double originPrice;
	private Double realPrice;
	private int status;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp phoneEndTime;
	private String role;
	private String tradePhase;
	private String tradeSubphase;
	private int payWay;
	private int payStatus;
	private String memo;
	private String email;
	private String address;
	private String ownerName;
	private String ownerPhone;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public int getLawyerId() {
		return lawyerId;
	}

	public void setLawyerId(int lawyerId) {
		this.lawyerId = lawyerId;
	}

	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getLawyerPhoneNum() {
		return lawyerPhoneNum;
	}

	public void setLawyerPhoneNum(String lawyerPhoneNum) {
		this.lawyerPhoneNum = lawyerPhoneNum;
	}

	public int getProcessorId() {
		return processorId;
	}

	public void setProcessorId(int processorId) {
		this.processorId = processorId;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Double originPrice) {
		this.originPrice = originPrice;
	}

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTradePhase() {
		return tradePhase;
	}

	public void setTradePhase(String tradePhase) {
		this.tradePhase = tradePhase;
	}

	public String getTradeSubphase() {
		return tradeSubphase;
	}

	public void setTradeSubphase(String tradeSubphase) {
		this.tradeSubphase = tradeSubphase;
	}

	public int getPayWay() {
		return payWay;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public Timestamp getPhoneEndTime() {
		return phoneEndTime;
	}
	
	public void setPhoneEndTime(Timestamp phoneEndTime) {
		this.phoneEndTime = phoneEndTime;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
}
