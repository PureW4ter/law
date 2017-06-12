package com.jfzy.service.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisRepository<V> {

	@Autowired
	private RedisTemplate<String, V> template;

	public void setWithTimeout(String key, V value, long time) {

		ValueOperations<String, V> ops = template.opsForValue();
		ops.set(key, value, time, TimeUnit.SECONDS);
	}

	public V get(String key) {
		ValueOperations<String, V> ops = template.opsForValue();
		return ops.get(key);
	}

	public void set(String key, V value) {
		ValueOperations<String, V> ops = template.opsForValue();
		ops.set(key, value);
	}

}
