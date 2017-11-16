package com.dianping.model;

import lombok.Data;



/**
 * ip与port
 * @author elikv
 *
 */
@Data
public class ProxyIpInfo {
	
	private String ip;
	private int port;
	private boolean flag;

}