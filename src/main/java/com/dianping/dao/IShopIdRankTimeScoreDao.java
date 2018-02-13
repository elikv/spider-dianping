package com.dianping.dao;


import org.springframework.stereotype.Component;

import com.dianping.model.ShopIdRankTimeScoreEntity;


/**
 * @author elikv
 *         Date: 18-2-13
 *         Time: 下午3:42
 */
@Component
public interface IShopIdRankTimeScoreDao {

    public int add(ShopIdRankTimeScoreEntity shopIdRankTimeScore);
    
    public ShopIdRankTimeScoreEntity findByShopId(int shopId);
    
    public void update(ShopIdRankTimeScoreEntity shopIdRankTimeScore);
    
    
    
}
