package com.destinesia.entity;

import java.util.Date;

public class View {
	private String id;
	private String albumId;
	private String deviceNO;
	private String ipAddress;
	private Date viewDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getDeviceNO() {
		return deviceNO;
	}
	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getViewDate() {
		return viewDate;
	}
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}
}
