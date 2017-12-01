package com.dianping.schedule;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.dianping.dao.DianPingDAO;
import com.dianping.main.RestaurantCrawler;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class RedisSchedulerExtend extends RedisScheduler {
	
		
	private ArrayList<String> existUrls= new ArrayList<String>();
	
	
	public RedisSchedulerExtend(JedisPool pool,ArrayList<String> existUrls) {
		super(pool);
		setExistUrls(existUrls);
	}
	public ArrayList<String> getExistUrls() {
		return existUrls;
	}
	public void setExistUrls(ArrayList<String> existUrls) {
		this.existUrls = existUrls;
	}
	@Override
    public synchronized Request poll(Task task) {
        Jedis jedis = pool.getResource();
        try {
            String url = jedis.lpop(getQueueKey(task));
            if (url == null) {
                return null;
            }
            if(existUrls.indexOf(url)>=0){
            	return new Request(url);
            }
            String key = "item_" + task.getUUID();
            String field = DigestUtils.shaHex(url);
            byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
            if (bytes != null) {
                Request o = JSON.parseObject(new String(bytes), Request.class);
                return o;
            }
                Request request = new Request(url);
            return request;
        } finally {
            pool.returnResource(jedis);
        }
    }
	

}
