package com.lin.dao;

import org.apache.ibatis.annotations.Insert;

import com.lin.domain.ProxyIpInfo;
import com.lin.domain.ProxyList;



public interface ProxyIpDAO {
	/**
	 * 添加代理ip到数据库
	 * @param proxyIpInfo
	 * @return
	 */

    public void add(ProxyIpInfo proxyIpInfo);
    /**
     * 取数据库里所有的数据
     * @return
     */
    public ProxyList selectAll();
    
    
    
}
