package com.dianping.model;

import lombok.Data;

public class ProxyIpInfo {
	private String host;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	private int port;
	
	public ProxyIpInfo(String host,int port){
		this.host = host ;
		this.port = port ;
	}
	public ProxyIpInfo(){
		this.host = host ;
		this.port = port ;
	}
}
