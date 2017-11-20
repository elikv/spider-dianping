package com.dianping.schedule;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dianping.main.MainProxyIp;

@Component
public class FindProxyIpSchedule {
	
//	@Scheduled(cron ="0 40/30 * * * ?")
	public void findProxyIpSchedule(){
	ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext("classpath:application*.xml");
	 final MainProxyIp mainProxyIp = applicationContext.getBean(MainProxyIp.class);
	 mainProxyIp.startFind(50);
	}

}
