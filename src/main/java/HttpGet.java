import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dianping.dao.RankShopDao;
import com.dianping.model.RankShopInfo;
import com.dianping.util.URLUtils;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClient;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClientBuilder;

public class HttpGet {
	
	@Autowired
	private RankShopDao rankShopDao;
	
	
	
	
	public void doGet() throws ParseException {
		CrawlerHttpClient client = CrawlerHttpClientBuilder.create().build();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("cityId","1"));
		params.add(new BasicNameValuePair("shopType","10"));
		params.add(new BasicNameValuePair("rankType",URLUtils.rankType[0]));
		params.add(new BasicNameValuePair("categoryId",URLUtils.categoryId[0]));
		String data = client.get(URLUtils.baseUrl,params,Charset.defaultCharset());
		List<RankShopInfo> parseData = parseData(data);
		rankShopDao.addList(parseData);
	}
	
	public static void main(String[] args) throws ParseException {
		new HttpGet().doGet();
	}
	
	public List<RankShopInfo> parseData(String data) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(new Date());
		Date rankTime = simpleDateFormat.parse(format);
		 JSONObject parseObject = JSON.parseObject(data);
		 String rankType = parseObject.getString("rankType");
		 String categoryId = parseObject.getString("categoryId");
		List<RankShopInfo> list = JSON.parseArray(parseObject.getString("shopBeans"), RankShopInfo.class);
		for (int i = 0; i < list.size(); i++) {
			RankShopInfo rankShopInfo = list.get(i);
			rankShopInfo.setRankNo(i+1);
			rankShopInfo.setCategoryId(categoryId);
			rankShopInfo.setRankType(rankType);
			rankShopInfo.setRankTime(rankTime);
			rankShopInfo.setUrl("www.dianping.com/shop/"+rankShopInfo.getShopId());
		}
		return list;
	}

}
