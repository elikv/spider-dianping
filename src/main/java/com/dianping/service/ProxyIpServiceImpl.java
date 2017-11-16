package com.dianping.service;


import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianping.dao.ProxyIpDAO;

import com.dianping.model.ProxyIpInfo;

@Service
public class ProxyIpServiceImpl implements ProxyIpService {
	
	@Autowired
	private ProxyIpDAO proxyIpDao;

	@Override
	public void add(ProxyIpInfo proxyIpInfo) {
		proxyIpDao.add(proxyIpInfo);
		System.out.println("正在添加"+proxyIpInfo.getHost());

	}

	@Override
	public Vector<String> setSuccessIPVector() {
		List<ProxyIpInfo> selectAll = proxyIpDao.selectAllValid();
		Vector<String> vector = new Vector<String>();

		for(ProxyIpInfo proxyIpInfo : selectAll){
			vector.add(proxyIpInfo.getHost()+":"+proxyIpInfo.getPort());
		}
		return vector;
	}

}
