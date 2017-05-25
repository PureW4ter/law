package com.jfzy.web.vo;

public class SearchOrderVo {
	
	private int id;
	private String sn;
	private int userId;
	private int productId;
	private Double originPrice;
	private Double realPrice;
	private String role;
	private String tradePhase;
	private String tradeSubphase;
	private String memo;
	
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
