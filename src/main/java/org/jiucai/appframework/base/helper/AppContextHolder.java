package org.jiucai.appframework.base.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AppContextHolder {

	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return requestHolder.get();
	}

	public static HttpServletResponse getResponse() {
		return responseHolder.get();
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

	public static void setResponse(HttpServletResponse response) {
		responseHolder.set(response);
	}

	public static void removeRequest() {

		requestHolder.remove();

	}

	public static void removeResponse() {

		responseHolder.remove();

	}

	public static ThreadLocal<HttpServletRequest> getRequestHolder() {
		return requestHolder;
	}

	public static ThreadLocal<HttpServletResponse> getResponseHolder() {
		return responseHolder;
	}

}
