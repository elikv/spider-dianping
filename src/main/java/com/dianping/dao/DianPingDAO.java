package com.dianping.dao;


import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.dianping.model.DianPingInfo;
import com.dianping.model.ShopStarEntity;


/**
 * @author elikv
 *         Date: 17-6-23
 *         Time: 下午4:27
 */
@Repository
public interface DianPingDAO {

    //添加大众点评网的数据到数据库
    public int add(DianPingInfo dianPingInfo);
    //返回存在的url
    public ArrayList<String> findExist();
    
    public ArrayList<DianPingInfo> findAll();
    
    public void update(DianPingInfo dianPingInfo);
    
    public ShopStarEntity findStarByShopId(String shopId);
    
    public ShopStarEntity findStarAll();
    
    public void updateStar(ShopStarEntity shopStar);
    
    public void addStar(ShopStarEntity shopStar);
    
    public void addStarChild(ShopStarEntity shopStar);
}
