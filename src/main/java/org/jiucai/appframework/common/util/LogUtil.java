package org.jiucai.appframework.common.util;

import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

/**
 * 根据需要返回不同实现的 包装类
 * 
 * @author zhaidw
 * 
 */
public class LogUtil {

	/**
	 * 获得日志类
	 * 
	 * @param clazz 类
	 * @return 返回 使用 org.apache.commons.logging.Log 实现的日志类
	 */
	public static Logs getLog(Class<?> clazz) {
		return new Logs(LogFactory.getLog(clazz));
	}

	/**
	 * 获得日志类
	 * 
	 * @param name 类名称
	 * @return 使用 org.apache.commons.logging.Log 实现的日志类
	 */
	public static Logs getLog(String name) {
		return new Logs(LogFactory.getLog(name));
	}

	/**
	 * 获得日志类
	 * 
	 * @param clazz 类
	 * @return 使用 org.slf4j.Logger 实现的日志类
	 */
	public static Logs getLogger(Class<?> clazz) {
		return new Logs(LoggerFactory.getLogger(clazz));
	}

	/**
	 * 获得日志类
	 * 
	 * @param name 类名称
	 * @return 使用 org.slf4j.Logger 实现的日志类
	 */
	public static Logs getLogger(String name) {
		return new Logs(LoggerFactory.getLogger(name));
	}

}
