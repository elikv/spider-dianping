package com.dianping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.dianping.dao.RankShopDao;
import com.dianping.model.RankShopInfo;
@Service
public class RankServiceImpl {
	@Autowired
	private RankShopDao rankShopDao;
	
	private Logger logger = LoggerFactory.getLogger(RankServiceImpl.class);

	
	public List<RankShopInfo> findByShopIdAndRankTime (int shopId,String rankTime){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shopId", shopId);
		map.put("rankTime", rankTime);
		List<RankShopInfo> list = rankShopDao.findByShopIdAndRankTime(map);
		return list;
	}
	
	public List<RankShopInfo> findByShopIdAndRankType (int shopId,String rankType){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shopId", shopId);
		map.put("rankType", rankType);
		List<RankShopInfo> list = rankShopDao.findByShopIdAndRankType(map);
		return list;
	}
	
	public void distinctList(){
		List<String> findShopIdAll = rankShopDao.findShopIdAll();
		for (String shopId : findShopIdAll) {
			distinct(Integer.parseInt(shopId));
		}
		
	}
	/**
	 * //用一个三维数组的set来去重   以rankTime,rankType,categoryId为维度
	 * @param shopId
	 */
	public void distinct(int shopId){
		Set<ArrayList<String>> set = new HashSet<ArrayList<String>>();
		List<RankShopInfo> findByShopId = rankShopDao.findByShopId(shopId);
		for (RankShopInfo rankShopInfo : findByShopId) {
			//以rankTime,rankType,categoryId为维度 进行去重
			ArrayList<String> list = new ArrayList<String>();
			list.add(rankShopInfo.getCategoryId());
			list.add(rankShopInfo.getRankType());
			list.add(rankShopInfo.getRankTime().toString());
			//重复 则删除
			if(!set.add(list)) {
				rankShopDao.deletefromId(rankShopInfo.getId());
				logger.info("正在删除shopId为"+rankShopInfo.getShopId()+"的重复项");
			}
		}
	}
	
	
	public List<RankShopInfo>findNewtonCooling(String category,String start, String end){
		Map<String,Object> map = new HashMap<String,Object>();
			map.put("category", category);
			map.put("start", start);
			map.put("end", end);
		List<RankShopInfo> list = rankShopDao.findNewtonCooling(map);
		return list;
	}
	
	public static void main(String[] args) {
		AbstractApplicationContext ab = new ClassPathXmlApplicationContext("classpath:application*.xml");
		RankServiceImpl bean = ab.getBean(RankServiceImpl.class);
		bean.distinct(8844957);
	}
}
