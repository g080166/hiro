package com.hiro.spider.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import redis.clients.jedis.Jedis;

public abstract class AbstractRedisCache {

	@Autowired
	@Qualifier("hiroCoreRedisExecutor")
	private RedisExecutor hiroCoreRedisExecutor;
	
	/**
	 * 普通set
	 * @param key
	 * @param value
	 */
	public void set(final String key,final String value){
		hiroCoreRedisExecutor.doInRedis(new JedisCallable<String>() {
			@Override
			public String call(Jedis instance) throws Exception {
				// TODO Auto-generated method stub
				return instance.set(key, value);
			}
		});
	}
	
	public String get(final String key){
		return hiroCoreRedisExecutor.doInRedis(new JedisCallable<String>() {
			@Override
			public String call(Jedis instance) throws Exception {
				// TODO Auto-generated method stub
				return instance.get(key);
			}
		});
	}
}
