package com.dianping.util;

import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigExtend extends JedisPoolConfig {
	public JedisPoolConfigExtend(){
		//最大连接数 无限制
		setMaxTotal(0);
		//最大空闲连接数
		setMaxIdle(50);
		//最小空闲连接数
		setMinIdle(10);
		 //对拿到的connection进行validateObject校验
        setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        setTestOnReturn(true);

	}
	
}
