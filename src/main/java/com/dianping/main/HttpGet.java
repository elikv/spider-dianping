package com.dianping.main;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dianping.dao.RankShopDao;
import com.dianping.model.RankShopInfo;
import com.dianping.schedule.RedisScheduler;
import com.dianping.util.JedisPoolConfigExtend;
import com.dianping.util.URLUtils;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClient;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClientBuilder;

import redis.clients.jedis.JedisPool;
/**
 * 接口采集数据
 * @author 16315
 *
 */
@Component
public class HttpGet {
	
	private  static Logger logger = LoggerFactory.getLogger(HttpGet.class);
	
	@Autowired
	private RankShopDao rankShopDao;
	RedisScheduler redisScheduler = new RedisScheduler(new JedisPool(new JedisPoolConfigExtend(),"123.206.206.111",6379,5000,"W1314zan1g"));
	
	
	
	public void doGet() throws ParseException, InterruptedException {
		
		CrawlerHttpClient client = CrawlerHttpClientBuilder.create().build();
		List<List<NameValuePair>> needParams = URLUtils.getParams();
		for (List<NameValuePair> params : needParams) {
			String data = client.get(URLUtils.baseUrl,params,Charset.defaultCharset());
			List<RankShopInfo> parseData = parseData(data);
			if(!CollectionUtils.isEmpty(parseData)){
				rankShopDao.addList(parseData);
			}
			Random rand = new Random();
			//5-15s
			Thread.sleep(5000+rand.nextInt(10)*1000);
		}
		
	}
	
	public static void main(String[] args) throws ParseException, InterruptedException {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final HttpGet httpGet = applicationContext.getBean(HttpGet.class);
        httpGet.doGet();
        applicationContext.close();
	}
	
	public List<RankShopInfo> parseData(String data) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(new Date());
		Date rankTime = simpleDateFormat.parse(format);
		 JSONObject parseObject = JSON.parseObject(data);
		 
		 String rankType = parseObject.getString("rankType");
		 String categoryId = parseObject.getString("categoryId");
		List<RankShopInfo> list = JSON.parseArray(parseObject.getString("shopBeans"), RankShopInfo.class);
		if(!CollectionUtils.isEmpty(list)){
		for (int i = 0; i < list.size(); i++) {
			RankShopInfo rankShopInfo = list.get(i);
			rankShopInfo.setRankNo(i+1);
			rankShopInfo.setCategoryId(categoryId);
			rankShopInfo.setRankType(rankType);
			rankShopInfo.setRankTime(rankTime);
			rankShopInfo.setUrl("http://www.dianping.com/shop/"+rankShopInfo.getShopId());
			redisScheduler.push(rankShopInfo.getUrl());
		}
		String jsonData = JSONArray.toJSONString(list);
		redisScheduler.pushData(jsonData, format);
		}
		
		return list;
	}

}
