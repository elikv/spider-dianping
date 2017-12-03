package com.dianping.schedule;
import java.util.HashMap;  
import java.util.Map;  
  
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;  
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;  
import com.alibaba.fastjson.JSONArray;  
import com.alibaba.fastjson.JSONObject;
import com.dianping.pipeline.DianPingDaoPipeline;

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
 * @author CainGao 
 */  
public class RedisScheduler extends DuplicateRemovedScheduler implements  
        MonitorableScheduler, DuplicateRemover {  
	private  static Logger logger = LoggerFactory.getLogger(RedisScheduler.class);
    private JedisPool pool;  
  
    private static final String QUEUE_PREFIX = "queue_";  
  
    private static final String SET_PREFIX = "set_";  
  
    private static final String ITEM_PREFIX = "item_";  
  
    public RedisScheduler(String host) {  
        this(new JedisPool(new JedisPoolConfig(), host));  
    }  
  
    public RedisScheduler(JedisPool pool) {  
        this.pool = pool;  
        setDuplicateRemover(this);  
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
        jedis.rpush(getQueueKey(task), request.getUrl());
        try {
        if (checkForAdditionalInfo(request)) {
            String field = DigestUtils.shaHex(request.getUrl());
            String value = JSON.toJSONString(request);
            jedis.hset((ITEM_PREFIX + task.getUUID()), field, value);
        }
        } finally {  
            pool.returnResource(jedis);  
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
  
}  
