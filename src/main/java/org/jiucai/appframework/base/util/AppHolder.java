package org.jiucai.appframework.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 应用的 ThreadLocal
 * 
 * @author zhaidw
 * @deprecated since 1.1.4 renamed to {@link org.jiucai.appframework.base.util.RequestHolder}
 */
@Deprecated
public class AppHolder {

	public static HttpServletRequest getRequest() {
		return RequestHolder.getRequest();
	}

	public static HttpSession getSession() {
		
		return RequestHolder.getSession();
	}

	public static void setRequest(HttpServletRequest request) {
		RequestHolder.setRequest(request);
	}


	public static ThreadLocal<HttpServletRequest> getRequestHolder() {
		return RequestHolder.getRequestHolder();
	}

}
