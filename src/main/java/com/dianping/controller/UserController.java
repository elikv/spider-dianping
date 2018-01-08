package com.dianping.controller;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianping.jdbc.Jdbc;
import com.dianping.main.HttpGet;
import com.dianping.main.RestaurantCrawler;
import com.dianping.main.StarCrawler;
import com.dianping.service.UserService;
import com.virjar.dungproxy.client.ippool.PreHeater;

/**
 * 功能概要：UserController
 * 
 * @author elikv
 * @since  2017年11月28日 
 */
@Controller
public class UserController {
	@Resource
	private UserService userService;
	@Autowired
	private Jdbc jdbc;
	
		@RequestMapping( "/" )
		public String showIndex(){
			return "index";
		}
		
		@RequestMapping("/trigger")
		public void trigger(){
			AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
	        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
	        restaurantCrawler.crawl();
	        applicationContext.close();
		}
		
		@RequestMapping("/removeDuplicate")
		public void removeDuplicate() throws IllegalAccessException, InvocationTargetException, SQLException{
			jdbc.removeDuplicate();
		}
		
		@RequestMapping("/preHeater")
		public void preHeater(){
			PreHeater.start();
		}
		
		@RequestMapping("/rankTrigger")
		public void rankTrigger() throws ParseException, InterruptedException{
			AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
	        final HttpGet httpGet = applicationContext.getBean(HttpGet.class);
	        httpGet.doGet();
	        applicationContext.close();
		}
		
		@RequestMapping("/addStar")
		public void addStar() throws ParseException, InterruptedException{
			AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
	        final StarCrawler starCrawler = applicationContext.getBean(StarCrawler.class);
	        starCrawler.crawl();
	        applicationContext.close();
		}
	
//	@RequestMapping("/")  
//    public ModelAndView getIndex(){    
//		ModelAndView mav = new ModelAndView("index"); 
//		User user = userService.selectUserById(1);
//	    mav.addObject("user", user); 
//        return mav;  
//    }  
}
