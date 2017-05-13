import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianping.model.ProxyIpInfo;
import com.dianping.service.ProxyIpService;
import com.lin.baseTest.SpringTestCase;

public class insertTest extends SpringTestCase {
	@Autowired
	private ProxyIpService proxyIpService;
	@Test
	public void startFind(){
		ProxyIpInfo proxyIpInfo = new ProxyIpInfo();
		proxyIpInfo.setIp("123");
		proxyIpInfo.setPort(123);
		proxyIpService.add(proxyIpInfo);
		
//		CatchProxyIp catchProxyIp = new CatchProxyIp();
//		ProxyList proxyList = new ProxyList();
//		proxyList.setIpVector(FindProxyWeb.find(proxyList));
//		catchProxyIp.SetProxyList(proxyList);
//		new Thread(catchProxyIp).start();
//		 new Thread(catchProxyIp).start();
//		 new Thread(catchProxyIp).start();
//		 new Thread(catchProxyIp).start();
		
	}
}
