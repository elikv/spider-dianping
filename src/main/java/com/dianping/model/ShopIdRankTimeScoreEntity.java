package com.dianping.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ShopIdRankTimeScoreEntity {
	private int shopId;
	private String rankTime;
	private String score;
	private double coolingScore;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifyTime;
}
