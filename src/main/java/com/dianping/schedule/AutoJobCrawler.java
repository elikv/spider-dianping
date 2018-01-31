package com.dianping.schedule;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.jdbc.Jdbc;
import com.dianping.main.HttpGet;
import com.dianping.main.RestaurantCrawler;
import com.virjar.dungproxy.client.ippool.PreHeater;

@Component
public class AutoJobCrawler {
	
	/**
	 * 每周1，3，5早上10点爬接口
	 * @throws ParseException
	 * @throws InterruptedException
	 */
//	@Scheduled(cron="0 0 10 ? * MON,WED,FRI")
	public void autoRankTrigger() throws ParseException, InterruptedException{
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final HttpGet httpGet = applicationContext.getBean(HttpGet.class);
        httpGet.doGet();
        applicationContext.close();
	}
	
	
	/**
	 * 每天14点预热ip
	 */
//	@Scheduled(cron="0 0 14 * * ?")
	public void autoPreHeater(){
		PreHeater.start();
	}
	
	/**
	 * 每天18点爬取信息
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
//	@Scheduled(cron="0 0 18 * * ?")
	public void autoJobCrawler() throws IllegalAccessException, InvocationTargetException, SQLException{
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        new Jdbc().removeDuplicate();
        restaurantCrawler.crawl();
        applicationContext.close();
	}

	
}
