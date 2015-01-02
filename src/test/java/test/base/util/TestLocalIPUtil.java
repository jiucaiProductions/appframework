package test.base.util;

import java.util.List;

import org.jiucai.appframework.base.util.LocalIPUtil;

public class TestLocalIPUtil {


	public static void main(String[] args) {
		System.out.println("os.name: " + 	System.getProperty("os.name") );
		
	
		System.out.println("getLocalIP: " + LocalIPUtil.getLocalIP());

		System.out.println("getIpByNetworkInterfaceName: "
				+ LocalIPUtil.getIpByNetworkInterfaceName("eth0"));
		
		
		List<String> ipv4List = LocalIPUtil.getIPv4();
		
		if(null != ipv4List){
			for (int i = 0; i < ipv4List.size();  i++) {
				System.out.println("IPv4 [" + i + "]: "  +ipv4List.get(i) );
			}
		}
		
		List<String> ipv6List = LocalIPUtil.getIPv6();
		
		if(null != ipv6List){
			for (int i = 0; i < ipv6List.size();  i++) {
				System.out.println("IPv6  [" + i + "]: "  +ipv6List.get(i) );
			}
		}
		

	}

}
