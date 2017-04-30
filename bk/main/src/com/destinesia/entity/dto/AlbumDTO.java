package com.destinesia.entity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.destinesia.entity.Album;
import com.destinesia.entity.Picture;

public class AlbumDTO {
	private String token;
	private String id;
	private String name;
	private String description;
	private String cover;
	private List<PictureDTO> picList;
	private double longitude;
	private double latitude;
	private double altitude;
	private String userNickName;
	private int userGrade;
	private RegionDTO region;
	private int picCount;
	private Date createDate;
	private String road;
	private double distance;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public List<PictureDTO> getPicList() {
		return picList;
	}
	public void setPicList(List<PictureDTO> picList) {
		this.picList = picList;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public static AlbumDTO convertToDTO(Album o){
		AlbumDTO dto = new AlbumDTO();	
		
		return dto;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public int getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(int userGrade) {
		this.userGrade = userGrade;
	}
	public RegionDTO getRegion() {
		return region;
	}
	public void setRegion(RegionDTO region) {
		this.region = region;
	}
	public int getPicCount() {
		return picCount;
	}
	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public static Album convert(AlbumDTO dto){
		Album album = new Album();
		album.setId(dto.getId());
		album.setName(dto.getName());
		album.setDescription(dto.getDescription());
		album.setCover(dto.getCover());
		album.setLatitude(dto.getLatitude());
		album.setLongitude(dto.getLongitude());
		album.setAltitude(dto.getAltitude());
		album.setDistance(dto.getDistance());
		album.setRoad(dto.getRoad());
		List<Picture> list = new ArrayList<Picture>();
		for(PictureDTO picDto : dto.getPicList()){
			list.add(PictureDTO.convert(picDto));
		}
		album.setPicList(list);
		return album;
	}

	public static AlbumDTO revert(Album album){
		AlbumDTO dto = new AlbumDTO();
		dto.setId(album.getId());
		dto.setName(album.getName());
		dto.setDescription(album.getDescription());
		dto.setCover(album.getCover());
		dto.setUserNickName(album.getUser().getNickName());
		dto.setUserGrade(album.getUser().getGrade());
		dto.setPicCount(album.getPicList().size());
		dto.setRegion(RegionDTO.revert(album.getRegion()));
		dto.setLatitude(album.getLatitude());
		dto.setLongitude(album.getLongitude());
		dto.setAltitude(album.getAltitude());
		dto.setCreateDate(album.getCreateDate());
		dto.setRoad(album.getRoad());
		dto.setDistance(album.getDistance());
		List<PictureDTO> list = new ArrayList<PictureDTO>();
		for(Picture pic : album.getPicList()){
			list.add(PictureDTO.revert(pic));
		}
		dto.setPicList(list);
		return dto;
	}
	
}
