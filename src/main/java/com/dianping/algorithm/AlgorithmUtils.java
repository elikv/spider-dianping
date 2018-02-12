package com.dianping.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dianping.model.AppraiseEntity;

public class AlgorithmUtils {
	
	
	/**
	 * 95%可信度统计量常数1.96
	 */
	private static final BigDecimal K =new BigDecimal("1.96");
	
	/**
	 * 牛顿冷却系数   -0.658  7天近似为0
	 */
	private static final BigDecimal PARAM = new BigDecimal("-0.658") ;
	
	
	
	public static List<BigDecimal> getScoreCope(){
		 List<BigDecimal> SCORE_COPE= new ArrayList<BigDecimal>();
		 SCORE_COPE.add(BigDecimal.ZERO);
		 SCORE_COPE.add(new BigDecimal("5"));
		 SCORE_COPE.add(new BigDecimal("10"));
		 return SCORE_COPE;
	}
	
	public static List<AppraiseEntity>getAppraiseEntity(){
		List<AppraiseEntity> appraiseList= new ArrayList<AppraiseEntity>();
		appraiseList.add(new AppraiseEntity("1", "20", "20", "20"));
		appraiseList.add(new AppraiseEntity("2", "40", "40", "40"));
		appraiseList.add(new AppraiseEntity("3", "20", "40", "20"));
		return appraiseList;
	}
	/**
	 * @param entity 好中差评论数实体 
	 * @param n 项数
	 * @return
	 */
	public static BigDecimal Avg(AppraiseEntity entity,int n){
		List<BigDecimal> scoreCope = getScoreCope();
		BigDecimal bigDecimal = BigDecimal.ZERO;
		// 评论数 * 评论分值
		bigDecimal = new BigDecimal(entity.getCommon()).multiply(scoreCope.get(1)).add(new BigDecimal(entity.getGood()).multiply(scoreCope.get(2)));
		bigDecimal = bigDecimal.divide(new BigDecimal(n), 2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal;
	}
	
	
	/*
	 * 牛顿冷却曲线   y=score*e^(-param*x)
	 * 本期得分 = 上一期得分 x exp(-(冷却系数) x 间隔天数)  天数为1
	 */
	public double NewtonCooling(double score,int x){
		double pow = Math.pow(2.718,PARAM.doubleValue()*x);
		return pow*score;
	}
	/**
	 * 根据排名 转换成分数
	 * @param rankNo
	 * @return
	 */
	public int getScore(int rankNo){
		return 100-rankNo;
	}
	
	public static void main(String[] args) {
		List<BigDecimal> scoreCope = getScoreCope();
		List<AppraiseEntity> appraiseEntity = getAppraiseEntity();
		for (AppraiseEntity entity : appraiseEntity) {
			int n=Integer.parseInt(entity.getBad())+Integer.parseInt(entity.getBad())+Integer.parseInt(entity.getBad());
			BigDecimal avg = Avg(entity, n);
			
		}
		
	}
}
