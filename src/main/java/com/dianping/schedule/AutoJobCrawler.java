package com.dianping.schedule;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.jdbc.Jdbc;
import com.dianping.main.RestaurantCrawler;

@Component
public class AutoJobCrawler {
	
	@Scheduled(cron="0 0 3,6,13,18,21 * * ?")
	public void autoJobCrawler(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        DianPingDAO dianPingDAO = applicationContext.getBean(DianPingDAO.class);
        restaurantCrawler.crawl(dianPingDAO.findExist());
	}
	
	@Scheduled(cron="0 0 11,23 * * ?")
	public void removeDuplicate() throws IllegalAccessException, InvocationTargetException, SQLException{
		new Jdbc().removeDuplicate();
	}
}
