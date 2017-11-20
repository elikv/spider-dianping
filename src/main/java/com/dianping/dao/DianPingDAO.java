package com.dianping.dao;


import com.dianping.model.DianPingInfo;


/**
 * @author elikv
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
public interface DianPingDAO {

    //添加大众点评网的数据到数据库
    public int add(DianPingInfo dianPingInfo);
}
