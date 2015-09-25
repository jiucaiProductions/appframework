package org.jiucai.appframework.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiucai.appframework.base.helper.AppContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * AppContextInterceptor
 *
 * @author jiucai
 *
 */
public class AppContextInterceptor extends HandlerInterceptorAdapter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion entered..." + handler);

        AppContextHolder.removeRequest();
        AppContextHolder.removeResponse();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        log.debug("preHandle entered..." + handler);

        AppContextHolder.setRequest(request);
        AppContextHolder.setResponse(response);

        return true;

    }

}
