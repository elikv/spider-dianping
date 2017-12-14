package com.dianping.util;

import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigExtend extends JedisPoolConfig {
	public JedisPoolConfigExtend(){
		//最大连接数 无限制
		setMaxTotal(70);
		

	}
	
}
