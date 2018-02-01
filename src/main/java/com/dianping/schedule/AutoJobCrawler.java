package com.dianping.schedule;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dianping.jdbc.Jdbc;
import com.dianping.main.HttpGet;
import com.dianping.main.RestaurantCrawler;
import com.virjar.dungproxy.client.ippool.PreHeater;

@Component
public class AutoJobCrawler {
	@Autowired
	private HttpGet httpGet;
	@Autowired
	private RestaurantCrawler restaurantCrawler;
	
	/**
	 * 每周1，3，5早上10点爬接口
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	@Scheduled(cron="0 0 10 ? * MON,WED,FRI")
	public void autoRankTrigger() throws ParseException, InterruptedException{
        httpGet.doGet();
	}
	
	
	/**
	 * 每天14点预热ip
	 */
	@Scheduled(cron="0 0 14 * * ?")
	public void autoPreHeater(){
		PreHeater.start();
	}
	
	/**
	 * 每天18点爬取信息
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	@Scheduled(cron="0 0 18 * * ?")
	public void autoJobCrawler() throws IllegalAccessException, InvocationTargetException, SQLException{
        new Jdbc().removeDuplicate();
        restaurantCrawler.crawl();
	}

	
}
