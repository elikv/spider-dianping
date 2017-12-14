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
        list.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        list.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
        list.add("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:15.0) Gecko/20100101 Firefox/15.0.1");
        list.add("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
 



	

