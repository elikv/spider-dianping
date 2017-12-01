package com.dianping.util;

import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigExtend extends JedisPoolConfig {
	public JedisPoolConfigExtend(){
		//最大连接数 无限制
		setMaxTotal(60);
		
		 //对拿到的connection进行validateObject校验
        setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        setTestOnReturn(true);

	}
	
}
