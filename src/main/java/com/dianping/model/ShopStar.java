package com.dianping.model;



import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
@TargetUrl(value ="http://www.dianping.com/shop/\\d+/review_all",sourceRegion="//div[@class=\"reviews-pages\"]/a")

//http://www.dianping.com/shop/17182037/review_all

//@HelpUrl("^http://www.dianping.com/search/category/1/10/(g\\d{3,5})?(p*)?$"
//		+ "|^http://www.dianping.com/search/category/1/10/(r\\d{3,5})?(p*)?$"
//		+ "|c")


public class ShopStar implements AfterExtractor  {
	
	
	//sml-rank-stars sml-str40 star
	private String id;
	
	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"])")
	private int fiveStar;
	
	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"])")
	private int fourStar;
	
	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str30 star\"])")
	private int threeStar;
	
	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str20 star\"])")
	private int twoStar;
	
	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str10 star\"])")
	private int oneStar;
	
	private String shopId;
	
	

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getFiveStar() {
		return fiveStar;
	}
	public void setFiveStar(int fiveStar) {
		this.fiveStar = fiveStar;
	}
	public int getFourStar() {
		return fourStar;
	}
	public void setFourStar(int fourStar) {
		this.fourStar = fourStar;
	}
	public int getThreeStar() {
		return threeStar;
	}
	public void setThreeStar(int threeStar) {
		this.threeStar = threeStar;
	}
	public int getTwoStar() {
		return twoStar;
	}
	public void setTwoStar(int twoStar) {
		this.twoStar = twoStar;
	}
	public int getOneStar() {
		return oneStar;
	}
	public void setOneStar(int oneStar) {
		this.oneStar = oneStar;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	

	
	   @Override
	    public String toString() {
	        return "DianPingInfo{" +
	                "shopId='" + shopId + '\'' +
	                ", 5star='" + fiveStar + '\'' +
	                ", 4star='" + fourStar + '\'' +
	                ", 3star='" + threeStar + '\'' +
	                ", 2star='" + twoStar + '\'' +
	                 ", 1star='" + oneStar + '\'' +
	                '}';
	    }
	   
	 //http://www.dianping.com/shop/17182037/review_all
	@Override
	public void afterProcess(Page page) {
//		System.out.println(  average  );
//		average=average.split("：")[1].split("元")[0];
//		System.out.println(  average  );
		shopId = page.getRequest().getUrl();
		shopId = shopId.split("/")[4];
		
		
		
		
	}

	
}
