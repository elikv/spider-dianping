package com.lin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.dao.ProxyIpDAO;
import com.lin.domain.ProxyIpInfo;
import com.lin.domain.ProxyList;

@Service
public class ProxyIpServiceImpl implements ProxyIpService {
	
	@Autowired
	private ProxyIpDAO proxyIpDao;

	@Override
	public void add(ProxyIpInfo proxyIpInfo) {
		proxyIpDao.add(proxyIpInfo);
		System.out.println("正在添加"+proxyIpInfo.getIp());

	}

	@Override
	public ProxyList selectAll() {
		proxyIpDao
		return null;
	}

}
