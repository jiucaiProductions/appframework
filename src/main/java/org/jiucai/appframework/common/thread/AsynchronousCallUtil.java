package org.jiucai.appframework.common.thread;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.helper.AppRequestHolder;
import org.jiucai.appframework.common.util.BaseUtil;

/**
 * 任务并行调用服务
 *
 * @author wangbo 2012-09-29
 * @version 1.0
 */
public class AsynchronousCallUtil extends BaseUtil {

    /**
     * 获得任务的返回值，List中的值按提交任务的顺序返回
     * 
     * @return List
     * @throws Exception
     *             Exception
     */
    static public List<Object> get() throws Exception {
        try {
            List<Object> res = threadLocal.get().getInter();
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            threadLocal.remove();
        }
    }

    /**
     * 提交并执行任务
     * 
     * @param instance
     *            要执行的类实例
     * @param methName
     *            执行方法名
     * @param args
     *            原方法的参数，支持多个参数， 要调用的方法参数逗号分隔原样填写即可
     * @throws Exception
     *             Exception
     */
    static public void submit(Object instance, String methName, Object... args) throws Exception {
        threadLocal.get().submitInter(instance, methName, args);
    }

    static private final ThreadLocal<AsynchronousCallUtil> threadLocal = new ThreadLocal<AsynchronousCallUtil>() {
        @Override
        protected AsynchronousCallUtil initialValue() {
            return new AsynchronousCallUtil();
        }
    };

    private int poolsize = 16;

    private Map<String, Method> modelMap;

    private List<Future<Object>> taskList;

    static private ThreadPoolExecutor threadPool;

    private AsynchronousCallUtil() {
        super();
        init();
    }

    public void setMaxThreadPoolSize(int size) {
        threadLocal.get().poolsize = size;
    }

    private List<Object> getInter() throws Exception {
        // 为强制启动最后一个提交的线程，提交一个伪任务
        try {
            threadPool.submit(new Thread());
            List<Object> res = new ArrayList<Object>();
            for (Future<Object> item : taskList) {
                try {
                    res.add(item.get());
                } catch (Exception e) {
                    throw new Exception("thread execute failed : "
                            + ExceptionUtils.getFullStackTrace(e));
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            taskList.clear();
        }

    }

    private void init() {
        modelMap = new HashMap<String, Method>();
        taskList = new ArrayList<Future<Object>>();
        if (threadPool == null) {
            synchronized (threadLocal) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(0, poolsize, 1000L, TimeUnit.MILLISECONDS,
                            new ArrayBlockingQueue<Runnable>(1), new TaskThreadFactory());
                    threadPool
                            .setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                }
                threadLocal.notifyAll();
            }
        }
    }

    private void submitInter(Object instance, String methName, Object... args) throws Exception {
        Class<? extends Object> className = instance.getClass();
        Method method = modelMap.get(className.getName());
        if (method == null) {
            for (Method item : className.getMethods()) {
                if (item.getName().equalsIgnoreCase(methName)) {
                    method = item;
                    modelMap.put(methName, method);
                    break;
                }
            }
        }
        if (method == null) {
            throw new Exception("Can not find method [" + methName + "] in class:" + className);
        }
        Future<Object> resItem = threadPool.submit(new AsynchronousTask(method, instance, args));
        taskList.add(resItem);
    }

}

class AsynchronousTask implements Callable<Object> {

    private Method method;

    private Object instance;

    private Object[] args;

    private HttpServletRequest request;// 保存调用线程的request上下文引用 by wangbo
                                       // 2013-01-29

    public AsynchronousTask(Method method, Object instance, Object... args) {
        super();
        this.method = method;
        this.instance = instance;
        this.args = args;
        request = AppRequestHolder.getRequest();// 获取调用线程的上下文
    }

    @Override
    public Object call() throws Exception {
        AppRequestHolder.setRequest(request);// 设置当前上下文
        return method.invoke(instance, args);
    }

}

class TaskThreadFactory implements ThreadFactory {
    static final AtomicInteger poolNumber = new AtomicInteger(1);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;

    public TaskThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}