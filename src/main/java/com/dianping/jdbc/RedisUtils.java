package com.dianping.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.config.RedisConfig;
import com.dianping.dao.DianPingDAO;
import com.dianping.model.ShopStarEntityExtend;
import com.dianping.util.JedisPoolConfigExtend;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 删除redis中的分类种子
 * @author 39346
 *
 */
@Component
public class RedisUtils {
	final String HOST_ADDRESS=RedisConfig.HOST_ADDRESS;
	final String PASSWORD=RedisConfig.PASSWORD;
	
	
	
	@Autowired
	private DianPingDAO dianpingDao;
	public static void main(String[] args) {
		new RedisUtils().deleteCategory();
	}
	
	public Jedis connect(){
		JedisPool  jedisPool = new JedisPool(new JedisPoolConfigExtend(),HOST_ADDRESS,6379,5000,PASSWORD);
		return   jedisPool.getResource();
	}
	public void deleteCategory() {
		Jedis resource = connect();
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
	//TODO too much complex
	public void checkAndAdd(){
		List<String> shopIds = dianpingDao.findShopIdByStar();
		Jedis resource = connect();
		for (String shopId : shopIds) {
			int maxPage = dianpingDao.findMaxPage(shopId);
			for(int i=2;i<=maxPage;i++){
				String url = "http://www.dianping.com/shop/"+shopId+"/review_all/p"+i;
				ShopStarEntityExtend entity = dianpingDao.findStarChildByUrl(url);
				if(null == entity){
					resource.rpush("star_queue_www.dianping.com",url);
				}
			}
			
		}
	}
	
	public void addDuplicate() {
		Jedis resource = connect();
		ArrayList<String> findExist = dianpingDao.findExist();
		for (String string : findExist) {
			System.out.println("正在添加："+string);
			resource.sadd("set_www.dianping.com", string);
		}
	}
	
}

