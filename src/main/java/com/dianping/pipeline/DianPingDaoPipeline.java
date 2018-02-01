package com.dianping.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.AppraiseEntity;
import com.dianping.model.DianPingInfo;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


/**
 * @author elikv
 *         Date: 18-1-31
 *         Time: 下午5:56
 */
@Component("DianPingDaoPipeline")
public class DianPingDaoPipeline implements PageModelPipeline<DianPingInfo> {

	private  static Logger logger = LoggerFactory.getLogger(DianPingDaoPipeline.class);
    @Autowired
    private DianPingDAO jobInfoDAO;

    public  void process(DianPingInfo dianPingInfo, Task task) {
    	System.out.println("正在添加"+dianPingInfo.getShopName());
    	AppraiseEntity appraiseEntity = new AppraiseEntity();
    	BeanUtils.copyProperties(dianPingInfo, appraiseEntity);
    	
    	logger.info("正在添加"+dianPingInfo.getShopName());
    	AppraiseEntity findByShopId = jobInfoDAO.findByShopId(dianPingInfo.getShopId());
    	if(findByShopId==null){
    		jobInfoDAO.addAppraise(appraiseEntity);
    	}else{
    		jobInfoDAO.updateAppraiseEntity(appraiseEntity);
    	}
    	jobInfoDAO.add(dianPingInfo);
    }
}
