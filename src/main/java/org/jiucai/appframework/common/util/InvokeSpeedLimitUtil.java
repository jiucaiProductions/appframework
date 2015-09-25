/**
 *
 */
package org.jiucai.appframework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service call speed limit client util
 *
 * @author wangbo
 * @author jiucai
 *
 */
public class InvokeSpeedLimitUtil {
    protected static Logger log = LoggerFactory.getLogger(InvokeSpeedLimitUtil.class);

    /** 速度限制每个时间周期内的调用次数 */
    protected static int speedLimitCount = 0;

    /** 速度限制的时间周期,单位秒 */
    protected static long speedLimitInterval = 0;

    /** 等待周期,单位秒 */
    protected static int sleepTimeInSeconds = 10;

    private static int executedCount = 0;

    private static long lastRunTime = 0;

    static {
        speedLimitCount = 22;
        speedLimitInterval = 60000;
    }

    /**
     * 限制调用速度
     *
     * @param updateCount
     *            传入本次调用的条数
     */
    public static synchronized void limitSpeed(int updateCount) {
        // 如果没有配置速度限制，直接返回
        if (speedLimitCount <= 0 || speedLimitInterval <= 0) {
            return;
        }
        // 更新计数
        executedCount += updateCount;

        long nowTime = System.currentTimeMillis();
        if (lastRunTime == 0L) {
            lastRunTime = nowTime;
        }
        // 检查速度是否超过限制
        while (checkOverLimit(nowTime)) {
            try {
                log.info("调用速度已达到 " + speedLimitCount + " 次/" + (speedLimitInterval) / 1000
                        + " 秒的限制,在 " + (nowTime - lastRunTime) / 1000 + " 秒内已调用 " + executedCount
                        + " 次,暂停" + sleepTimeInSeconds + "秒 ...");
                Thread.sleep(sleepTimeInSeconds * 1000);
            } catch (InterruptedException e) {
                log.warn("接受到线程退出信号,退出");
                return;
            }
            nowTime = System.currentTimeMillis();
        }

    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            System.out.println("第 " + i + " 次调用开始");
            InvokeSpeedLimitUtil.limitSpeed(50);
            System.out.println("第 " + i + " 次调用结束");
        }
    }

    /**
     * 设置限制参数
     *
     * @param limitCount
     *            调用次数
     * @param limitInterval
     *            调用周期,单位秒
     * @param waitTimeInSeconds
     *            等待周期,单位秒
     */
    public static void setLimit(int limitCount, int limitInterval, int waitTimeInSeconds) {
        if (limitCount < 0 || limitInterval < 0) {
            throw new RuntimeException("param can not be null.");
        }
        speedLimitCount = limitCount;
        speedLimitInterval = limitInterval * 1000;
        sleepTimeInSeconds = waitTimeInSeconds;

    }

    private static boolean checkOverLimit(long nowTime) {
        long timedurion = nowTime - lastRunTime;
        // 如果时间超过60秒，且执行数大于限制数
        if (timedurion < speedLimitInterval) {
            if (executedCount > speedLimitCount) {
                return true;
            }
        } else {
            // 每一个周期，重置一次计数器
            lastRunTime = nowTime;
            executedCount = 0;
        }
        return false;
    }

}
