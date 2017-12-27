package com.dianping.model;




import java.util.List;

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
	private String id;
	
//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str50 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str50 star\"]")
	private List<String> fiveStarDiv;
	private int fiveStar;
//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str40 star\"]")
	private List<String> fourStarDiv;
	private int fourStar;


	//	@ExtractBy("count(//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str30 star\"])")
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str30 star\"]")
	private List<String> threeStarDiv;
	private int threeStar;
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str20 star\"]")
	private List<String> twoStarDiv;
	private int twoStar;
	@ExtractBy("//div[@class=\"review-rank\"]/span[@class=\"sml-rank-stars sml-str10 star\"]")
	private List<String> oneStarDiv;
	private int oneStar;
	private String shopId;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	
	
	   @Override
	    public String toString() {
	        return "DianPingInfo{" +
	                "shopId='" + shopId + '\'' +
	                ", fiveStar='" + fiveStar + '\'' +
	                ", fourStar='" + fourStar + '\'' +
	                ", threeStar='" + threeStar + '\'' +
	                ", twoStar='" + twoStar + '\'' +
	                 ", oneStar='" + oneStar + '\'' +
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
		fiveStar = fiveStarDiv.size();
		fourStar = fourStarDiv.size();
		threeStar = threeStarDiv.size();
		twoStar = twoStarDiv.size();
		oneStar = oneStarDiv.size();
		
		
		
		
	}

	
}
