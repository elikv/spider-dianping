package com.dianping.proxy;



import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dianping.model.ProxyIpInfo;
import com.dianping.model.ProxyList;
import com.dianping.service.ProxyIpService;
import com.dianping.util.IfTrueProxy;


/**
 * 多线程的run()判断是代理ip后加入数据库
 * @author elikv
 *
 */

public class CatchProxyIpRunnable implements Runnable {
	@Autowired
	private ProxyIpService proxyIpService;
	//ProxyList包含一个待测Vector和一个可用Set，定义在run()外， 线程可共享数据
	//设置线程数
	int threadNum ;
	ProxyList proxyList = new ProxyList();
	//spring在多线程的Runnable中防止注入，因此用构建函数传入服务
	public void SetProxyList(ProxyList proxyList,ProxyIpService proxyIpService){
		this.proxyList = proxyList;
		this.proxyIpService = proxyIpService;
	}
	public void SetThreadNum(int threadNum){
		this.threadNum=threadNum;
	}
	//线程标识，第一个线程0号，第二个线程1号
	AtomicInteger signal = new AtomicInteger(0);
	

	public void run() {
		//获得线程号后自增
		int int_signal = signal.getAndIncrement();
//		proxyList.setIpVector(new Vector<String>());
		//Set<String> successIPSet = new HashSet<>();// 可用代理ip  放在run()里面不共享数据

		

		try {

			
			int size=proxyList.getIpVector().size();
			System.out.println("线程"+int_signal+"待测数目： " + size);
			//threadNum个线程一起跑，故分流线程0，1，2，3,...threadNum-1
			int condition =int_signal%threadNum;
			for(int j=0;j<threadNum;j=j+threadNum){
			if(condition==j){
				for(int i =condition;i<size;i=i+threadNum){
					String proxyIp = proxyList.getIpVector().get(i);
					Vector<String> successVector =proxyList.getSuccessIPVector();
					boolean repeat = false;
					for(String every : successVector){
						if(every==proxyIp){
							System.out.println("数据库中已存在"+every);
							repeat =true;
							break;
						}
					}
					if(repeat ){
						continue;
					}
					String ip = proxyIp.split(":")[0];
					int port = Integer.valueOf(proxyIp.split(":")[1]);
					
						if (IfTrueProxy.createIPAddress(ip, port)) {
							proxyList.getSuccessIPVector().add(proxyList.getIpVector().get(i));
							ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
							proxyIpInfo.setIp(ip);
							proxyIpInfo.setPort(port);
							synchronized(proxyIpInfo){
								proxyIpService.add(proxyIpInfo);
							}
						System.out.println("*************************");
						System.out.println("可用数目： " + proxyList.getSuccessIPVector().size());
						for (String proxyIPq : proxyList.getSuccessIPVector()) {
							System.out.println(proxyIPq);
						}
				}
			}
			
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}





















/**

int size=proxyList.getIpVector().size();
System.out.println("线程"+int_signal+"待测数目： " + size);
//4个线程一起跑，故分流线程0，1，2，3
int condition =int_signal%4;
if(condition==0){
	for(int i =condition;i<size;i=i+4){
		String proxyIp = proxyList.getIpVector().get(i);
		Vector<String> successVector =proxyList.getSuccessIPVector();
		boolean repeat = false;
		for(String every : successVector){
			if(every==proxyIp){
				System.out.println("数据库中已存在"+every);
				repeat =true;
			}
		}
		if(repeat ){
			continue;
		}
		String ip = proxyIp.split(":")[0];
		int port = Integer.valueOf(proxyIp.split(":")[1]);
		synchronized(proxyList){
			if (IfTrueProxy.createIPAddress(ip, port)) {
				proxyList.getSuccessIPVector().add(proxyList.getIpVector().get(i));
				ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
				proxyIpInfo.setIp(ip);
				proxyIpInfo.setPort(port);
				synchronized(proxyIpInfo){
					proxyIpService.add(proxyIpInfo);
				}
			System.out.println("*************************");
			System.out.println("可用数目： " + proxyList.getSuccessIPVector().size());
			for (String proxyIPq : proxyList.getSuccessIPVector()) {
				System.out.println(proxyIPq);
			}
	}
}
}
}
if(condition==1){
	for(int i =condition;i<size;i=i+4){
		String proxyIp = proxyList.getIpVector().get(i);
		Vector<String> successVector =proxyList.getSuccessIPVector();
		boolean repeat = false;
		for(String every : successVector){
			if(every==proxyIp){
				System.out.println("数据库中已存在"+every);
				repeat =true;
			}
		}
		if(repeat ){
			continue;
		}
		
		
		
		String ip = proxyList.getIpVector().get(i).split(":")[0];
		int port = Integer.valueOf(proxyIp.split(":")[1]);
//		synchronized(proxyIpInfo){
			if (IfTrueProxy.createIPAddress(ip, port)) {
				proxyList.getSuccessIPVector().add(proxyIp);
				ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
				proxyIpInfo.setIp(ip);
				proxyIpInfo.setPort(port);
				synchronized(proxyIpInfo){
				proxyIpService.add(proxyIpInfo);
				}

			System.out.println("*************************");
			System.out.println("可用数目： " + proxyList.getSuccessIPVector().size());
			for (String proxyIPq : proxyList.getSuccessIPVector()) {
				System.out.println(proxyIPq);
			}
	}
}
	
}
if(condition==2){
	for(int i =condition;i<size;i=i+4){
		
			String proxyIp = proxyList.getIpVector().get(i);
			Vector<String> successVector =proxyList.getSuccessIPVector();
			boolean repeat = false;
			for(String every : successVector){
				if(every==proxyIp){
					System.out.println("数据库中已存在"+every);
					repeat =true;
				}
			}
			if(repeat ){
				continue;
			}
		
		
		String ip = proxyIp.split(":")[0];
		int port = Integer.valueOf(proxyIp.split(":")[1]);
//		synchronized(proxyList){
			if (IfTrueProxy.createIPAddress(ip, port)) {
				proxyList.getSuccessIPVector().add(proxyList.getIpVector().get(i));
				ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
				proxyIpInfo.setIp(ip);
				proxyIpInfo.setPort(port);
				synchronized(proxyIpInfo){
				proxyIpService.add(proxyIpInfo);
				}
//			}
			System.out.println("*************************");
			System.out.println("可用数目： " + proxyList.getSuccessIPVector().size());
			for (String proxyIPq : proxyList.getSuccessIPVector()) {
				System.out.println(proxyIPq);
			}
	}
}
}
	

if(condition==3){
	for(int i =condition;i<size;i=i+4){
		
		String proxyIp = proxyList.getIpVector().get(i);
		Vector<String> successVector =proxyList.getSuccessIPVector();
		boolean repeat = false;
		for(String every : successVector){
			if(every==proxyIp){
				System.out.println("数据库中已存在"+every);
				repeat =true;
			}
		}
		if(repeat ){
			continue;
		}
		
		
		
		String ip = proxyIp.split(":")[0];
		int port = Integer.valueOf(proxyIp.split(":")[1]);
//		synchronized(proxyList){
			if (IfTrueProxy.createIPAddress(ip, port)) {
				proxyList.getSuccessIPVector().add(proxyList.getIpVector().get(i));
				ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
				proxyIpInfo.setIp(ip);
				proxyIpInfo.setPort(port);
				synchronized(proxyIpInfo){
				proxyIpService.add(proxyIpInfo);
				}
//			}
			System.out.println("*************************");
			System.out.println("可用数目： " + proxyList.getSuccessIPVector().size());
			for (String proxyIPq : proxyList.getSuccessIPVector()) {
				System.out.println(proxyIPq);
			}
	}
}
}
	


for (String proxyIP : proxyList.getIpVector()) {
	String ip = proxyIP.split(":")[0];
	int port = Integer.valueOf(proxyIP.split(":")[1]);
	 代理服务器
	 InetSocketAddress(ip,port)
	InetSocketAddress proxyAddr = new InetSocketAddress(ip, port);
	Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
	HttpURLConnection conn = (HttpURLConnection) tmpURL.openConnection(proxy);
	conn.setRequestProperty("User-Agent", UserAgentUtils.radomUserAgent());
	conn.setReadTimeout(1000*10);
	conn.setConnectTimeout(1000*10);
	conn.setRequestMethod("GET");
	 if(conn.getResponseCode() == 200){
	 successIPSet.add(proxyIP);
	 }
	synchronized(proxyList){
	if (IfTrueProxy.createIPAddress(ip, port)) {
		proxyList.getSuccessIPSet().add(proxyIP);
	}
	

	System.out.println("*************************");
	System.out.println("可用数目： " + proxyList.getSuccessIPSet().size());
	for (String proxyIPq : proxyList.getSuccessIPSet()) {
		System.out.println(proxyIPq);
	}
	Thread.sleep(500);
}
*/




