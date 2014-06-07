package org.jiucai.appframework.base.executor;

/**
 * Executor 命令接口，主要实现关闭线程池功能
 * @author zhaidw
 *
 */
public interface AppExecutorService {

	/**
	 * 返回线程池名称
	 * @return 线程池名称
	 */
	public String getName();
	
	/**
	 * 关闭线程池
	 */
	public void shutdown();
}
