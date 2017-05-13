package com.lin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lin.domain.User;
import com.lin.service.UserService;

/**
 * 功能概要：UserController
 * 
 * @author linbingwen
 * @since  2015年9月28日 
 */
@Controller
public class UserController {
	@Resource
	private UserService userService;
	
		@RequestMapping( "/" )
		public String showIndex(){
			return "index";
		}
	
//	@RequestMapping("/")  
//    public ModelAndView getIndex(){    
//		ModelAndView mav = new ModelAndView("index"); 
//		User user = userService.selectUserById(1);
//	    mav.addObject("user", user); 
//        return mav;  
//    }  
}
