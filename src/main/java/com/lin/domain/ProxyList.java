package com.lin.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;
/**
 * 该实体类包含一个待测Vector和一个可用Set
 * @author elikv
 *
 */
public class ProxyList {
	
	private CopyOnWriteArraySet<String> successIPSet = new CopyOnWriteArraySet<>();
	private Vector<String> ipVector = new Vector<>();
	public CopyOnWriteArraySet<String> getSuccessIPSet() {
		return successIPSet;
	}

	public void setSuccessIPSet(CopyOnWriteArraySet<String> successIPSet) {
		this.successIPSet = successIPSet;
	}

	public Vector<String> getIpVector() {
		return ipVector;
	}

	public void setIpVector(Vector<String> ipVector) {
		this.ipVector = ipVector;
	}

}
