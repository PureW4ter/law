package com.jfzy.service;

public interface SmsService {

	void sendRegisterCode(String phoneNum, String message);

}
