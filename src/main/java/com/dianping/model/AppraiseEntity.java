package com.dianping.model;

import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;

@Data
public class AppraiseEntity {
	protected String shopId;
	//好评数
	@ExtractBy("//div[@class=\"comment-filter-box\"]/lable[@class=\"J-filter-good\"]/span[@class=\"count\"]/text()")
	private String good;
	//中评数
	@ExtractBy("//div[@class=\"comment-filter-box\"]/lable[@class=\"J-filter-common\"]/span[@class=\"count\"]/text()")
	private String common;
	//差评数
	@ExtractBy("//div[@class=\"comment-filter-box\"]/lable[@class=\"J-filter-bad\"]/span[@class=\"count\"]/text()")
	private String bad;
	//第一种打分
	private String score1;
	//第二种打分
	private String score2;
	
	public AppraiseEntity(String shopId,String bad ,String common,String good){
		this.shopId=shopId;
		this.good=good;
		this.common=common;
		this.bad=bad;
	}
	public AppraiseEntity(){
		
	}
}
