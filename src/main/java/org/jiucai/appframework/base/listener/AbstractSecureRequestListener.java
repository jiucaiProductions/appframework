package org.jiucai.appframework.base.listener;

import java.util.Enumeration;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/***
 * 请求监听器，对 request 所有参数进行安全处理
 *
 * @author zhaidw
 *
 */
public abstract class AbstractSecureRequestListener extends AbstractRequestListener {

    public AbstractSecureRequestListener() {
        super();
        log.info("AbstractSecureRequestListener inited.");

    }

    /**
     * 应用可自行实现的扩展方法
     * 
     * @param request
     *            HttpServletRequest
     */
    public abstract void doSecureRequest(HttpServletRequest request);

    /**
     * 放在 reqesut attribute 中的 parameter 的前缀
     * 
     * @return String
     */
    public abstract String getReqPrefix();

    @Override
    public void requestDestroyed(ServletRequestEvent event) {

        super.requestDestroyed(event);

    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {

        super.requestInitialized(event);

        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

        trimRequestParam(request);
        doSecureRequest(request);

    }

    /**
     * 过滤请求参数中的左右空格
     * 
     * @param request
     *            HttpServletRequest
     */
    protected synchronized void trimRequestParam(HttpServletRequest request) {

        Enumeration<String> paramNames = request.getParameterNames();

        if (null != paramNames) {
            String paramName;
            String[] paramValues;
            while (paramNames.hasMoreElements()) {
                paramName = paramNames.nextElement();

                paramValues = request.getParameterValues(paramName);

                if (null != paramValues && paramValues.length > 0) {

                    if (paramValues.length > 1) {

                        StringBuffer b = new StringBuffer();
                        for (int i = 0; i < paramValues.length; i++) {
                            String tempVal = StringUtils.isBlank(paramValues[i]) ? ""
                                    : paramValues[i].trim();
                            b.append(tempVal);
                            if (i != paramValues.length - 1) {
                                b.append(",");
                            }
                        }
                        request.setAttribute(getReqPrefix() + paramName, b.toString());
                    } else {
                        if (StringUtils.isNotBlank(paramValues[0])) {
                            request.setAttribute(getReqPrefix() + paramName, paramValues[0].trim());
                        } else {
                            request.setAttribute(getReqPrefix() + paramName, "");
                        }

                    }

                }

            }
        }

    }

}
