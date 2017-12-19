package com.dianping.util;

import com.virjar.dungproxy.client.ippool.strategy.Offline;
import com.virjar.dungproxy.client.ippool.strategy.impl.DefaultOffliner;
import com.virjar.dungproxy.client.model.AvProxy;

public interface DianPingOffliner extends Offline {

	public  boolean needOffline(AvProxy avProxy);

}
