package com.dianping.model;



import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
@TargetUrl("http://www.dianping.com/shop/\\d+")
//^http://www.dianping.com/search/category/1/10/(g\d{3,4})?(o3)?(p.*)?$
//@HelpUrl("http://www.dianping.com/search/category/1/10/((g\\d{3,4})|(r\\d{3,4}))?(o3)?(p*)?")
//@HelpUrl("http://www.dianping.com/search/category/1/10/(g\\d{3,4})?(o3)?(p*)?")

//@HelpUrl("^http://www.dianping.com/search/category/1/10/(g\\d{3,5})?(p*)?$"
//		+ "|^http://www.dianping.com/search/category/1/10/(r\\d{3,5})?(p*)?$"
//		+ "|^http://www.dianping.com/search/category/1/10/(g\\d{3,5}r\\d{3,5})?(p*)?$")


public class DianPingInfo implements AfterExtractor  {
	
	@ExtractBy("//div[@class=\"basic-info\"]/h1[@class=\"shop-name\"]/text()")
	private String shopName;
	
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"reviewCount\"]/text()")
	private String comment;
	
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"avgPriceTitle\"]/text()")
	private String average;
	
	//brief-info\"]/span[@id=\"comment_score\"]/allText()
	@ExtractBy("//div[@class=\"brief-info\"]/span[@id=\"comment_score\"]/allText()")
	private String comment_score;
    //	private String comment_score2=List2StringUtil.listToString(comment_score);
	
	@ExtractBy("//div[@class=\"expand-info address\"]/span[@class=\"item\"]/text()")
	private String address;
	//div[@class=\"breadcrumb\"]/allText()
	@ExtractBy("//div[@class=\"breadcrumb\"]/allText()")
	private String tag;
    //	private String styleAndTag2=List2StringUtil.listToString(styleAndTag);/a[@class=\"J_main-photo\"]/@href
//	@ExtractBy("//div[@class=\"photo-info\"]/span[@class=\"photo-count\"]/text()")
	@ExtractBy("//div[@class=\"aside\"]/div[@class=\"photo-thumb\"]/a[@class=\"add-photo J_addPhoto\"]/@target/text()")
	private String img;
	
	private String url;
	
	private String taste;
	private String env;
	private String service;
	private String cookStyle;
	
	private String threadNo;
	
	
	public String getThreadNo() {
		return threadNo;
	}
	public void setThreadNo(String threadNo) {
		this.threadNo = threadNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getCookStyle() {
		return cookStyle;
	}
	public void setCookStyle(String cookStyle) {
		this.cookStyle = cookStyle;
	}
	

	
	   @Override
	    public String toString() {
	        return "DianPingInfo{" +
	                "shopName='" + shopName + '\'' +
	                ", average='" + average + '\'' +
	                ", taste='" + taste + '\'' +
	                ", env='" + env + '\'' +
	                ", service='" + service + '\'' +
	                 ", address='" + address + '\'' +
	                  ", cookStyle='" + cookStyle + '\'' +
	                ", comment='" + comment + '\'' +
	                ", tag='" + tag+ '\'' +
	                ", url='" + url + '\'' +
	                ", img='" + img + '\'' +
	                
	                '}';
	    }
	@Override
	public void afterProcess(Page page) {
//		System.out.println(  average  );
//		average=average.split("：")[1].split("元")[0];
//		System.out.println(  average  );
		url = page.getRequest().getUrl();
		if(StringUtils.isEmpty(comment_score)) {
			page.setSkip(true);
			return;
		}
		String[] split = comment_score.split(":");
		taste=split[1].substring(0, 3);
		env = split[2].substring(0, 3);
		service = split[3];
		if(Double.valueOf(taste)<=7.5) {
			page.setSkip(true);
			return;
		}
		if(!StringUtils.equals(tag.split(">")[0].trim(), "上海美食")) {
			page.setSkip(true);
			return;
		}
		cookStyle= tag.split(">")[1];
		System.out.println(comment_score); 
		System.out.println(tag);
		//photo-header
		img = page.getHtml().xpath("//div[@class=\"photos\"]/a").css("img","src").toString();
		if(StringUtils.isEmpty(img)) {
			img = page.getHtml().xpath("//div[@class=\"photo-header\"]/a").css("img","src").toString();
		}
		System.out.println(img);
		threadNo = String.valueOf(Thread.currentThread().getId());
	}

	
}
