package com.dianping.main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.config.RedisConfig;
import com.dianping.dao.DianPingDAO;
import com.dianping.dao.ProxyIpDAO;
import com.dianping.downloader.WebMagicCustomOfflineProxyDownloader;
import com.dianping.jdbc.Jdbc;
import com.dianping.listener.DianPingListener;
import com.dianping.model.DianPingInfo;
import com.dianping.model.ProxyIpInfo;
import com.dianping.schedule.RedisScheduler;
import com.dianping.service.DianPingServiceImpl;
import com.dianping.util.DianPingOffliner;
import com.dianping.util.DianPingOfflinerImpl;
import com.dianping.util.JedisPoolConfigExtend;
import com.dianping.util.UserAgentUtils;
import com.virjar.dungproxy.client.httpclient.cookie.CookieDisableCookieStore;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.client.ippool.strategy.Offline;
import com.virjar.dungproxy.client.ippool.strategy.impl.WhiteListProxyStrategy;
import com.virjar.dungproxy.webmagic7.DungProxyDownloader;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import com.dianping.dao.DianPingDAO;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:19
 */
@Component
public class RestaurantCrawler {

    @Qualifier("DianPingDaoPipeline")
    @Autowired
    private PageModelPipeline dianPingDaoPipeline;
    
	
	

    public void crawl() {
        Site site = Site.me().addHeader("Cache-Control", "no-cache")
        		.addHeader("Host", "www.dianping.com")
        		.addHeader("Connection", "keep-alive")
        		.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
        		.addHeader("Referrer", "www.dianping.com")
        		.addHeader("Accept-Language", "zh-CN,zh;q=0.9")
        		.addHeader("Cookie", "s_ViewType=10")
        		// .setHttpProxy(new HttpHost("127.0.0.1",8888))
//        		.setDisableCookieManagement(true)
        		.addCookie("www.dianping.com", "s_ViewType", "10")
        		//        		.setDisableCookieManagement(true)
                .setRetryTimes(99) // 就我的经验,这个重试一般用处不大,他是httpclient内部重试
                .setTimeOut(35000)// 在使用代理的情况下,这个需要设置,可以考虑调大线程数目
                .setSleepTime(2000)// 使用代理了之后,代理会通过切换IP来防止反扒。同时,使用代理本身qps降低了,所以这个可以小一些
                .setCycleRetryTimes(99)// 这个重试会换IP重试,是setRetryTimes的上一层的重试,不要怕三次重试解决一切问题。。
                .setUseGzip(true)
                .setUserAgent(UserAgentUtils.radomUserAgent())
                .setCharset("UTF-8");
        		
        		
        		
        WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
        whiteListProxyStrategy.addAllHost("www.dianping.com");
        String[] gUrl= {"g101","g113","g117","g132","g111","g112","g116","g114","g103","g508","g115","g102","g109","g106","g104","g248","g3243","g251","g26481","g203","g107","g105","g215","g219","g247","g1783","g118","g110","g34014","g34032","g34015","g198","g25474","g199","g200","g201","g202"};
        String[] rUrl= {"r801","r802","r804","r865","r860","r803","r835","r812","r842","r846","r849","r806","r808","r811","r839","r854","r1","r2","r3","r4","r5","r6","r7","r8","r9","r10","r5937","r12","r5938","r5939","r8846","r8847","r3580","r1325","r1326","r1327","r1328","r1329","r1330","r3110","r1331","r1332","r6338","r6339","r25986","r8135","r26247"};
        String baseUrl="http://www.dianping.com/search/category/1/10/";
        
        // Step2 创建并定制代理规则
        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(true);
		//增加预热
//        dungProxyContext.getPreHeater().start();
        // Step3 使用代理规则初始化默认IP池
        dungProxyContext.setDefaultOffliner(DianPingOfflinerImpl.class);
        IpPoolHolder.init(dungProxyContext);
        RedisScheduler redisScheduler = new RedisScheduler(new JedisPool(new JedisPoolConfigExtend(),RedisConfig.HOST_ADDRESS,6379,5000,RedisConfig.PASSWORD));
        OOSpider create = OOSpider.create(site,dianPingDaoPipeline, DianPingInfo.class);
//        create.setExitWhenComplete(false);
        create.setScheduler(redisScheduler)
        		.setDownloader(new WebMagicCustomOfflineProxyDownloader());
        ArrayList<String> list = new ArrayList<String>();
        //http://www.dianping.com/shoplist/search/1_10_0_popscore  热门
        //http://www.dianping.com/shoplist/search/1_10_0_score 评分
      //http://www.dianping.com/shoplist/search/1_10_0_score1 口味
      //http://www.dianping.com/shoplist/search/1_10_0_score2 环境
      //http://www.dianping.com/shoplist/search/1_10_0_score3 服务
        //
        		for(int i=0;i<gUrl.length;i++) {
        			list.add(baseUrl+gUrl[i]+"o3");
        		}
        		for(int i=0;i<rUrl.length;i++) {
        			list.add(baseUrl+rUrl[i]+"o3");
        		}
        		for(int i=0;i<gUrl.length;i++) {
        			for(int j=0;j<rUrl.length;j++) {
            			list.add(baseUrl+gUrl[i]+rUrl[i]+"o3");
            		}
        		}
        		String[] array = list.toArray(new String[0]);
        		create.addUrl(array);
        		create.thread(120)
                .run();
        
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, SQLException {
    	new Jdbc().removeDuplicate();
    	AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        restaurantCrawler.crawl();
//        applicationContext.close();
    }
}
