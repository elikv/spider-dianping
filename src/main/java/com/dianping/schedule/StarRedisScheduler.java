package com.dianping.schedule;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;  
  
/** 
 * Redis中存储着所有抓取到的链接 抓取程序首先判断Redis中是否存在,如果存在该链接直接抛弃. //ERROR 
 *  
 * @author elikv 
 */  
@Component
public class StarRedisScheduler extends DuplicateRemovedScheduler implements  
        MonitorableScheduler, DuplicateRemover {  
	private  static Logger logger = LoggerFactory.getLogger(StarRedisScheduler.class);
    private JedisPool pool;  
  
    private static final String QUEUE_PREFIX = "star_queue_";  
  
    private static final String SET_PREFIX = "star_set_";  
  
    private static final String ITEM_PREFIX = "star_item_";
    
    private static final String RANK_PREFIX = "rank_category_";
    
    private static final String ITEM_SUCCESS_PREFIX = "star_success_";
  
    public StarRedisScheduler(String host) {  
        this(new JedisPool(new JedisPoolConfig(), host));  
    }  
  
    public StarRedisScheduler(JedisPool pool) {  
        this.pool = pool;  
    }  
    public StarRedisScheduler() {  
         
    }  
  
    @Override  
    public void resetDuplicateCheck(Task task) {  
        Jedis jedis = pool.getResource();  
        try {  
            jedis.del(getSetKey(task));  
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
  
    @Override  
    public boolean isDuplicate(Request request, Task task) {  
        Jedis jedis = pool.getResource();  
        try {  
            boolean isDuplicate = jedis.sismember(getSetKey(task),  
                    request.getUrl());   
            if (!isDuplicate) {  
                jedis.sadd(getSetKey(task), request.getUrl());   
            }  
            return isDuplicate;  
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
  
    @Override  
    protected void pushWhenNoDuplicate(Request request, Task task) {  
        Jedis jedis = pool.getResource();  
        try {
        	jedis.rpush(getQueueKey(task), request.getUrl());  
        
        if (checkForAdditionalInfo(request)) {
        	String field = DigestUtils.shaHex(request.getUrl());  
        	String value = JSON.toJSONString(request);
            jedis.hset((ITEM_PREFIX + task.getUUID()), field, value);  
        }
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
    
    @Override
    public void push(Request request, Task task) {
        logger.trace("get a candidate url {}", request.getUrl());
        if (shouldReserved(request) || noNeedToRemoveDuplicate(request) || !isDuplicate(request, task)) {
            logger.debug("push to queue {}", request.getUrl());
            pushWhenNoDuplicate(request, task);
        }
    }
  
    /** 
     * 移动并且返回队列头部一个元素 
     */  
    @Override
    public synchronized Request poll(Task task) {
        Jedis jedis = pool.getResource();
        try {
            String url = jedis.lpop(getQueueKey(task));
            if (url == null) {
                return null;
            }
            String key = ITEM_PREFIX + task.getUUID();
            String field = DigestUtils.shaHex(url);
            byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
            if (bytes != null) {
                Request o = JSON.parseObject(new String(bytes), Request.class);
                return o;
            }
                Request request = new Request(url);
            return request;
        } finally {
            pool.returnResource(jedis);
        }
    }
  
    protected String getSetKey(Task task) {  
        return SET_PREFIX + task.getUUID();  
    }  
  
    protected String getQueueKey(Task task) {  
        return QUEUE_PREFIX + task.getUUID();  
    }  
  
    @Override  
    public int getLeftRequestsCount(Task task) {  
        Jedis jedis = pool.getResource();  
        try {  
            Long size = jedis.llen(getQueueKey(task));  
            return size.intValue();  
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
  
    @Override  
    public int getTotalRequestsCount(Task task) {  
        Jedis jedis = pool.getResource();  
        try {  
            Long size = jedis.scard(getQueueKey(task));  
            return size.intValue();  
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
    
    private boolean checkForAdditionalInfo(Request request) {
        if (request == null) {
            return false;
        }

        if (!request.getHeaders().isEmpty() || !request.getCookies().isEmpty()) {
            return true;
        }

        if ( StringUtils.isNotBlank(request.getMethod())) {
            return true;
        }

        if ( request.getRequestBody() != null) {
            return true;
        }

        if (request.getExtras() != null && !request.getExtras().isEmpty()) {
            return true;
        }
        if (request.getPriority() != 0L) {
            return true;
        }

        return false;
    }
    
    
    
    
    
    
    public void push(String url) {
        logger.trace("get a candidate url {}", url);
        if ( !isDuplicate(url)) {
            logger.debug("push to queue {}", url);
            pushWhenNoDuplicate(url);
        }
    }
    
    public boolean isDuplicate(String url) {  
        Jedis jedis = pool.getResource();  
        try {  
            boolean isDuplicate = jedis.sismember(SET_PREFIX + "www.dianping.com",  
                    url);   
            if (!isDuplicate) {  
                jedis.sadd(SET_PREFIX + "www.dianping.com", url);   
            }  
            return isDuplicate;  
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
    
    public void pushWhenNoDuplicate(String url) {  
        Jedis jedis = pool.getResource();  
        try {
        	jedis.rpush(QUEUE_PREFIX +"www.dianping.com", url);  
        
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
    
    public void pushData(String data,String rankTime) {  
        Jedis jedis = pool.getResource();  
        try {
        	jedis.rpush(RANK_PREFIX+rankTime, data);  
        
        } finally {  
            pool.returnResource(jedis);  
        }  
    }  
    
    public void hsetSuccess(Request request){
    	 Jedis jedis = pool.getResource();  
         try {
        	 String shopId = request.getUrl().split("/")[4];
         	String field = DigestUtils.shaHex(request.getUrl());  
         	String value = JSON.toJSONString(request);
             jedis.hset((ITEM_SUCCESS_PREFIX +shopId), field, value);  
         } finally {  
             pool.returnResource(jedis);  
         }  
    }
    
    
    
  
}  
