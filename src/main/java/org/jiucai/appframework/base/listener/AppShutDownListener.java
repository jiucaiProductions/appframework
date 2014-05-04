/**
 * 
 */
package org.jiucai.appframework.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;

/**
 * ServletContextShutDownListener
 * @author zhaidw
 *
 */
public class AppShutDownListener extends AbstractBaseListener implements ServletContextListener {


	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("AppShutDownListener inited.");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		log.info("接收到停止信号,停止所有线程池...");
		
		AppExecutorServiceFactory.shutdown();
		
		log.info("停止所有线程池成功.");

	}

}
