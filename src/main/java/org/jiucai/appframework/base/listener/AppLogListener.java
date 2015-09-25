package org.jiucai.appframework.base.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.jiucai.appframework.base.util.IpUtil;
import org.slf4j.MDC;

/**
 * a listener to save user ip to log4j , you can override it
 *
 * @author jiucai
 *
 */
@WebListener("a listener to save user ip to log4j ")
public class AppLogListener extends AbstractBaseListener implements ServletRequestListener {

    // protected MDCAdapter adapter;

    public AppLogListener() {
        super();
        log.info("AppLogListener inited.");
        // adapter = new LogbackMDCAdapter();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {

        MDC.remove("ip");

    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

        String ip = IpUtil.getIp(request);

        MDC.put("ip", ip);

    }

}
