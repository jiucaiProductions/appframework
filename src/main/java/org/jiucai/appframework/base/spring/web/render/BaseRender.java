package org.jiucai.appframework.base.spring.web.render;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Render的基类，实现共有的 output方法
 * @author zhaidw
 *
 */
public abstract class BaseRender implements Render {

	@Override
	public abstract void setEncoding(String encoding);

	@Override
	public abstract String  getContentType();
	
	@Override
	public abstract String getString(Object data) throws Exception;

	@Override
	public void output(HttpServletResponse response, String msg)
			throws Exception {
		PrintWriter out = null;
		try {
			// 必须放在 response.getWriter(); 之前否则不起作用
			response.addHeader("Content-Type", getContentType());
			out = response.getWriter();
			out.write(msg);
		} finally {
			if (null != out) {
				out.close();
			}

		}

	}

}
