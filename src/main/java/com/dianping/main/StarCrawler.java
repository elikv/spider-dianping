package com.dianping.main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.downloader.WebMagicCustomOfflineProxyDownloader;
import com.dianping.jdbc.Jdbc;
import com.dianping.model.ShopStarEntity;
import com.dianping.schedule.StarRedisScheduler;
import com.dianping.util.DianPingOfflinerImpl;
import com.dianping.util.JedisPoolConfigExtend;
import com.dianping.util.UserAgentUtils;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.client.ippool.strategy.impl.WhiteListProxyStrategy;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author elikv
 *         Date: 17-6-23
 *         Time: 下午4:19
 */
@Component
public class StarCrawler {

    @Qualifier("ShopStarDaoPipeline")
    @Autowired
    private PageModelPipeline shopStarDaoPipeline;
    
	
	

    public void crawl() {
        Site site = Site.me().addHeader("Cache-Control", "no-cache")
        		.addHeader("Host", "www.dianping.com")
        		.addHeader("Connection", "keep-alive")
        		.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
        		.addHeader("Referrer", "www.dianping.com")
        		.addHeader("Accept-Language", "zh-CN,zh;q=0.9")
//        		.addHeader("Cookie", "s_ViewType=10")
        		// .setHttpProxy(new HttpHost("127.0.0.1",8888))
//        		.setDisableCookieManagement(true)
        		.addCookie("www.dianping.com", "_hc.v", "567d6b73-87bd-0529-d84f-ce567653edc8.1513747703")
        		.addCookie("www.dianping.com", "cy", "1")
        		.addCookie("www.dianping.com", "cye", "shanghai")
        		.addCookie("www.dianping.com", "_lx_utm", "utm_source%3DBaidu%26utm_medium%3Dorganic")
        		.addCookie("www.dianping.com", "_lxsdk", "1607264e5f624-0c8286770f8631-3b3e5906-1fa400-1607264e5f7c8")
        		.addCookie("www.dianping.com", "_lxsdk_cuid", "1607264e5f624-0c8286770f8631-3b3e5906-1fa400-1607264e5f7c8")
        		.addCookie("www.dianping.com", "_lxsdk_s", "160d3b2ed33-59f-f5d-17c%7C%7C19")
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
        // Step2 创建并定制代理规则
        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(true);
		//增加预热
//        dungProxyContext.getPreHeater().start();
        // Step3 使用代理规则初始化默认IP池
        dungProxyContext.setDefaultOffliner(DianPingOfflinerImpl.class);
        IpPoolHolder.init(dungProxyContext);
        StarRedisScheduler redisScheduler = new StarRedisScheduler(new JedisPool(new JedisPoolConfigExtend(),"123.206.206.111",6379,5000,"W1314zan1g"));
        OOSpider create = OOSpider.create(site,shopStarDaoPipeline, ShopStarEntity.class);
//        create.setExitWhenComplete(false);
        create.setScheduler(redisScheduler)
        		.setDownloader(new WebMagicCustomOfflineProxyDownloader());
        //http://www.dianping.com/shop/92974819/review_all
        		create.thread(200).addUrl("http://www.dianping.com/shop/93646843/review_all")
                .run();
        
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, SQLException {
    	new Jdbc().removeDuplicate();
    	AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final StarCrawler restaurantCrawler = applicationContext.getBean(StarCrawler.class);
        restaurantCrawler.crawl();
//        applicationContext.close();
    }
}
