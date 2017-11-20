package com.dianping.schedule;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dianping.main.RestaurantCrawler;

@Component
public class AutoJobCrawler {
	@Scheduled(cron="0 0 0,3,6,13,18,21 * * ?")
	public void autoJobCrawler(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        restaurantCrawler.crawl();
	}
}
