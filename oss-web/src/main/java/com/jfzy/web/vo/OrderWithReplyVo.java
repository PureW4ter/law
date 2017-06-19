package com.jfzy.web.vo;

import java.util.List;

public class OrderWithReplyVo {
	private OrderVo orderVo;
	private LawyerReplyVo lawyerReplyVo;
	private List<OrderPhotoVo> orderPhotoList;
	private List<OrderPhotoVo> replyPhotoList;
	
	public OrderVo getOrderVo() {
		return orderVo;
	}
	public void setOrderVo(OrderVo orderVo) {
		this.orderVo = orderVo;
	}
	public LawyerReplyVo getLawyerReplyVo() {
		return lawyerReplyVo;
	}
	public void setLawyerReplyVo(LawyerReplyVo lawyerReplyVo) {
		this.lawyerReplyVo = lawyerReplyVo;
	}
	public List<OrderPhotoVo> getOrderPhotoList() {
		return orderPhotoList;
	}
	public void setOrderPhotoList(List<OrderPhotoVo> orderPhotoList) {
		this.orderPhotoList = orderPhotoList;
	}
	public List<OrderPhotoVo> getReplyPhotoList() {
		return replyPhotoList;
	}
	public void setReplyPhotoList(List<OrderPhotoVo> replyPhotoList) {
		this.replyPhotoList = replyPhotoList;
	}
}
