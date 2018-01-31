package com.dianping.downloader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.config.RedisConfig;
import com.dianping.schedule.StarRedisScheduler;
import com.dianping.util.JedisPoolConfigExtend;
import com.virjar.dungproxy.client.util.PoolUtil;
import com.virjar.dungproxy.webmagic7.DungProxyHttpClientGenerator;
import com.virjar.dungproxy.webmagic7.DungProxyProvider;
import com.virjar.dungproxy.webmagic7.UserSessionPage;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;



@ThreadSafe
public class WebMagicCustomOfflineProxyDownloader extends AbstractDownloader  {


	private Logger logger = LoggerFactory.getLogger(WebMagicCustomOfflineProxyDownloader.class);

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private DungProxyHttpClientGenerator httpClientGenerator = new DungProxyHttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();

    private DungProxyProvider proxyProvider ;

    private boolean responseHeader = true;

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    public void setProxyProvider(DungProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    public CloseableHttpClient getHttpClient(Site site) {
        if (site == null) {
            return httpClientGenerator.getClient(null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }



    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task)
            throws IOException {
        String content = getResponseContent(charset, httpResponse);
        Page page = new UserSessionPage();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    private String getResponseContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()",
                        Charset.defaultCharset());
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    private String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        return CharsetUtils.detectCharset(httpResponse.getEntity().getContentType().getValue(), contentBytes);
    }

    /**
     * 默认封禁403和401两个状态码的IP
     *
     * @param page 爬取结果
     * @return 是否需要封禁这个IP
     */
    protected boolean needOfflineProxy(Page page) {
    	String rawText = page.getRawText();
    	Integer statusCode = page.getStatusCode();
    	if( statusCode !=200){//父类默认下线 401和403,你也可以不调用
            return true;
        }
    	else if(!StringUtils.containsIgnoreCase(rawText, "dianping")) {
    		return true;
    	}
        else{
        	
        	return StringUtils.containsIgnoreCase(rawText, "验证中心") 
        			|| StringUtils.containsIgnoreCase(rawText, "Bad Request")
        			|| StringUtils.containsIgnoreCase(rawText, "403 Forbidden")
        			|| StringUtils.containsIgnoreCase(rawText, "welcome to zscaler directory authentication")
//        			|| StringUtils.containsIgnoreCase(rawText, "404 File Not Found")
        			;
        }
    }

    protected boolean needOfflineProxy(Exception e) {
        return false;
    }

    /**
     * 0.7.x page里面自带statusCode,不需要单独提供这个方法
     * 
     * @param statusCode statusCode
     * @return
     */
    @Deprecated
    protected boolean needOfflineProxy(int statusCode) {
        throw new UnsupportedOperationException("");
    }

    /**
     * 判断当前请求是不是最后的重试,流程等同于 addToCycleRetry
     * 
     * @see us.codecraft.webmagic.Spider#doCycleRetry
     * @param request request
     * @param site site
     * @return 是否是最后一次重试
     */
    protected boolean isLastRetry(Request request, Site site) {
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        if (cycleTriedTimesObject == null) {
            return false;
        } else {
            int cycleTriedTimes = (Integer) cycleTriedTimesObject;
            cycleTriedTimes++;
            if (cycleTriedTimes >= site.getCycleRetryTimes()) {
                return true;
            }
        }
        return false;
    }

    private Page addToCycleRetry(Site site, Page page) {
        if (site.getCycleRetryTimes() < 0) {
            site.setCycleRetryTimes(1);
        }
        if (page == null) {
            page = UserSessionPage.fail();
        } else {
            page.setDownloadSuccess(false);
        }
        return page;
    }

    public Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        logger.debug("downloading page {}", request.getUrl());
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        Proxy proxy = proxyProvider != null ? proxyProvider.getProxy(task) : null;
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
//        // 扩展功能,支持多用户隔离,默认使用的是crawlerHttpClient,crawlerHttpClient默认则使用multiUserCookieStore
//        if (request.getExtra(ProxyConstant.DUNGPROXY_USER_KEY) != null) {
//            PoolUtil.bindUserKey(requestContext.getHttpClientContext(),
//                    request.getExtra(ProxyConstant.DUNGPROXY_USER_KEY).toString());
//        }
        Page page = UserSessionPage.fail();
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(),
                    requestContext.getHttpClientContext());
            page = handleResponse(request, task.getSite().getCharset(), httpResponse, task);
            if (needOfflineProxy(page)) {
                PoolUtil.offline(requestContext.getHttpClientContext());
                return addToCycleRetry(task.getSite(), page);
            }
            onSuccess(request,page);
            logger.debug("downloading page success {}", page);
            return page;
        } catch (Exception e) {
            if (needOfflineProxy(e)) {
                logger.warn("发生异常:{},IP下线");
                PoolUtil.offline(requestContext.getHttpClientContext());// 由IP异常导致,直接重试
                return addToCycleRetry(task.getSite(), null);
            }
            if (isLastRetry(request, task.getSite())) {// 移动异常日志位置,只记录最终失败的。中途失败不算失败
                logger.warn("download page {} error", request.getUrl(), e);
            }
            if (task.getSite() != null && task.getSite().getCycleRetryTimes() > 0) {
                return addToCycleRetry(task.getSite(), null);
            }

            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
                // ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            if (proxyProvider != null && proxy != null) {
                proxyProvider.returnProxy(proxy, page, task);
            }
        }
    }
    public   void onSuccess(Request request,Page page) {
    	
    	addCategory( request, page);
    	//starCrawler触发
		if(StringUtils.contains(request.getUrl(), "review")){
			StarRedisScheduler redisScheduler = new StarRedisScheduler(new JedisPool(new JedisPoolConfigExtend(),RedisConfig.HOST_ADDRESS,6379,5000,RedisConfig.PASSWORD));
			redisScheduler.hsetSuccess(request);
		}
    	logger.info("successTime:"+new Date());
    	logger.info("request:"+request.toString());
    }
    
    /**
     * 加入0-50分页
     * @param request
     * @param page
     */
    public void addCategory(Request request,Page page){
    	System.out.println(new Date());
    	String url = request.getUrl();
    	boolean hasAid = false;
    	String aidUrl = "";
    	//增加分页列表   增加?aid判断
    	if(StringUtils.containsIgnoreCase(request.getUrl(), "http://www.dianping.com/search/category/1/10")) {
    		if(StringUtils.containsIgnoreCase(request.getUrl(), "aid")) {
    			hasAid = true;
    			String[] split = url.split("\\?aid");
    			url = split[0];
    			aidUrl = "?aid"+split[1];
    		}
    		
    		List<String> request2 = new ArrayList<String>();
    		//倒数第二个字母是p
    		if(StringUtils.equals(url.substring(url.length()-2,url.length()-1), "p")) {
    			String str3 = url.substring(0,url.length()-2);
    			for(int i=1;i<=50;i++) {
    				if(hasAid) {
    					request2.add(str3+"p"+String.valueOf(i)+aidUrl);
    				}else {
    				request2.add(str3+"p"+String.valueOf(i));
    				}
    			}
    		}
    		//倒数第三个字母是p    http://www.dianping.com/search/category/1/10/p21
    		else if(StringUtils.equals(url.substring(url.length()-3,url.length()-2), "p")) {
    			String str3 = url.substring(0,url.length()-3);
    			for(int i=1;i<=50;i++) {
    				if(hasAid) {
    					request2.add(str3+"p"+String.valueOf(i)+aidUrl);
    				}else {
    				request2.add(str3+"p"+String.valueOf(i));
    				}
    			}
    		}
    		//没有分页
    		
    		else{
    			for(int i=1;i<=50;i++) {
    				if(hasAid) {
    					request2.add(url+"p"+String.valueOf(i)+aidUrl);
    				}else {
    				request2.add(url+"p"+String.valueOf(i));
    				}
    			}
    		}
    		if(!CollectionUtils.isEmpty(request2)) {
    			page.addTargetRequests(request2);
    		}
    	}
    }
   
    
}

