package org.jiucai.appframework.base.executor;

import java.util.concurrent.ArrayBlockingQueue;
/**
 * 自定义队列,支持,线程池满时,等待而非报错
 * @author wangbo
 *
 * @param <E> 放入队列的实例
 */
public class AppExecutorServiceQueue<E> extends ArrayBlockingQueue<E> {
    /**long serialVersionUID */
	private static final long serialVersionUID = -4807012422065044753L;
	//private final long timeoutMS;

    public AppExecutorServiceQueue(int capacity) {
        super(capacity);
        //this.timeoutMS = timeoutMS;
    }

    @Override
    public boolean offer(E e) {
        try {
        	super.put(e);
        	return true;
            //return super.offer(e, timeoutMS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
