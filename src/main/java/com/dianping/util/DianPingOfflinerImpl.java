package com.dianping.util;

import com.virjar.dungproxy.client.model.AvProxy;

public class DianPingOfflinerImpl implements DianPingOffliner {

	@Override
	public boolean needOffline(AvProxy avProxy) {
			return avProxy.getReferCount() > 3 && avProxy.getAvgScore() < 0.1D;// 
	}

}
