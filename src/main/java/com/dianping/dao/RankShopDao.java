package com.dianping.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.dianping.model.DianPingInfo;
import com.dianping.model.RankShopInfo;


/**
 * @author elikv
 *         Date: 17-6-23
 *         Time: 下午4:27
 */
@Repository
public interface RankShopDao {

    //添加大众点评网的数据到数据库
    public int addList(List<RankShopInfo> dianPingInfo);
   
}
