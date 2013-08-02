package org.jiucai.appframework.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 应用的 HttpServletRequest ThreadLocal
 * 
 * @author zhaidw
 * 
 */
public class RequestHolder {

	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public static HttpSession getSession() {
		HttpSession session = null;
		HttpServletRequest request = requestHolder.get();

		if (null != request) {
			session = request.getSession(true);
		}

		return session;
	}

	public static void setRequest(HttpServletRequest request) {
		requestHolder.set(request);
	}

	public static ThreadLocal<HttpServletRequest> getRequestHolder() {
		return requestHolder;
	}

}
