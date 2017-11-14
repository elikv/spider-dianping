package com.dianping.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.model.DianPingInfo;
import com.dianping.util.UserAgentUtils;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

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
        OOSpider.create(Site.me().setSleepTime(1800).setRetryTimes(3)
                .setUserAgent(UserAgentUtils.radomUserAgent()),dianPingDaoPipeline, DianPingInfo.class)
                .addUrl("http://www.dianping.com/search/category/1/10/g101o3p1")
                .thread(5)
                .run();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application*.xml");
        final RestaurantCrawler restaurantCrawler = applicationContext.getBean(RestaurantCrawler.class);
        restaurantCrawler.crawl();
    }
}
