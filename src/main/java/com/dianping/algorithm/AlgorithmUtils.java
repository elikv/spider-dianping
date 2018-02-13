package com.dianping.algorithm;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.model.AppraiseEntity;
import com.dianping.model.ShopIdRankTimeScoreEntity;

public class AlgorithmUtils {
	public static Logger logger = LoggerFactory.getLogger(AlgorithmUtils.class);
	
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
	public static double NewtonCooling(double score,int x){
		double pow = Math.pow(2.718,PARAM.doubleValue()*x);
		return pow*score;
	}
	
	
	public static double NewtonCooling(ShopIdRankTimeScoreEntity shopIdRankTimeScoreEntity) throws ParseException{
		String[] scorelist = shopIdRankTimeScoreEntity.getScore().split(",");
		String[] rankTimelist = shopIdRankTimeScoreEntity.getRankTime().split(",");
		double sum = 0;
		if(scorelist.length == rankTimelist.length){
			for (int i = 0; i < rankTimelist.length; i++) {
				int daysBetween = daysBetween(rankTimelist[i], today());
				double newtonCooling = NewtonCooling(Double.valueOf(scorelist[0]),daysBetween);
				sum = sum + newtonCooling;
			}
		}else{
			logger.info("id为"+shopIdRankTimeScoreEntity.getShopId()+"的店数据格式有误,请检查");
		}
		
		return sum;
		
	}
	/**
	 * 根据排名 转换成分数
	 * @param rankNo
	 * @return
	 */
	public int getScore(int rankNo){
		return 100-rankNo;
	}
	/**
	 * 计算两个日期的差值  以天为单位 可跨年
	 * @param sourceDate
	 * @param targetDate
	 * @return
	 * @throws ParseException
	 */
	 @SuppressWarnings("deprecation")
	public static int daysBetween(String sourceDate,String targetDate) throws ParseException {
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        int yearDay = 0 ;
	        Date fDate=sdf.parse(sourceDate);
	        Date oDate=sdf.parse(targetDate);
	        int year = fDate.getYear();
	        int year2 = oDate.getYear();
	        yearDay = (year2-year)*365;
	        Calendar aCalendar = Calendar.getInstance();
	        aCalendar.setTime(fDate);
	        
	        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	        aCalendar.setTime(oDate);
	        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	        int day3 = day2+yearDay;
	        int days=day3-day1;
	        System.out.print(days);
	        return days;
	    }
	
	public static String today(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(date);
		return format;
	}
	
	public static void main(String[] args) throws ParseException {
		today();
	}
}
