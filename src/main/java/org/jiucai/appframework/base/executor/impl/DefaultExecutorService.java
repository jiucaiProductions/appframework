package org.jiucai.appframework.base.executor.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认 线程池
 *
 * @author zhaidw
 *
 */
public class DefaultExecutorService {

    protected static Logger logger = LoggerFactory.getLogger(DefaultExecutorService.class);

    // 线程池名称
    private static String name = "Default";

    // 线程池大小
    private static int maxThreads = 20;

    private volatile static DefaultExecutorService uniqueInstance;

    private volatile static ExecutorService executorService;

    /**
     * 获取 ExecutorService 单例对象
     *
     * @return ExecutorService 单例对象
     */
    public static ExecutorService getExecutorService() {
        if (null == executorService) {
            synchronized (DefaultExecutorService.class) {
                if (null == executorService) {

                    logger.info("try to create executorService[name=" + name + "] with "
                            + maxThreads + " threads ...");

                    // 使用这个线程过程最大线程数时，超出的不会被执行
                    // executorService =
                    // Executors.newFixedThreadPool(maxThreads);

                    executorService = new ThreadPoolExecutor(maxThreads, // 主线程数
                            maxThreads, // 最大线程数
                            0, // 等待时间
                            TimeUnit.SECONDS,// 时间单位
                            new LinkedBlockingQueue<Runnable>(1),// 队列数，为防止线程启动问题，统一修改为1
                            new ThreadPoolExecutor.CallerRunsPolicy());// 超出队列后的操作（队列满时在当前线程执行任务）

                    // 注册到全局服务类
                    AppExecutorServiceFactory.add(executorService);

                    logger.info("executorService[name=" + name + "] with " + maxThreads
                            + " threads created");
                }
            }
        }

        return executorService;
    }

    /**
     * 获取 DefaultExecutorService 单例对象
     *
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
     * 设置线程池参数
     *
     * @param name
     *            线程池名称
     * @param maxThreads
     *            最大线程数
     * @return DefaultExecutorService 单例对象
     */
    public static DefaultExecutorService setParam(final String name, final int maxThreads) {
        DefaultExecutorService.name = name;
        DefaultExecutorService.maxThreads = maxThreads;
        return getInstance();
    }

    private DefaultExecutorService() {
    }

    public String getName() {
        return name;
    }

    /**
     * 关闭 下载数据生成 线程池
     */
    public void shutdown() {
        logger.info("try to shutdown executorService: " + name + " ... ");

        if (executorService != null) {
            executorService.shutdownNow();
            logger.info("shutdown executorService: " + name);
        }
    }

}