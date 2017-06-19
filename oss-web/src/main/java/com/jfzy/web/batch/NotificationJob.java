package com.jfzy.web.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jfzy.service.NotificationService;

@EnableScheduling
@Service
public class NotificationJob {

	@Value("${job.notification.enabled}")
	private boolean enabled = false;

	@Autowired
	private NotificationService notifyService;

	@Scheduled(fixedRate = 5000)
	public void run() {
		if (enabled) {
			notifyService.notifyForAssignment();
			notifyService.notifyForConfirm();
		}
	}
}
