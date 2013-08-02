package org.jiucai.appframework.base.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.common.util.BaseUtil;

/**
 * 获取本机IP的工具类
 * 
 * @author zhaidw
 * 
 */
public class LocalIPUtil extends BaseUtil {

	public static void main(String[] args) {
		
		//System.out.println("eth0: " + getIpByNetworkInterfaceName("eth0"));
		//System.out.println("eth1: " + getIpByNetworkInterfaceName("eth1"));
		
		String serverIP = getLocalIP();
		System.out.println("LocalIP : " + serverIP);
	}

	/**
	 * 判断当前操作是否Windows.
	 * 
	 * @return true---是Windows操作系统
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	
	private static String getNetworkInterface(NetworkInterface ni ){

		String ipValue = "";
		
		InetAddress ip = null;
		
		if( null == ni){
			return ipValue;
		}

		try {

			// 虚拟网卡或未使用网卡，直接跳过
			if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
				return ipValue;
			}
			// ----------特定情况，可以考虑用ni.getName判断
			// 遍历所有ip
			Enumeration<InetAddress> addrEnum = ni.getInetAddresses();
			while (addrEnum.hasMoreElements()) {
				ip = (InetAddress) addrEnum.nextElement();

				log.debug("HostName: " + ip.getHostName() + " HostAddress: " + ip.getHostAddress());

				if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
						&& ip.getHostAddress().indexOf(":") == -1) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("获取 HostName[" + ip.getHostName() +"] IP失败: " + ExceptionUtils.getFullStackTrace(e));
		}

		if (null != ip) {
			ipValue = ip.getHostAddress();
		}
		
		return ipValue;
	}
	
	
	
	/**
	 * 根据网卡名获取 ip
	 * @param networkInterfaceName
	 * @return String
	 */
	public static String getIpByNetworkInterfaceName(String networkInterfaceName) {

		String ipValue = "";
		
		NetworkInterface ni;
		try {
			ni = NetworkInterface.getByName(networkInterfaceName);
			ipValue = getNetworkInterface(ni);
			
		} catch (SocketException e) {
			log.error("获取 NetworkInterface[" + networkInterfaceName +"] IP失败: " + ExceptionUtils.getFullStackTrace(e));
		}
		
		

		return ipValue;

	}

	/**
	 * 获取本机IP地址，并自动区分Windows还是Linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		String ipValue = "";
		
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				InetAddress addr = InetAddress.getLocalHost();
				
				if(null != addr){
					ipValue = addr.getHostAddress();
				}
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				
				
				ipValue = getIpByNetworkInterfaceName("eth0");
				log.debug("eth0: " + ipValue);
				
				if( StringUtils.isBlank(ipValue)){
					ipValue = getIpByNetworkInterfaceName("eth1");
					log.debug("eth1: " + ipValue);
					
				}else if( StringUtils.isBlank(ipValue)){
					
					Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
					while (netInterfaces.hasMoreElements()) {
						if (bFindIP) {
							break;
						}
						NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();

						log.debug("NetworkInterface Name: " + ni.getName());
						
						if(null != ni){
							ipValue = getNetworkInterface(ni);
							if(StringUtils.isNotBlank(ipValue)){
								bFindIP = true;
								
								log.debug(ni.getName() + ": " + ipValue);
								
								break;
							}
						}
						
					}
					
					
				}
				
				
			}
		} catch (Exception e) {
			log.error("获取本机IP失败: " + ExceptionUtils.getFullStackTrace(e));
		}

		return ipValue;
	}
}
