import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dianping.jdbc.main;
import com.dianping.util.URLUtils;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClient;
import com.virjar.dungproxy.client.httpclient.CrawlerHttpClientBuilder;

public class HttpGet {
	
	
	
	
	public void doGet() {
		CrawlerHttpClient client = CrawlerHttpClientBuilder.create().build();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("cityId","1"));
		params.add(new BasicNameValuePair("shopType","10"));
		params.add(new BasicNameValuePair("rankType",URLUtils.rankType[0]));
		params.add(new BasicNameValuePair("category",URLUtils.category[0]));
		String string = client.get(URLUtils.baseUrl,params,Charset.defaultCharset());
		System.out.println(string);
		
	}
	
	public static void main(String[] args) {
		new HttpGet().doGet();
	}

}
