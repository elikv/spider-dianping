package com.dianping.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dianping.model.DianPingInfo;
import com.dianping.model.ShopStarEntity;
import com.dianping.model.ShopStarEntityExtend;


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
    
    /**
     * 通过shopId查出所有starChild
     * @param shopId
     * @return
     */
    public List<ShopStarEntityExtend> findStarChildByShopId(String shopId);
    /**
     * 通过url查starChild
     * @param shopId
     * @return
     */
    public ShopStarEntityExtend findStarChildByUrl(String url);
    
    public List<ShopStarEntity> findStarAll();
    /**
     *  
     * @return t_shop_star所有shopId
     */
    public List<String>findShopIdByStar();
    
    public void updateStar(ShopStarEntity shopStar);
    
    public void addStar(ShopStarEntity shopStar);
    
    public void addStarChild(ShopStarEntity shopStar);
    
    public int findMaxPage(String shopId);
    //通过排行榜数据 查找上榜次数最多的 shopId
    public List<String> findStarShopId();
    
    public List<ShopStarEntity>findRecommend();
    
}
