package com.dianping.model;




import java.util.List;
import java.util.UUID;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
@TargetUrl(value ="http://www.dianping.com/shop/\\d+/review_all/p*",sourceRegion="//div[@class=\"reviews-pages\"]/a")

//http://www.dianping.com/shop/17182037/review_all

//@HelpUrl("^http://www.dianping.com/search/category/1/10/(g\\d{3,5})?(p*)?$"
//		+ "|^http://www.dianping.com/search/category/1/10/(r\\d{3,5})?(p*)?$"
//		+ "|c")


public class ShopStarEntity implements AfterExtractor  {
	
	
	//sml-rank-stars sml-str40 star
	public String id;
	
//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str50 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str50 star\"]")
	public List<String> fiveStarDiv;
	public int fiveStar;
//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"]")
	public List<String> fourStarDiv;
	public int fourStar;


	//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str30 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str30 star\"]")
	public List<String> threeStarDiv;
	public int threeStar;
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str20 star\"]")
	public List<String> twoStarDiv;
	public int twoStar;
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str10 star\"]")
	public List<String> oneStarDiv;
	public int oneStar;
	public String shopId;
	
	public String url;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getFiveStarDiv() {
		return fiveStarDiv;
	}

	public void setFiveStarDiv(List<String> fiveStarDiv) {
		this.fiveStarDiv = fiveStarDiv;
	}

	public int getFiveStar() {
		return fiveStar;
	}

	public void setFiveStar(int fiveStar) {
		this.fiveStar = fiveStar;
	}

	public List<String> getFourStarDiv() {
		return fourStarDiv;
	}

	public void setFourStarDiv(List<String> fourStarDiv) {
		this.fourStarDiv = fourStarDiv;
	}

	public int getFourStar() {
		return fourStar;
	}

	public void setFourStar(int fourStar) {
		this.fourStar = fourStar;
	}

	public List<String> getThreeStarDiv() {
		return threeStarDiv;
	}

	public void setThreeStarDiv(List<String> threeStarDiv) {
		this.threeStarDiv = threeStarDiv;
	}

	public int getThreeStar() {
		return threeStar;
	}

	public void setThreeStar(int threeStar) {
		this.threeStar = threeStar;
	}

	public List<String> getTwoStarDiv() {
		return twoStarDiv;
	}

	public void setTwoStarDiv(List<String> twoStarDiv) {
		this.twoStarDiv = twoStarDiv;
	}

	public int getTwoStar() {
		return twoStar;
	}

	public void setTwoStar(int twoStar) {
		this.twoStar = twoStar;
	}

	public List<String> getOneStarDiv() {
		return oneStarDiv;
	}

	public void setOneStarDiv(List<String> oneStarDiv) {
		this.oneStarDiv = oneStarDiv;
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
	
	public String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
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
	                '}';
	    }
	   
	 //http://www.dianping.com/shop/17182037/review_all
	@Override
	public void afterProcess(Page page) {
//		System.out.println(  average  );
//		average=average.split("：")[1].split("元")[0];
//		System.out.println(  average  );
		url = page.getRequest().getUrl();
		shopId = url.split("/")[4];
		fiveStar = fiveStarDiv.size();
		fourStar = fourStarDiv.size();
		threeStar = threeStarDiv.size();
		twoStar = twoStarDiv.size();
		oneStar = oneStarDiv.size();
		
		
		
		
	}

	
}
