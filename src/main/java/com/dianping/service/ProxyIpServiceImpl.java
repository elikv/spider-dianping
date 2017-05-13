package com.dianping.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianping.dao.ProxyIpDAO;
import com.dianping.model.ProxyIpInfo;
import com.dianping.model.ProxyList;

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
		List<ProxyIpInfo> selectAll = proxyIpDao.selectAll();
		ProxyList proxyList = new ProxyList();

		for(ProxyIpInfo proxyIpInfo : selectAll){
		proxyList.getSuccessIPVector().add(proxyIpInfo.getIp()+":"+proxyIpInfo.getPort());
		}
		return proxyList;
	}

}
