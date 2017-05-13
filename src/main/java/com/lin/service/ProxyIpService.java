package com.lin.service;

import com.lin.domain.ProxyIpInfo;
import com.lin.domain.ProxyList;

public interface ProxyIpService {
	//加入数据库
	public void add( ProxyIpInfo proxyIpInfo);
	//取数据库数据
	public ProxyList selectAll();

}
