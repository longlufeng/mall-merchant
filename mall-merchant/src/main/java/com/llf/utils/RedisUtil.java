package com.llf.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings("unchecked")
@Service
public class RedisUtil {
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	/**
	 * 设置缓存对象
	 * @param key
	 * @param value
	 * @param timeout
	 * @param timeUnit
	 */
	public void setObject(final String key, Object value,long timeout, TimeUnit timeUnit) {
		
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
		
	}
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	public Object getObject(final String key) {
		
		return redisTemplate.opsForValue().get(key);
		
	}
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param timeOut
	 * @param timeUnit
	 */
	public void expired(final String key, long timeOut,TimeUnit timeUnit) {
		
		redisTemplate.opsForValue().getAndExpire(key, timeOut, timeUnit);
		
	}
	
	/**
	 * 设置缓存对象
	 * @param key
	 * @param value
	 * @param timeout
	 * @param timeUnit
	 */
	@SuppressWarnings("rawtypes")
	public void setMap(final String key, Map value,long timeout, TimeUnit timeUnit) {
		
		redisTemplate.opsForHash().putAll(key, value);
		redisTemplate.opsForHash().getOperations().expire(key,timeout,timeUnit);
		
	}
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getMap(final String key) {
		
		return redisTemplate.opsForHash().entries(key);
		
	}
	
	/**
	 * 设置缓存对象
	 * @param key
	 * @param value
	 * @param timeout
	 * @param timeUnit
	 */
	public void setMapKey(final String key, String hashKey,String value,long timeout, TimeUnit timeUnit) {
		
		redisTemplate.opsForHash().put(key, hashKey, value);
		
	}
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	public Object getMapKey(final String key,final String hashKey) {
		
		return redisTemplate.opsForHash().get(key, hashKey);
		
	}

}
