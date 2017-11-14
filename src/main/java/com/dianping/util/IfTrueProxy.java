package com.dianping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class IfTrueProxy {
	/**
	 * 思路
	 * java.net用代理ip尝试连接百度，从网页内容里找是否有baidu，有就返回true
	 * 输入流→BUfferReader存储进StringBuffer
	 */
	/**
	 * 判断是否是可用代理ip，是返回true
	 * @param ip
	 * @param port
	 */
	public static Boolean createIPAddress(String ip,int port) {
        URL url = null;
        try {
        url = new URL("http://www.baidu.com");
        } catch (MalformedURLException e) {
        System.out.println("url invalidate");
        }
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(ip, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
        InputStream in = null;
        try {
        URLConnection conn = url.openConnection(proxy);
        conn.setRequestProperty("User-Agent", UserAgentUtils.radomUserAgent());
		conn.setReadTimeout(1000*5);
		conn.setConnectTimeout(1000*20);
        in = conn.getInputStream();
        } catch (Exception e) {
        System.out.println("ip " + ip + " is not aviable");//异常IP
        }
        String s = convertStreamToString(in);
        //System.out.println(s);
        // System.out.println(s);
        if (s.indexOf("baidu") > 0) {//有效IP
        System.out.println(ip + ":"+port+ " is ok");
        return true;
        }
		return false;
        }
     
     
    public static String convertStreamToString(InputStream is) {
        if (is == null)
        return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
        while ((line = reader.readLine()) != null) {
        sb.append(line + "/n");
        }
        } catch (IOException e) {
        e.printStackTrace();
        } finally {
        try {
        is.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }
        return sb.toString();
 
        }

}
