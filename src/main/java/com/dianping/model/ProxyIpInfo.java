package com.dianping.model;

import lombok.Data;

@Data
public class ProxyIpInfo {
	private String host;
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
