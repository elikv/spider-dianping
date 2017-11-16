package com.dianping.dao;

import java.util.List;


import com.dianping.model.ProxyIpInfo;




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
    public List<ProxyIpInfo> selectAllValid();
    
    
    
}
