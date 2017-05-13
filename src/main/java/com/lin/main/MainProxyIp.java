package com.lin.main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.lin.domain.ProxyList;
import com.lin.proxy.CatchProxyIp;
import com.lin.proxy.FindProxyWeb;
import com.lin.service.ProxyIpService;


@Component
public class MainProxyIp {
//    @Qualifier("ProxyIpServiceImpl")
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
