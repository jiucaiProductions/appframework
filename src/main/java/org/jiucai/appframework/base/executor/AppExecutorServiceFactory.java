package org.jiucai.appframework.base.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池服务
 *
 * @author zhaidw
 *
 */
public class AppExecutorServiceFactory {

    protected static Logger logger = LoggerFactory.getLogger(AppExecutorServiceFactory.class);

    private volatile static AppExecutorServiceFactory uniqueInstance;

    private volatile static List<ExecutorService> threadPoolList;

    private volatile static List<AppExecutorService> commandList;

    public static Boolean add(ExecutorService service) {
        return getInstance().getThreadPoolList().add(service);
    }

    public static Boolean addExecutorCommand(AppExecutorService cmd) {
        return getInstance().getCommandList().add(cmd);
    }

    public static AppExecutorServiceFactory getInstance() {
        if (null == uniqueInstance) {
            synchronized (AppExecutorServiceFactory.class) {
                if (null == uniqueInstance) {
                    uniqueInstance = new AppExecutorServiceFactory();
                    threadPoolList = new ArrayList<ExecutorService>();
                    commandList = new ArrayList<AppExecutorService>();
                }
            }
        }

        return uniqueInstance;
    }

    public static Boolean remove(ExecutorService service) {
        return getInstance().getThreadPoolList().remove(service);
    }

    public static Boolean removeExecutorCommand(AppExecutorService cmd) {
        return getInstance().getCommandList().remove(cmd);
    }

    public static void shutdown() {

        for (AppExecutorService s : getInstance().getCommandList()) {
            if (null != s) {
                logger.info("try to shutdown ExecutorService from command: "
                        + s.getClass().getName());
                s.shutdown();
                logger.info("shutdown ExecutorService: " + s.getName());
            }
        }

        for (ExecutorService s : getInstance().getThreadPoolList()) {
            if (null != s) {
                logger.info("try to shutdown ExecutorService " + s);
                s.shutdown();
                logger.info("shutdown ExecutorService: " + s);
            }
        }

    }

    private AppExecutorServiceFactory() {
    }

    public List<AppExecutorService> getCommandList() {
        return commandList;
    }

    public List<ExecutorService> getThreadPoolList() {
        return threadPoolList;
    }

}
