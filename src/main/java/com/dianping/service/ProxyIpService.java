package com.dianping.service;

import java.util.Vector;

import com.dianping.model.ProxyIpInfo;


public interface ProxyIpService {
	/**
	 * 加入数据库
	 * @param proxyIpInfo
	 */
	public void add( ProxyIpInfo proxyIpInfo);
	/**
	 * 取数据库的代理ipVector
	 * @return
	 */
	public Vector<String> setSuccessIPVector();

}
