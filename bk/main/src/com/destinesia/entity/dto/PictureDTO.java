package com.destinesia.entity.dto;

import java.util.Date;

import com.destinesia.entity.Picture;

public class PictureDTO {
	private String id;
	private String albumId;
	private String name;
	private String description;
	private float longitude;
	private float latitude;
	private double altitude;
	private String path;
	private int type;
	private RegionDTO region;
	private Date createDate;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
	public RegionDTO getRegion() {
		return region;
	}
	public void setRegion(RegionDTO region) {
		this.region = region;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static Picture convert(PictureDTO dto){
		Picture pic = new Picture();
		pic.setId(dto.getId());
		pic.setAlbumId(dto.getAlbumId());
		pic.setName(dto.getName());
		pic.setDescription(dto.getDescription());
		pic.setLatitude(dto.getLatitude());
		pic.setLongitude(dto.getLongitude());
		pic.setAltitude(dto.getAltitude());
		pic.setPath(dto.getPath());
		pic.setType(dto.getType());
		pic.setCreateDate(dto.getCreateDate());
		return pic;
	}
	public static PictureDTO revert(Picture picture){
		PictureDTO dto = new PictureDTO();
		dto.setId(picture.getId());
		dto.setAlbumId(picture.getAlbumId());
		dto.setName(picture.getName());
		dto.setDescription(picture.getDescription());
		dto.setLatitude(picture.getLatitude());
		dto.setLongitude(picture.getLongitude());
		dto.setAltitude(picture.getAltitude());
		dto.setRegion(RegionDTO.revert(picture.getRegion()));
		dto.setPath(picture.getPath());
		dto.setType(picture.getType());
		dto.setCreateDate(picture.getCreateDate());
		return dto;
	}
}
