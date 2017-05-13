package com.dianping.service;

import org.springframework.stereotype.Service;

import com.dianping.model.User;

/**
 * 功能概要：UserService接口类
 * 
 * @author linbingwen
 * @since  2015年9月28日 
 */
public interface UserService {
	User selectUserById(Integer userId);

}
