package org.jiucai.appframework.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.helper.AppContextHolder;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * AppContextInterceptor
 * @author jiucai
 *
 */
public class AppContextInterceptor extends HandlerInterceptorAdapter {

	protected Logs log = LogUtil.getLog(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		log.debug("preHandle entered..." + handler);

		AppContextHolder.setRequest(request);
		AppContextHolder.setResponse(response);

		return true;

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("afterCompletion entered..." + handler);

		AppContextHolder.removeRequest();
		AppContextHolder.removeResponse();
	}

}
