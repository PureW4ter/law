package com.jfzy.service;

public interface NotificationService {

	void notifyForAssignment();

	void notifyForConfirm();

	void completeNotify(int orderId);

}
