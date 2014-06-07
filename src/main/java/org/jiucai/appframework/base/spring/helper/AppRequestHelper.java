package org.jiucai.appframework.base.spring.helper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

public class AppRequestHelper extends SpringHelper {
	
	protected static Logs log = LogUtil.getLog(AppRequestHelper.class);
	
	
	public static final String REQ_PREFIX = "__req_app_";
	
	/**
	 * 解析请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static synchronized Map<String, Object> parseRequest(
			HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		Object reqAttr = null;
		String attrName, paraName;

		@SuppressWarnings("rawtypes")
		Enumeration attrs = request.getAttributeNames();

		while (attrs.hasMoreElements()) {
			attrName = (String) attrs.nextElement();

			if (null != attrName && attrName.startsWith(REQ_PREFIX)) {

				paraName = attrName.substring(
						attrName.indexOf(REQ_PREFIX)
								+ REQ_PREFIX.length(),
						attrName.length());

				reqAttr = request.getAttribute(attrName);

				param.put(paraName, reqAttr);

			}
		}
		return param;

	}
	
	
	/**
	 * 替换掉字符串开头的域名字符串
	 * @param val
	 * @return
	 */
	public static String replaceDomain(final String val){
		String result = val;
		
		if(StringUtils.isBlank(result)){
			return "";
		}
		
		int pos = -1;
		
		result = result.toLowerCase();

			
		if( result.startsWith("http://") || result.startsWith("https://") ){
			pos = result.indexOf("/",8);
			if( pos > -1){
				result = result.substring(pos);
			}
			
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		String val="http://www.w3school.com.cn/js/jsref_substr.asp";
		
		System.out.println(replaceDomain(val));
	}
	
}
