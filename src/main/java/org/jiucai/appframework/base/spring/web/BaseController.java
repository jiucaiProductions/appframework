package org.jiucai.appframework.base.spring.web;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * 控制器的基类
 * 
 * @author zhaidw
 * 
 */
public abstract class BaseController {

	protected Logs log = LogUtil.getLog(getClass());

	/**
	 * 操作成功标识
	 */
	protected static final String SUCCESS = "1";

	/**
	 * 操作失败标识
	 */
	protected static final String FAILED = "0";

	/**
	 * 直接输出时的字符编码
	 */
	protected static final String CHARSET = "UTF-8";


}
