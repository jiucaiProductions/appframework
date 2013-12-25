package org.jiucai.appframework.common.util;

import java.net.URL;


/**
 * 域名工具
 * 
 * build: 20110711-1.0
 * 
 * <pre>
 * 用来从一个完整的 url 中解析顶级域名或子域名部分
 * 主要用于获取需要设置 cookie 的 domain
 * URL: 支持已知和未知协议、支持无线级子域名、支持IPv4 或 IPv6 (部分 IPv6 格式不支持)
 * </pre>
 * 
 * @author zhaidw
 * 
 */
public class DomainUtil extends BaseUtil {

	/**
	 * <pre>
	 * 根域名列表，. 多的靠前放
	 * 可以根据实际情况扩展
	 * </pre>
	 */
	protected static String[] rootDomains = { ".com.cn", ".net.cn", ".org.cn",
			".gov.cn", ".edu.cn", ".com.hk", ".net.hk", ".org.hk", ".gov.hk",
			".edu.hk", ".com.tw", ".net.tw", ".org.tw", ".gov.tw", ".edu.tw",
			".com", ".net", ".org", ".edu", ".cn", ".hk", ".tw", ".biz",
			".info", ".mobi", ".name", ".sh", ".ac", ".travel", ".tm", ".us",
			".cc", ".mn", ".asia", ".ws", ".tel", ".cm" };
	
	/**
	 * <pre>
	 * 协议列表
	 * 可以根据实际情况扩展
	 * </pre>
	 */
	protected static String[] protocols = { "http", "https", "ftp", "svn", "udp"
			,"thunder", "flashget", "ed2k","ssh" };

	/**
	 * 获取顶级域名
	 * 
	 * @param url
	 *            完整的URL地址
	 * @return 顶级域，比如 .emarbox.com
	 */
	public synchronized static String getTopDomain(final String url) {

		return getSubDomain(url, 1);
	}

	/**
	 * 获取子域名
	 * 
	 * @param url
	 *            完整的URL地址
	 * @param level
	 *            域名级别，比如 二级域名 test.emarbox.com ，则此处的值传入 2
	 * @return 二级域，比如 .test.emarbox.com
	 */
	public synchronized static String getSubDomain(final String url, int level) {

		String domain = url;
		domain = getDomain(url);

		if (null == domain) {
			return domain;
		}
		
		// 如果是ip，则直接返回
		if (isIp(domain)) {
			return replaceIpV6(domain);
		} else {
			// 去掉域名中的端口号
			int endPos = domain.lastIndexOf(":");
			if (endPos > -1) {
				domain = domain.substring(0, endPos);
			}
		}

		// 去掉域名中的端口号
		// domain = domain.replaceAll(":[0-9]+$", "");

		// url 中的根域名
		String rootDomain = "";
		for (int i = 0; i < rootDomains.length; i++) {
			if (domain.endsWith(rootDomains[i])) {
				domain = domain.replace(rootDomains[i], "");
				rootDomain = rootDomains[i];
				break;
			}
		}

		if (level <= 1) {
			int endPos = domain.lastIndexOf(".");
			if (endPos > -1) {
				domain = domain.substring(endPos + 1, domain.length());
			}

			domain = "." + domain + rootDomain;

		} else {

			// 把域名各个层次用 . 分隔开
			String[] subParts = domain.split("[.]");

			if (subParts.length > 0 && subParts.length >= level) {
				int stopPos = 0;
				// 找出指定级别域名的位置
				for (int i = 0; i < subParts.length; i++) {
					if (i == subParts.length - level) {
						stopPos = i;
						break;
					}
				}

				StringBuffer tempDomain = new StringBuffer();
				// 拼接结果值
				for (int i = 0; i < subParts.length; i++) {
					if (i >= stopPos) {
						tempDomain.append(".").append(subParts[i]);
					}
				}

				domain = tempDomain + rootDomain;

				// 如果指定的子域名级别不存在，则返回顶级域名
			} else {

				int endPos = domain.lastIndexOf(".");
				if (endPos > -1) {
					domain = domain.substring(endPos + 1, domain.length());
				}

				domain = "." + domain + rootDomain;
			}

		}

		return domain;
	}

	/**
	 * 判断 domain 是否是IP
	 * 
	 * 支持 IPV4 和 IPV6 的 粗略判断
	 * 
	 * @param domain  域名
	 * @return 是 ip 返回 true 否则 false
	 */
	public synchronized static Boolean isIp(final String domain) {
		String result = replaceIpV6(domain);

		if (null != result) {
			try {
				// 正则替换
				// result = result.replaceAll("[:|.]", "");

				// 替换ip v4 v6 中的分隔符
				result = result.replace(".", "");
				result = result.replace(":", "");

				hex2Byte(result);
				// byte[] ipVal = hex2Byte(result);
				// new BigInteger(1,ipVal);
			} catch (Exception e) {
				return false;
			}

		}

		return true;

	}

	/**
	 * 获取 url 中的域名部分
	 * 
	 * @param url
	 * @return 域名字符串
	 */
	public synchronized static String getDomain(final String url) {
		String domain = url;
		String tmpDomain = "";

		try {

			if (null != domain) {
				domain = domain.trim().toLowerCase();
				domain = domain.replace(" ", "");
				// http:www.test.com 此种格式不会导致 MalformedURLException ，因此需要单独处理
				tmpDomain = domain;

				URL a = new URL(domain);
				
				//getHost 可以识别出 RFC 2732 中定义的 ipv6 格式的 ip 地址
				domain = a.getHost();

				if (null == domain || "".equals(domain)) {
					domain = tmpDomain;
					throw new Exception(
							"Bad value from URL.getHost(), use paramter: "
									+ url);
				}

			} else {
				return domain;
			}

		} catch (Exception e) {
			
			//取域名，实现 getHost 功能
			domain = replaceProtocol(domain);
			int endPos = domain.indexOf("/");
			if (endPos > -1) {
				domain = domain.substring(0, endPos);
			}
			
			///////////去掉域名或IP中的端口号
			// 如果是ip，则直接返回
			if (isIp(domain)) {
				domain = replaceIpV6(domain);
				
				// 去掉IP 或 域名中的端口号
				endPos = domain.lastIndexOf(":");
				if (endPos > -1) {
					String portStr = domain.substring( endPos+ 1, domain.length());
					
					try{
						if(portStr.length() > 0 && portStr.length() <= 5){
							int port = Integer.valueOf(portStr);
							
							if( port > 0 &&  port < 65535){
								domain = domain.substring(0, endPos);
							}
						}
						
					}catch(Exception ex){
						
					}
				}
			}

			// log.error("Parse domain name from [" + url + "] failed: " +
			// ExceptionUtils.getRootCauseMessage(e));
		}

		return domain;
	}

	/**
	 * 去掉域名中的协议字符串，比如 http://
	 * 
	 * @param domain
	 * @return 去掉域名中的协议字符串
	 */
	private synchronized static String replaceProtocol(String domain) {

		if (null == domain) {
			return domain;
		}
		// domain = domain.replace("http://", "");
		for (int i = 0; i < protocols.length; i++) {

			domain = domain.replaceAll("^" + protocols[i] + ":[/]*", "");
		}
		// 去掉协议
		// domain = domain.replaceAll("^\\a-zA-Z*:[/]*", "");

		// 去掉用户名和密码

		// ftp://username@hostname/
		// ftp://username:password@hostname/
		domain = domain.replaceAll("^\\w+:\\w*@", "");

		return domain;
	}

	

	/**
	 * 替换ipv6中有端口时，ip两端的中括号
	 * 
	 * <pre>
	 * 比如： https://[2001:db8:85a3:8d3:1319:8a2e:370:7348]:443/
	 * </pre>
	 * 
	 * @param domain
	 * @return 替换中括号后IPV6
	 */
	private synchronized static String replaceIpV6(String domain) {

		if(domain.indexOf("[") > -1 && domain.lastIndexOf("]") > -1){
			
			domain = domain.replace("[", "");
			domain = domain.replace("]", "");
		}
		
		
		return domain;
	}
	
	/**
	 * 16进制转车字节
	 * 
	 * @param hexStr
	 *            16进制字符串
	 * @return byte array
	 */
	private synchronized static byte[] hex2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte result[] = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}

		return result;
	}


	private synchronized static void test() {

		System.out.println("--------- DomainUtil testcase by zhaidw: ---------");
		
		String[] urls = { 
				"http://www.emar.com.cn", 
				"http:www.jiucai.org",
				"adwiser.emarbox.com", 
				"https://www.emar.com.hk/test/test.htm",
				"thunder:a5.a4.a3.a2.emar.com.cn/index.jsp",
				"ftp://upload.test-test.org",
				"ftp://username:password@f.jiucai.org:21/upload",
				"ftp://username@upload.jiucai.org/upload",
				"0:0:0:FFFF:129.144.52.38/index.jsp",
				"http://1080::8:800::::",
				"https://[2001:db8:85a3:8d3:1319:8a2e:370:7348]:443/",
				"[2001:db8:85a3:8d3:1319:::]:443",
				"ftp://username:password@[2001:db8:85a3:8d3:1319:8a2e:370:7348]:21/upload"

		};

		for (int i = 0; i < urls.length; i++) {
			System.out.println("url[" + (i + 1) + "]: " + urls[i]);
			System.out.println("TopDomain: " + DomainUtil.getTopDomain(urls[i]));
			System.out.println("Level 2 SubDomain: "+ DomainUtil.getSubDomain(urls[i], 2));
		}

	}

	public static void main(String[] args) {

		long start = System.nanoTime();
		long start2 = System.currentTimeMillis();

		DomainUtil.test();
		
		
//		String url= "::FFFF:129.144.52.38/index.jsp";
//		System.out.println("url: " + url);
//		System.out.println("TopDomain: " + DomainUtil.getTopDomain(url));
//		System.out.println("Level 2 SubDomain: "+ DomainUtil.getSubDomain(url, 2));
		

		System.out.println("--------------------------------");

		System.out.println("nanoTime: " + (System.nanoTime() - start) / 100000
				+ " ms");
		System.out.println("currentTimeMillis: "
				+ (System.currentTimeMillis() - start2) / 100000 + " ms");

	}
}
