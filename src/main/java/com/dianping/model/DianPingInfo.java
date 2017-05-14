package com.dianping.model;



import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
@TargetUrl("http://www.dianping.com/shop/\\d+")
@HelpUrl("http://www.dianping.com/search/category/1/10/g\\d+o3p\\d+")
public class DianPingInfo implements AfterExtractor  {
	@ExtractBy("//div[@class=\"basic-info\"]/h1[@class=\"shop-name\"]/text()")
	private String title="";
	
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"reviewCount\"]/text()")
	private String comments="";
	
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"avgPriceTitle\"]/text()")
	private String average="";
	
	//brief-info\"]/span[@id=\"comment_score\"]/allText()
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"comment_score\"]/allText()")
	private String comment_score;
    //	private String comment_score2=List2StringUtil.listToString(comment_score);
	
	@ExtractBy("//div[@class=\"expand-info address\"]/span[@class=\"item\"]/text()")
	private String address="";
	//div[@class=\"breadcrumb\"]/allText()
	@ExtractBy("//div[@class=\"breadcrumb\"]/allText()")
	private String styleAndTag;
    //	private String styleAndTag2=List2StringUtil.listToString(styleAndTag);
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getComment_score() {
		return comment_score;
	}
	public void setComment_score(String comment_score) {
		this.comment_score = comment_score;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStyleAndTag() {
		return styleAndTag;
	}
	public void setStyleAndTag(String styleAndTag) {
		this.styleAndTag = styleAndTag;
	}
	
	   @Override
	    public String toString() {
	        return "DianPingInfo{" +
	                "title='" + title + '\'' +
	                ", comments='" + comments + '\'' +
	                ", average='" + average + '\'' +
	                ", comment_score='" + comment_score+ '\'' +
	                ", address='" + address + '\'' +
	                ", styleAndTag='" + styleAndTag + '\'' +
	                '}';
	    }
	@Override
	public void afterProcess(Page page) {
//		System.out.println(  average  );
//		average=average.split("：")[1].split("元")[0];
//		System.out.println(  average  );
		
	}

	
}
