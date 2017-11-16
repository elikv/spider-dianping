package com.dianping.main;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.dao.ProxyIpDAO;
import com.dianping.downloader.WebMagicCustomOfflineProxyDownloader;
import com.dianping.model.DianPingInfo;
import com.dianping.model.ProxyIpInfo;
import com.dianping.util.UserAgentUtils;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.client.ippool.strategy.impl.WhiteListProxyStrategy;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

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
	@Autowired
	private ProxyIpDAO proxyIpDao;
	
	private static final String HOST_ADDRESS="";
	private static final String PASSWORD="";
	

    public void crawl() {
        Site site = Site.me()// .setHttpProxy(new HttpHost("127.0.0.1",8888))
                .setRetryTimes(3) // 就我的经验,这个重试一般用处不大,他是httpclient内部重试
                .setTimeOut(30000)// 在使用代理的情况下,这个需要设置,可以考虑调大线程数目
                .setSleepTime(500)// 使用代理了之后,代理会通过切换IP来防止反扒。同时,使用代理本身qps降低了,所以这个可以小一些
                .setCycleRetryTimes(3)// 这个重试会换IP重试,是setRetryTimes的上一层的重试,不要怕三次重试解决一切问题。。
                .setUseGzip(true)
                .setUserAgent(UserAgentUtils.radomUserAgent());
        WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
        whiteListProxyStrategy.addAllHost("www.dianping.com");
        

        // Step2 创建并定制代理规则
        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(false);

        // Step3 使用代理规则初始化默认IP池
        IpPoolHolder.init(dungProxyContext);
        
        RedisScheduler redisScheduler = new RedisScheduler(new JedisPool(new GenericObjectPoolConfig(),HOST_ADDRESS,6379,2000,PASSWORD));
        OOSpider.create(site,dianPingDaoPipeline, DianPingInfo.class).setScheduler(redisScheduler)
        		.setDownloader(new WebMagicCustomOfflineProxyDownloader ()).thread(20)
                .addUrl("http://www.dianping.com/search/category/1/10/g101o3p1")
                .run();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        restaurantCrawler.crawl();
    }
}
