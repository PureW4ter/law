package com.jfzy.web.batch;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jfzy.service.AccessTokenService;

@EnableScheduling
@Service
public class AccessTokenRefreshJob {

	@Value("${job.accesstoken.enabled}")
	private boolean enabled = false;

	private static Logger logger = LoggerFactory.getLogger(AccessTokenRefreshJob.class);

	@Autowired
	private AccessTokenService service;

	@PostConstruct
	public void init() {
		if (enabled) {
			refreshAccessToken();
		}
	}

	@Scheduled(fixedRate = 5000)
	public void refreshAccessToken() {
		if (enabled) {
			service.refreshAccessToken();
		}
	}

}
