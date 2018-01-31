package com.dianping.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class URLUtils {
	
	public final static  String[] categoryId= {"101","113","117","111","116",
			"114","103","508","115","102","109","106","104","248","3243",
			"251","26481","203","107","105","215","219","247","1783","118",
			"110","34014","34032","34015"};
	
	public final static  String[] rankType= {"popscore","score","score1","score2","score3"};
	
	public final static  String cityId="1";
	
	public final static  String shopType="10";
	
	public final static  String baseUrl_rank = "http://www.dianping.com/mylist/ajax/shoprank";
	
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
	
	public final static String[] gUrl= {"g101","g113","g117","g132","g111","g112","g116","g114","g103","g508","g115","g102","g109","g106","g104","g248","g3243","g251","g26481","g203","g107","g105","g215","g219","g247","g1783","g118","g110","g34014","g34032","g34015","g198","g25474","g199","g200","g201","g202"};
	public final static String[] rUrl= {"r801","r802","r804","r865","r860","r803","r835","r812","r842","r846","r849","r806","r808","r811","r839","r854","r1","r2","r3","r4","r5","r6","r7","r8","r9","r10","r5937","r12","r5938","r5939","r8846","r8847","r3580","r1325","r1326","r1327","r1328","r1329","r1330","r3110","r1331","r1332","r6338","r6339","r25986","r8135","r26247"};
	public final static String baseUrl_category="http://www.dianping.com/search/category/1/10/";
    /**
     *      http://www.dianping.com/shoplist/search/1_10_0_popscore 热门
     		http://www.dianping.com/shoplist/search/1_10_0_score 评分
   			http://www.dianping.com/shoplist/search/1_10_0_score1 口味
   			http://www.dianping.com/shoplist/search/1_10_0_score2 环境
   			http://www.dianping.com/shoplist/search/1_10_0_score3 服务 
     * @return
     */
	
	public String[] getUrlFromCategroy(){
     ArrayList<String> list = new ArrayList<String>();
     		for(int i=0;i<gUrl.length;i++) {
     			list.add(baseUrl_category+gUrl[i]+"o3");
     		}
     		for(int i=0;i<rUrl.length;i++) {
     			list.add(baseUrl_category+rUrl[i]+"o3");
     		}
     		for(int i=0;i<gUrl.length;i++) {
     			for(int j=0;j<rUrl.length;j++) {
         			list.add(baseUrl_category+gUrl[i]+rUrl[i]+"o3");
         		}
     		}
     		String[] array = list.toArray(new String[0]);
     		return array;
     }

}
