package com.dianping.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.dianping.model.DianPingInfo;


/**
 * @author elikv
 *         Date: 13-6-23
 *         Time: 下午4:27
 */
@Repository
public interface DianPingDAO {

    //添加大众点评网的数据到数据库
    public int add(DianPingInfo dianPingInfo);
    //返回存在的url
    public ArrayList<String> findExist();
}
