package com.dianping.main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.model.ProxyList;
import com.dianping.proxy.CatchProxyIp;
import com.dianping.proxy.FindProxyWeb;
import com.dianping.service.ProxyIpService;


@Component
public class MainProxyIp {
    @Autowired
    private ProxyIpService proxyIpService;
    
    /**
     * 新思路
     * 跑的时候发现可用set里有一样的直接跳过
     */
    
    
    
	public void startFind(){
		CatchProxyIp catchProxyIp = new CatchProxyIp();
		ProxyList proxyList = new ProxyList();
		proxyList.setIpVector(FindProxyWeb.find(proxyList));
		catchProxyIp.SetProxyList(proxyList,proxyIpService);
		new Thread(catchProxyIp).start();
		 new Thread(catchProxyIp).start();
		 new Thread(catchProxyIp).start();
		 new Thread(catchProxyIp).start();
		
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("classpath:application*.xml");
		 final MainProxyIp mainProxyIp = applicationContext.getBean(MainProxyIp.class);
		 mainProxyIp.startFind();
	
	}

}
