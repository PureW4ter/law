package com.destinesia.entity.dto;

import com.destinesia.entity.Region;

public class RegionDTO {
	private String id;
	private String country;
	private String province;
	private String city;
	private String district;
	private String road;
	private String countryCN;
	private String provinceCN;
	private String cityCN;
	private String districtCN;
	private String roadCN;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCountryCN() {
		return countryCN;
	}
	public void setCountryCN(String countryCN) {
		this.countryCN = countryCN;
	}
	public String getProvinceCN() {
		return provinceCN;
	}
	public void setProvinceCN(String provinceCN) {
		this.provinceCN = provinceCN;
	}
	public String getCityCN() {
		return cityCN;
	}
	public void setCityCN(String cityCN) {
		this.cityCN = cityCN;
	}
	public String getDistrictCN() {
		return districtCN;
	}
	public void setDistrictCN(String districtCN) {
		this.districtCN = districtCN;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getRoadCN() {
		return roadCN;
	}
	public void setRoadCN(String roadCN) {
		this.roadCN = roadCN;
	}
	
	public static Region convert(RegionDTO dto){
		Region region = new Region();
		region.setId(dto.getId());
		region.setCity(dto.getCity());
		region.setCountry(dto.getCountry());
		region.setDistrict(dto.getDistrict());
		region.setProvince(dto.getProvince());
		region.setRoad(dto.getRoad());
		region.setCityCN(dto.getCityCN());
		region.setCountryCN(dto.getCountryCN());
		region.setDistrictCN(dto.getDistrictCN());
		region.setProvinceCN(dto.getProvinceCN());
		region.setRoadCN(dto.getRoadCN());
		return region;
	}
	public static RegionDTO revert(Region region){
		RegionDTO dto = new RegionDTO();
		dto.setId(region.getId());
		dto.setCity(region.getCity());
		dto.setCountry(region.getCountry());
		dto.setDistrict(region.getDistrict());
		dto.setProvince(region.getProvince());
		dto.setRoad(region.getRoad());
		dto.setCityCN(region.getCityCN());
		dto.setCountryCN(region.getCountryCN());
		dto.setDistrictCN(region.getDistrictCN());
		dto.setProvinceCN(region.getProvinceCN());
		dto.setRoadCN(region.getRoadCN());
		return dto;
	}
}
