package com.destinesia.service;

public interface ISmsService {
	public void regist(String phone);
	public void login(String phone);
	public void reset(String phone);
	public void valideCode(String phone, String passcode);
}
