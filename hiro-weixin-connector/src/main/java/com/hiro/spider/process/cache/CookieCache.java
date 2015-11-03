package com.hiro.spider.process.cache;

import org.springframework.stereotype.Repository;

import com.hiro.spider.core.redis.AbstractRedisCache;

@Repository
public class CookieCache extends AbstractRedisCache{
	
	private static final String PREFIX = "WEIXIN_COOKIE::"; 
	
	public void setCookie(String key,String value){
		set(PREFIX+key, value);
	}
	
	public String getCookie(String key){
		return get(PREFIX+key);
	}
}
