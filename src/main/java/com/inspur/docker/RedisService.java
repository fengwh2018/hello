package com.inspur.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	public void put(Object key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void delete(Object o) {
		redisTemplate.delete(o);
	}
}
