package org.jiucai.appframework.base.executor.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * 默认 线程池
 * @author zhaidw
 *
 */
public class DefaultExecutorService {

	protected static Logs logger = LogUtil.getLog(DefaultExecutorService.class);
	
	//线程池名称
	private static String name ="默认";
	
	//线程池大小
	private static int maxThreads = 50;
	
	private volatile static DefaultExecutorService uniqueInstance;
	
	private volatile static ExecutorService executorService;
	

	
	private DefaultExecutorService() {
	}
	
	/**
	 * 获取 DefaultExecutorService 单例对象
	 * @return DefaultExecutorService 单例对象
	 */
	public static DefaultExecutorService getInstance() {
		if (null == uniqueInstance) {
			synchronized (DefaultExecutorService.class) {
				if (null == uniqueInstance) {
					
					uniqueInstance = new DefaultExecutorService();
				}
			}
		}

		return uniqueInstance;
	}
	
	/**
	 * 获取 ExecutorService 单例对象
	 * @return ExecutorService 单例对象
	 */
	public ExecutorService getExecutorService() {
		if (null == executorService) {
			synchronized (DefaultExecutorService.class) {
				if (null == executorService) {
					
					logger.info("使用 " + maxThreads + " 个线程初始化 " + name + " 线程池" );

					executorService = new ThreadPoolExecutor(1, // 主线程数
							maxThreads, // 最大线程数
							1, // 等待时间
							TimeUnit.SECONDS,// 时间单位
							new ArrayBlockingQueue<Runnable>(1),// 队列数，为防止线程启动问题，统一修改为1
							new ThreadPoolExecutor.CallerRunsPolicy());// 超出队列后的操作（队列满时在当前线程执行任务）
					
					
					//注册到全局服务类
					AppExecutorServiceFactory.add(executorService);
				}
			}
		}

		return executorService;
	}
	
	/**
	 * 设置线程池参数
	 * @param name 线程池名称
	 * @param maxThreads 最大线程数
	 * @return DefaultExecutorService 单例对象
	 */
	public static DefaultExecutorService setParam(final String name, final int maxThreads) {
		DefaultExecutorService.name = name;
		DefaultExecutorService.maxThreads = maxThreads;
		return getInstance() ;
	}
	

	public String getName() {
		return name;
	}

	/**
	 * 关闭 下载数据生成 线程池
	 */
	public void shutdown() {
		logger.info("尝试停止线程池 " + name + " ... ");
		
		if (executorService != null) {
			executorService.shutdownNow();
			logger.info("成功停止线程池 " + name);
		}
	}

}