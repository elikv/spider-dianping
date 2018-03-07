package com.dianping.main;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dianping.algorithm.AlgorithmUtils;
import com.dianping.config.RedisConfig;
import com.dianping.dao.DianPingDAO;
import com.dianping.dao.IShopIdRankTimeScoreDao;
import com.dianping.dao.RankShopDao;
import com.dianping.model.RankShopInfo;
import com.dianping.model.ShopIdRankTimeScoreEntity;
import com.dianping.schedule.RedisScheduler;
import com.dianping.util.DistinctUtils;
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
	@Autowired
	private DianPingDAO dianPingDao;
	@Autowired
	private IShopIdRankTimeScoreDao shopIdRankTimeScoreDao;
	@Autowired
	private DistinctUtils distinctUtils;
	
	RedisScheduler redisScheduler = new RedisScheduler(new JedisPool(new JedisPoolConfigExtend(),RedisConfig.HOST_ADDRESS,6379,5000,RedisConfig.PASSWORD));
	
	
	
	
	public void rankSpider() {
		
	}
	
	
	@Transactional(rollbackFor=Throwable.class)
	public void doGet() throws ParseException, InterruptedException {
		List<RankShopInfo> list = new ArrayList<RankShopInfo>();
		CrawlerHttpClient client = CrawlerHttpClientBuilder.create().build();
		List<List<NameValuePair>> needParams = URLUtils.getParams();
		int i=0;
		for (List<NameValuePair> params : needParams) {
			String data = client.get(URLUtils.baseUrl_rank,params,Charset.defaultCharset());
			List<RankShopInfo> parseData = parseData(data);
			if(parseData==null) {
				//等待5分钟
				Thread.sleep(1000*60*5);
				continue;
			}
			list.addAll(parseData);
			i= i+1;
			logger.info("已完成："+i+"/"+needParams.size());
			Random rand = new Random();
			//7-27s
			int second = 7000+rand.nextInt(20)*1000;
			logger.info("等待"+second+"s后继续");
			Thread.sleep(second);
		}
		rankShopDao.addList(list);
		//去掉多余无用的数据
		distinctUtils.distinctList();
		updateNewtonCooling();
	}
	
	public void updateNewtonCooling() throws ParseException{
		long currentTimeMillis = System.currentTimeMillis();
		List<ShopIdRankTimeScoreEntity> list = rankShopDao.findShopIdRankTimeScore();
		for (ShopIdRankTimeScoreEntity shopIdRankTimeScoreEntity : list) {
			double newtonCooling = AlgorithmUtils.NewtonCooling(shopIdRankTimeScoreEntity);
			shopIdRankTimeScoreEntity.setCoolingScore(newtonCooling);
			if(rankShopDao.findByShopId(shopIdRankTimeScoreEntity.getShopId())==null) {
				shopIdRankTimeScoreDao.add(shopIdRankTimeScoreEntity);
			}else {
				shopIdRankTimeScoreDao.update(shopIdRankTimeScoreEntity);
			}
		}
		long now = (System.currentTimeMillis()-currentTimeMillis)/1000;
		logger.info("牛顿冷却总耗时："+now+"s,共处理了"+list.size()+"家店");
	}
	
	public static void main(String[] args) throws ParseException, InterruptedException {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final HttpGet httpGet = applicationContext.getBean(HttpGet.class);
        httpGet.doGet();
        applicationContext.close();
	}
	
	public List<RankShopInfo> parseData(String data) throws ParseException{
		logger.info(data);
		if(StringUtils.containsIgnoreCase(data,"maxResult")) {
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
		}else {
			return null;
		}
		
		
	}

}
