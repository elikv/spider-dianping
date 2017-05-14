package com.dianping.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dianping.dao.DianPingDAO;
import com.dianping.model.DianPingInfo;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午8:56
 */
@Component("DianPingDaoPipeline")
public class DianPingDaoPipeline implements PageModelPipeline<DianPingInfo> {

    @Autowired
    private DianPingDAO jobInfoDAO;

    public void process(DianPingInfo dianPingInfo, Task task) {
    	System.out.println("正在添加"+dianPingInfo.getTitle());
    	jobInfoDAO.add(dianPingInfo);
    }
}
