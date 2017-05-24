package com.jfzy.mweb.vo;

public class OrderCompleteVo {
	private int id;
	private String comment;
	private String[] picList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String[] getPicList() {
		return picList;
	}
	public void setPicList(String[] picList) {
		this.picList = picList;
	}
}
