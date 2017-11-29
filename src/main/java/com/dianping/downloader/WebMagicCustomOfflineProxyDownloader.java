package com.dianping.downloader;

import org.apache.commons.lang3.StringUtils;

import com.virjar.dungproxy.webmagic7.DungProxyDownloader;

import us.codecraft.webmagic.Page;

public class WebMagicCustomOfflineProxyDownloader extends DungProxyDownloader {
	@Override
    protected boolean needOfflineProxy(Page page) {
        if( super.needOfflineProxy(page)){//父类默认下线 401和403,你也可以不调用
            return true;
        }else{
            return StringUtils.containsIgnoreCase(page.getRawText(), "验证码");
        }
    }
}
