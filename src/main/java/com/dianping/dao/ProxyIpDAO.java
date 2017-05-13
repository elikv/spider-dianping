package com.dianping.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.dianping.model.ProxyIpInfo;
import com.dianping.model.ProxyList;



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
    public List<ProxyIpInfo> selectAll();
    
    
    
}
