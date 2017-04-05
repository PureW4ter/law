package com.destinesia.entity;

import java.util.Date;

public class Vote {
	private String id;
	private Date vodeDate;
	private String userId;
	private String albumId;
	private String pictureId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getVodeDate() {
		return vodeDate;
	}
	public void setVodeDate(Date vodeDate) {
		this.vodeDate = vodeDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
}
