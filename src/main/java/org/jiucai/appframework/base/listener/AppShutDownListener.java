/**
 * 
 */
package org.jiucai.appframework.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * ServletContextShutDownListener
 * @author zhaidw
 *
 */
public class AppShutDownListener implements ServletContextListener {

	protected static Logs logger = LogUtil.getLog(AppShutDownListener.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		logger.info("接收到停止信号,停止所有线程池...");
		
		AppExecutorServiceFactory.shutdown();
		
		logger.info("停止所有线程池成功.");

	}

}
