package com.lin.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.lin.dao.UserDao;
import com.lin.domain.User;

/**
 * 功能概要：UserService实现类
 * 
 * @author linbingwen
 * @since  2015年9月28日 
 */
@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;

	public User selectUserById(Integer userId) {
		return userDao.selectUserById(userId);
		
	}

}
