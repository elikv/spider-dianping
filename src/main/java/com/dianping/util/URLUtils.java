package com.dianping.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class URLUtils {
	
	public final static  String[] categoryId= {"101","113","117","132","111","112","116",
			"114","103","508","115","102","109","106","104","248","3243",
			"251","26481","203","107","105","215","219","247","1783","118",
			"110","34014","34032","34015"};
	
	public final static  String[] rankType= {"popscore","score","score1","score2","score3"};
	
	public final static  String cityId="1";
	
	public final static  String shopType="10";
	
	public final static  String baseUrl = "http://www.dianping.com/mylist/ajax/shoprank";
	
	/**
	 * 默认接口参数List
	 * @return
	 */
	public static List<List<NameValuePair>> getParams(){
		List<List<NameValuePair>> needParams = new ArrayList<List<NameValuePair>>();
		//纯 按热门，口味，环境，服务分类
		for(int i=0;i<rankType.length;i++){
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("cityId","1"));
			params.add(new BasicNameValuePair("shopType","10"));
			params.add(new BasicNameValuePair("rankType",URLUtils.rankType[i]));
			needParams.add(params);
		}
		//纯 按菜系分类
		for(int i=0;i<categoryId.length;i++){
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("cityId","1"));
			params.add(new BasicNameValuePair("shopType","10"));
			params.add(new BasicNameValuePair("categoryId",URLUtils.categoryId[i]));
			needParams.add(params);
		}
		//按 (热门，口味，环境，服务)/菜系 分类
		for(int i=0;i<rankType.length;i++){
			for(int j=0;j<categoryId.length;j++){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("cityId","1"));
				params.add(new BasicNameValuePair("shopType","10"));
				params.add(new BasicNameValuePair("rankType",URLUtils.rankType[i]));
				params.add(new BasicNameValuePair("categoryId",URLUtils.categoryId[j]));
				needParams.add(params);
			}
			
		}
		
		
		return needParams;
	}
	
	
	

}
