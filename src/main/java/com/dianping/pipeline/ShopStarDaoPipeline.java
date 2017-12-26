package com.dianping.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.DianPingInfo;
import com.dianping.model.ShopStar;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午8:56
 */
@Component("ShopStarDaoPipeline")
public class ShopStarDaoPipeline implements PageModelPipeline<ShopStar> {

	private  static Logger logger = LoggerFactory.getLogger(ShopStarDaoPipeline.class);
    @Autowired
    private DianPingDAO jobInfoDAO;

    public  void process(ShopStar shopStar, Task task) {
    	ShopStar byShopId = jobInfoDAO.findStarByShopId(shopStar.getShopId());
    	if(null==byShopId){
    		jobInfoDAO.addStar(byShopId);
    	}else{
    		shopStar.setFiveStar(shopStar.getFiveStar()+byShopId.getFiveStar());
    		shopStar.setFourStar(shopStar.getFourStar()+byShopId.getFourStar());
    		shopStar.setThreeStar(shopStar.getThreeStar()+byShopId.getThreeStar());
    		shopStar.setTwoStar(shopStar.getTwoStar()+byShopId.getTwoStar());
    		shopStar.setOneStar(shopStar.getOneStar()+byShopId.getOneStar());
    		jobInfoDAO.updateStar(shopStar);
    	}
    	System.out.println("正在添加shopId"+shopStar.getShopId()+",当前fiveStar："+
    	shopStar.getFiveStar()+"当前fourStar："+shopStar.getFourStar()
    	+"当前threeStar："+shopStar.getThreeStar()+"当前twoStar："+shopStar.getTwoStar()
    	+"当前oneStar："+shopStar.getOneStar());
    	logger.info("正在添加shopId"+shopStar.getShopId()+",当前fiveStar："+
    	shopStar.getFiveStar()+"当前fourStar："+shopStar.getFourStar()
    	+"当前threeStar："+shopStar.getThreeStar()+"当前twoStar："+shopStar.getTwoStar()
    	+"当前oneStar："+shopStar.getOneStar());
    	
    }
}
