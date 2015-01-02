package org.jiucai.appframework.base.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用认证接口
 *
 * @author zhaidw
 *
 */
public interface AuthPlugin {

    /**
     * 实现本方法来进行应用之间认证，认证失败抛出异常
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @throws AuthException
     *             AuthException
     */
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws AuthException;
}
