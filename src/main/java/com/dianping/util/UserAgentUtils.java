package com.dianping.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
public class UserAgentUtils {
    
    /**
     * 从预定义的User-Agent列表中随机抽取一个返回
     * @return
     */
    public static String radomUserAgent(){
        List<String> list = new ArrayList<>();
        list.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36");
        for(int a=50;a<=500;a++) {
        	list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0."+(3000+a)+".108 Safari/537.36");
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
    
    public static String BaiduSpider(){
        return "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)";
    }
}
 



	

