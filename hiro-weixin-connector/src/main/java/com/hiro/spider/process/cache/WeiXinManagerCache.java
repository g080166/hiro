package com.hiro.spider.process.cache;

import org.springframework.stereotype.Repository;

import com.hiro.spider.core.redis.AbstractRedisCache;

@Repository
public class WeiXinManagerCache extends AbstractRedisCache{
	private static final String PREFIX = "weixin_manager"; 
	
	public void setManager(String value){
		set(PREFIX, value);
	}
	
	public String getManager(){
		return get(PREFIX);
	}
}
