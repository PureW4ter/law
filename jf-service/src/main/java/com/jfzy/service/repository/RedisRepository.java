package com.jfzy.service.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisRepository {

	@Autowired
	private StringRedisTemplate template;

	public void setWithTimeout(String key, String value, long time) {

		ValueOperations<String, String> ops = template.opsForValue();
		ops.set(key, value, time, TimeUnit.SECONDS);
	}

	public String get(String key) {
		ValueOperations<String, String> ops = template.opsForValue();
		return ops.get(key);
	}

	public void set(String key, String value) {
		ValueOperations<String, String> ops = template.opsForValue();
		ops.set(key, value);
	}

}
