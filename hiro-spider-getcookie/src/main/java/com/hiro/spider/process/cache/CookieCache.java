package com.hiro.spider.process.cache;

import org.springframework.stereotype.Repository;

import com.hiro.spider.core.redis.AbstractRedisCache;

@Repository
public class CookieCache extends AbstractRedisCache{
	
	private static final String PREFIX = "COOKIE::"; 

	
	public void setTest(String key,String value){
		set(PREFIX+key, value);
	}
	
	public String getTest(String key){
		return get(PREFIX+key);
	}
}
