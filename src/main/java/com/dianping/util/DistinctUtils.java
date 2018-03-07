package com.dianping.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dianping.dao.RankShopDao;
import com.dianping.model.RankShopInfo;

@Component
public class DistinctUtils {
	@Autowired
	private RankShopDao rankShopDao;
	
	private Logger logger = LoggerFactory.getLogger(DistinctUtils.class);
	/**
	 *  t_rank_shop 去重
	 */
	public void distinctList(){
		long currentTimeMillis = System.currentTimeMillis();
		List<String> findShopIdAll = rankShopDao.findShopIdAll();
		for (String shopId : findShopIdAll) {
			distinct(Integer.parseInt(shopId));
		}
		long seconds = (System.currentTimeMillis() - currentTimeMillis)/1000;
		logger.info("删除完成，总耗时"+seconds+"s");
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
	
	public static void main(String[] args) {
		AbstractApplicationContext ab = new ClassPathXmlApplicationContext("classpath:application*.xml");
		DistinctUtils bean = ab.getBean(DistinctUtils.class);
		bean.distinctList();
		ab.close();
	}

}
