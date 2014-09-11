package org.jiucai.appframework.base.web;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/**
 * 控制器的基类
 * 
 * @author zhaidw
 * 
 */
public abstract class BaseController {


	protected static ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	protected Logs log = LogUtil.getLog(getClass());

	/**
	 * 操作成功标识
	 */
	public static final String SUCCESS = "1";

	/**
	 * 操作失败标识
	 */
	public static final String FAILED = "0";

	/**
	 * 直接输出时的字符编码
	 */
	public static final String CHARSET = "UTF-8";
	
	
	protected void output(HttpServletResponse response, String msg,
			String contentType) {
		PrintWriter out = null;
		try {
			// 必须放在 response.getWriter(); 之前否则不起作用
			response.setHeader("Content-Type", contentType);
			response.setHeader("Pragma", "no-cache");

			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");

			response.setDateHeader("Expires", 0);

			out = response.getWriter();

			if (null != out) {
				if (null == msg) {
					msg = "";
				}
				out.write(msg);
			}

		} catch (Exception e) {
			log.error("output failed: " + ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (null != out) {
				out.close();
			}

		}

	}

	protected static File getFileFromClasspath(String classpathFileName) {

		String cp = new StringBuffer(classpathFileName).toString();
		URL url = loader.getResource(cp);

		File file = new File(url.getFile());

		return file;

	}


}
