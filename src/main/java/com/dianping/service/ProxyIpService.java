package com.dianping.service;

import java.util.List;

import com.dianping.model.ProxyIpInfo;
import com.dianping.model.ProxyList;

public interface ProxyIpService {
	//加入数据库
	public void add( ProxyIpInfo proxyIpInfo);
	//取数据库数据
	public ProxyList selectAll();

}