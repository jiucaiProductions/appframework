package org.jiucai.appframework.base.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jiucai.appframework.base.service.BaseService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 从 ServletContext 或 Bean初始化时 获取 spring 上下文
 *
 * @author zhaidw
 *
 */
public class SpringHelper extends BaseService implements ApplicationContextAware {

    protected static ApplicationContext context;

    /**
     * 返回 Bean初始化时 的 ApplicationContext
     *
     * @return ApplicationContext ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 从 ServletContext 获取 ApplicationContext
     *
     * @param request
     *            HttpServletRequest
     * @return ApplicationContext ApplicationContext
     */
    public static ApplicationContext getApplicationContext(HttpServletRequest request) {

        ServletContext servletContext = request.getSession().getServletContext();
        ApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);

        return context;

    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        log.info("ApplicationContext[" + applicationContext.getClass().getName() + "] inited: "
                + applicationContext);
    }

}
