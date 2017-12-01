package com.dianping.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.dao.ProxyIpDAO;
import com.dianping.downloader.WebMagicCustomOfflineProxyDownloader;
import com.dianping.model.DianPingInfo;
import com.dianping.model.ProxyIpInfo;
import com.dianping.schedule.RedisSchedulerExtend;
import com.dianping.service.DianPingServiceImpl;
import com.dianping.util.JedisPoolConfigExtend;
import com.dianping.util.UserAgentUtils;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.client.ippool.strategy.impl.WhiteListProxyStrategy;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.RedisScheduler;
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
	
	private static final String HOST_ADDRESS="123.206.206.111";
	private static final String PASSWORD="W1314zan1g";
	

    public void crawl(ArrayList<String> existUrls) {
        Site site = Site.me()// .setHttpProxy(new HttpHost("127.0.0.1",8888))
                .setRetryTimes(3) // 就我的经验,这个重试一般用处不大,他是httpclient内部重试
                .setTimeOut(25000)// 在使用代理的情况下,这个需要设置,可以考虑调大线程数目
                .setSleepTime(200)// 使用代理了之后,代理会通过切换IP来防止反扒。同时,使用代理本身qps降低了,所以这个可以小一些
                .setCycleRetryTimes(3)// 这个重试会换IP重试,是setRetryTimes的上一层的重试,不要怕三次重试解决一切问题。。
                .setUseGzip(true)
                .setUserAgent(UserAgentUtils.radomUserAgent())
                .setCharset("UTF-8");
        WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
        whiteListProxyStrategy.addAllHost("www.dianping.com");
        

        // Step2 创建并定制代理规则
        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(false);

        // Step3 使用代理规则初始化默认IP池
        IpPoolHolder.init(dungProxyContext);
        
        RedisSchedulerExtend redisScheduler = new RedisSchedulerExtend(new JedisPool(new JedisPoolConfigExtend(),HOST_ADDRESS,6379,3500,PASSWORD),existUrls);
        OOSpider.create(site,dianPingDaoPipeline, DianPingInfo.class)
        		.setDownloader(new WebMagicCustomOfflineProxyDownloader ())
        		.setScheduler(redisScheduler).thread(50)
                .addUrl("http://www.dianping.com/search/category/1/10/g101o3p1")
                .run();
    }

    public static void main(String[] args) {
    	AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        DianPingDAO dianPingDAO = applicationContext.getBean(DianPingDAO.class);
        restaurantCrawler.crawl(dianPingDAO.findExist());
    }
}
