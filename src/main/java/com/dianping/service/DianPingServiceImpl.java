package com.dianping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.dianping.dao.DianPingDAO;
import com.dianping.main.RestaurantCrawler;
import com.dianping.model.DianPingInfo;

@Service
public class DianPingServiceImpl  {
@Autowired
private DianPingDAO dianPingDAO;
	public void add(DianPingInfo dianPingInfo) {
		dianPingDAO.add(dianPingInfo);
	}

	public ArrayList<String> findExist() {
		ArrayList<String> findExist = dianPingDAO.findExist();
		return findExist;
	}

}
