package org.jiucai.appframework.base.executor.util;

import java.util.concurrent.ExecutorService;

import org.jiucai.appframework.base.executor.AppExecutorService;
import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.jiucai.appframework.base.executor.impl.DefaultExecutorCommand;
import org.jiucai.appframework.base.executor.impl.DefaultExecutorService;

/**
 * ExecutorService 获取工具类
 * @author jiucai
 *
 */
public class ExecutorServiceUtil {
	
	static{
		AppExecutorService cmd = new DefaultExecutorCommand(DefaultExecutorService.getInstance());
		AppExecutorServiceFactory.addExecutorCommand(cmd);
	}
	
	public static ExecutorService getExecutorService(){
		return DefaultExecutorService.getExecutorService();
	}
	
	public static ExecutorService getExecutorService(final String name, final int maxThreads){
		DefaultExecutorService.setParam(name, maxThreads);
		return DefaultExecutorService.getExecutorService();
	}
	
	public static void shutdown(){
		AppExecutorServiceFactory.shutdown();
	}
	
	
}
