package com.dianping.model;

public class ShopStarEntityExtend extends ShopStarEntity {
	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Override
    public String toString() {
        return "DianPingInfo{" +
                "shopId='" + shopId + '\'' +
                ", fiveStar='" + fiveStar + '\'' +
                ", fourStar='" + fourStar + '\'' +
                ", threeStar='" + threeStar + '\'' +
                ", twoStar='" + twoStar + '\'' +
                 ", oneStar='" + oneStar + '\'' +
                  ", url='" + url + '\'' +
                  ", parentId='" + parentId + '\'' +
                '}'; 
    }

}
