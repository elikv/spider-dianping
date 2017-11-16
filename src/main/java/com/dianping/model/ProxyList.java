package com.dianping.model;

import java.util.Vector;
/**
 * 该实体类包含一个待测Vector和一个可用Vector
 * @author elikv
 *
 */
public class ProxyList {
	
	private Vector<String> successIPVector= new Vector<>();
	private Vector<String> ipVector = new Vector<>();
	public Vector<String> getSuccessIPVector() {
		return successIPVector;
	}

	public void setSuccessIPVector(Vector<String> successIPVector) {
		this.successIPVector = successIPVector;
	}

	public Vector<String> getIpVector() {
		return ipVector;
	}

	public void setIpVector(Vector<String> ipVector) {
		this.ipVector = ipVector;
	}

}
