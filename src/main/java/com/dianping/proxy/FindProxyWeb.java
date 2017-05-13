package com.dianping.proxy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dianping.model.ProxyList;
import com.dianping.util.UserAgentUtils;


public class FindProxyWeb {
	public static Vector<String> find(ProxyList proxyList){
		String url2 = "http://www.66ip.cn/nmtq.php?getnum=800&isp=0&"
				+ "anonymoustype=0&start=&ports=&export=&ipaddress=&area=1&proxytype=2&api=66ip";
	try{
		URL url = new URL(url2);
//		URL tmpURL = new URL("https://www.sogou.com");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(1000*10);
		connection.setConnectTimeout(1000*10);
		connection.setRequestProperty("User-Agent", UserAgentUtils.radomUserAgent());
		// 如果网页成功进入
		if (connection.getResponseCode() == 200) {
			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = "";
			Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+");
			Matcher matcher = null;
			// 读行找代理ip
			while ((line = reader.readLine()) != null) {
				matcher = pattern.matcher(line);
				while (matcher.find()) {
					proxyList.getIpVector().add(matcher.group());
				}
			}
			reader.close();
			inputStream.close();
			}
			connection.disconnect();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	return proxyList.getIpVector();
	}
}
