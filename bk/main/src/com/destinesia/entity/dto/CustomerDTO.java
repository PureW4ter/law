package com.destinesia.entity.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.destinesia.entity.Customer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {
	private String id;
	private String userId;
	private String name;
	private String address;
	private String addressCode;
	private String phone;
	private Date createDate;
	private String token;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public static Customer convert(CustomerDTO dto){
		Customer customer = new Customer();
		customer.setAddress(dto.getAddress());
		customer.setAddressCode(dto.getAddressCode());
		customer.setCreateDate(dto.getCreateDate());
		customer.setId(dto.getId());
		customer.setName(dto.getName());
		customer.setPhone(dto.getPhone());
		customer.setUserId(dto.getUserId());
		return customer;
	}
}
