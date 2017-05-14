package com.dianping.service;


import java.util.List;
import java.util.Vector;

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
	public Vector<String> setSuccessIPVector() {
		List<ProxyIpInfo> selectAll = proxyIpDao.selectAll();
		Vector<String> vector = new Vector<String>();

		for(ProxyIpInfo proxyIpInfo : selectAll){
			vector.add(proxyIpInfo.getIp()+":"+proxyIpInfo.getPort());
		}
		return vector;
	}

}
