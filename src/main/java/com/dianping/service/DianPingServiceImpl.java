package com.dianping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.DianPingInfo;
import com.dianping.model.RankShopInfo;

@Service
public class DianPingServiceImpl  {
@Autowired
private DianPingDAO dianPingDAO;
	public void add(DianPingInfo dianPingInfo) {
		dianPingDAO.add(dianPingInfo);
	}

	public ArrayList<String> findExist() {
		ArrayList<String> findExist = dianPingDAO.findExist();
		return findExist;
	}
	
	public List<RankShopInfo>findRecommend(String category,String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("category", category);
			map.put("start", start);
			map.put("end", end);
		List<RankShopInfo> list = dianPingDAO.findRecommend(map);
		return list;
		
		
	}

}
