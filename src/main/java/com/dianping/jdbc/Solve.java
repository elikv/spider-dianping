package com.dianping.jdbc;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.DianPingInfo;
@Component
public class Solve {
	@Autowired
	private DianPingDAO dianpingDao;
	
	public void solve(){
		ArrayList<DianPingInfo> findAll = dianpingDao.findAll();
		for (DianPingInfo dianPingInfo : findAll) {
			if(StringUtils.isEmpty(dianPingInfo.getShopId())){
				dianPingInfo.setshopId( dianPingInfo.getUrl().split("/")[4]);
			dianpingDao.update(dianPingInfo);
			}
		}
	}
	
	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
	     Solve restaurantCrawler = applicationContext.getBean(Solve.class);
	     restaurantCrawler.solve();
	     applicationContext.close();
	}
}
