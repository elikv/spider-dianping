package com.dianping.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RankShopInfo {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date rankTime;
	
	private int rankNo;
	
	private String shopName;
	
	private String address;
	
	private String defaultPic;
	
	private int shopId;
	
	private String url;
	
	private String categoryId;
	
	private String rankType;
	
	private String shopTags;
	
	private String avgPrice;
	
	private String refinedScore1;
	private String refinedScore2;
	private String refinedScore3;
	
	private String id;
	
}
