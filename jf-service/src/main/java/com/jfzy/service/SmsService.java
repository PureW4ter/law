package com.jfzy.service;

public interface SmsService {

	void sendRegisterCode(String phoneNum, String message);

	void sendLawyerNotify(String lawyerPhone, String productType, String price, String time, String content);
}
