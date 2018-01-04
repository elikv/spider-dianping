package com.dianping.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.ShopStarEntity;
import com.dianping.model.ShopStarEntityExtend;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午8:56
 */
@Component("ShopStarDaoPipeline")
public class ShopStarDaoPipeline implements PageModelPipeline<ShopStarEntity> {

	private  static Logger logger = LoggerFactory.getLogger(ShopStarDaoPipeline.class);
    @Autowired
    private DianPingDAO jobInfoDAO;

    public  synchronized void process(ShopStarEntity shopStar, Task task) {
    	ShopStarEntityExtend shopStarEntityExtend = new ShopStarEntityExtend();
    	
    	BeanUtils.copyProperties(shopStar, shopStarEntityExtend);
    	shopStar.setId(shopStar.getUUID());
    	shopStarEntityExtend.setId(shopStar.getId());
    	ShopStarEntity byShopId = jobInfoDAO.findStarByShopId(shopStar.getShopId());
    	if(null==byShopId){
    		shopStar.setId(shopStar.getUUID());
    		jobInfoDAO.addStar(shopStar);
    	}else{
    		shopStar.setFiveStar(shopStar.getFiveStar()+byShopId.getFiveStar());
    		shopStar.setFourStar(shopStar.getFourStar()+byShopId.getFourStar());
    		shopStar.setThreeStar(shopStar.getThreeStar()+byShopId.getThreeStar());
    		shopStar.setTwoStar(shopStar.getTwoStar()+byShopId.getTwoStar());
    		shopStar.setOneStar(shopStar.getOneStar()+byShopId.getOneStar());
    		jobInfoDAO.updateStar(shopStar);
    	}
    	jobInfoDAO.addStarChild(shopStarEntityExtend);
    	System.out.println("正在添加shopId"+shopStar.getShopId()+",当前fiveStar："+
    	shopStar.getFiveStar()+",当前fourStar："+shopStar.getFourStar()
    	+",当前threeStar："+shopStar.getThreeStar()+",当前twoStar："+shopStar.getTwoStar()
    	+",当前oneStar："+shopStar.getOneStar());
    	logger.info("正在添加shopId"+shopStar.getShopId()+",当前fiveStar："+
    	shopStar.getFiveStar()+",当前fourStar："+shopStar.getFourStar()
    	+",当前threeStar："+shopStar.getThreeStar()+",当前twoStar："+shopStar.getTwoStar()
    	+",当前oneStar："+shopStar.getOneStar());
    	
    }
}
