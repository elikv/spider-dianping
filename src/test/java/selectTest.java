import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianping.model.ProxyIpInfo;
import com.dianping.model.ProxyList;
import com.dianping.service.ProxyIpService;
import com.lin.baseTest.SpringTestCase;

public class selectTest  extends SpringTestCase{
	
	@Autowired
	private ProxyIpService proxyIpService;
	@Test
	public void startSelect(){
		ProxyList proxyList = proxyIpService.selectAll();
		
		
		
		for(int i =0 ;i<proxyList.getSuccessIPVector().size();i++)
			System.out.println(proxyList.getSuccessIPVector().get(i));
		

	}
}
