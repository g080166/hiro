package com.hiro.spider.process.cache;

import org.springframework.stereotype.Repository;

import com.hiro.spider.core.redis.AbstractRedisCache;

@Repository
public class TestCache extends AbstractRedisCache{
	
	public void setTest(String key,String value){
		set(key, value);
	}
	
	public String getTest(String key){
		return get(key);
	}
}
