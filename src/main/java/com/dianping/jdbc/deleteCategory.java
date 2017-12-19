package com.dianping.jdbc;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.config.RedisConfig;
import com.dianping.dao.DianPingDAO;
import com.dianping.util.JedisPoolConfigExtend;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 删除redis中的分类种子
 * @author 39346
 *
 */
@Component
public class deleteCategory {
	final String HOST_ADDRESS=RedisConfig.HOST_ADDRESS;
	final String PASSWORD=RedisConfig.PASSWORD;
	@Autowired
	private DianPingDAO dianpingDao;
	public static void main(String[] args) {
		new deleteCategory().deleteCategory();
	}
	
	public void deleteCategory() {
		JedisPool jedisPool = new JedisPool(new JedisPoolConfigExtend(),HOST_ADDRESS,6379,5000,PASSWORD);
		Jedis resource = jedisPool.getResource();
		Set<String> smembers = resource.smembers("set_www.dianping.com");
		int i = 0;
		for (String string : smembers) {
			if(StringUtils.containsIgnoreCase(string, "http://www.dianping.com/search/category")) {
				System.out.println("正在删除："+string);
				resource.srem("set_www.dianping.com", string);
				i = i+1;
			}
		}
		resource.srem("set_www.dianping.com", "http://www.dianping.com/shanghai/food");
		System.out.println("共删除"+i+"个分类地址");
		
	}
	
	public void addDuplicate() {
		JedisPool jedisPool = new JedisPool(new JedisPoolConfigExtend(),HOST_ADDRESS,6379,5000,PASSWORD);
		Jedis resource = jedisPool.getResource();
		
		ArrayList<String> findExist = dianpingDao.findExist();
		for (String string : findExist) {
			System.out.println("正在添加："+string);
			resource.sadd("set_www.dianping.com", string);
		}
	}
	
}

