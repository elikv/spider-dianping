package com.dianping.controller;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dianping.dao.DianPingDAO;
import com.dianping.jdbc.Jdbc;
import com.dianping.main.RestaurantCrawler;
import com.dianping.model.User;
import com.dianping.service.UserService;

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
	
//	@RequestMapping("/")  
//    public ModelAndView getIndex(){    
//		ModelAndView mav = new ModelAndView("index"); 
//		User user = userService.selectUserById(1);
//	    mav.addObject("user", user); 
//        return mav;  
//    }  
}
