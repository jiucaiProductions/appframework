package org.jiucai.appframework.base.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.util.AppContextHolder;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AppDefaultInterceptor extends HandlerInterceptorAdapter {


	protected Logs log = LogUtil.getLog(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		log.debug("preHandle entered..." + handler);

		AppContextHolder.setRequest(request);
		AppContextHolder.setResponse(response);
		
		return true;

	}


}
