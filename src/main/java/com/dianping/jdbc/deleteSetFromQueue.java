package com.dianping.jdbc;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.dianping.config.RedisConfig;
import com.dianping.util.JedisPoolConfigExtend;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class deleteSetFromQueue {
	
	public static void main(String[] args) {
		final String HOST_ADDRESS=RedisConfig.HOST_ADDRESS;
		final String PASSWORD=RedisConfig.PASSWORD;
		JedisPool jedisPool = new JedisPool(new JedisPoolConfigExtend(),HOST_ADDRESS,6379,5000,PASSWORD);
		Jedis resource = jedisPool.getResource();
		resource.srem("set_www.dianping.com", "http://www.dianping.com/shanghai/food");
		while(true) {
			String lpop = resource.lpop("queue_www.dianping.com");
			System.out.println("正在删除："+lpop);
			resource.srem("set_www.dianping.com", lpop);
		}
		
	}

}
