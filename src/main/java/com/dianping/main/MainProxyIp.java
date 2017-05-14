package com.dianping.main;


import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.model.ProxyList;
import com.dianping.proxy.CatchProxyIpRunnable;
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
    
    
    
	public void startFind(int threadNum){
		//定义一个多线程的runnable
		CatchProxyIpRunnable catchProxyIpRunnable = new CatchProxyIpRunnable();
		//定义一个model，有待测和成功的代理ip
		ProxyList proxyList = new ProxyList();
		
		//把数据库里的成可用ipVector传过来
		proxyList.setSuccessIPVector(proxyIpService.setSuccessIPVector() ); 
		proxyList.setIpVector(FindProxyWeb.find(proxyList));
		catchProxyIpRunnable.SetProxyList(proxyList,proxyIpService);
		catchProxyIpRunnable.SetThreadNum(threadNum);
		for(int i=0;i<threadNum;i++){
		new Thread(catchProxyIpRunnable).start();
		}
		
	}
	
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("classpath:application*.xml");
		 final MainProxyIp mainProxyIp = applicationContext.getBean(MainProxyIp.class);
		 mainProxyIp.startFind(5);
	
	}

}
