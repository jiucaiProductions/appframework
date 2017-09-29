package org.jiucai.appframework.base.service.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.util.ConfigUtil;
import org.jiucai.appframework.base.web.BaseController;
import org.jiucai.appframework.base.web.render.JsonRender;
import org.jiucai.appframework.base.web.render.XmlRender;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DefaultAppBaseService extends AbstractBaseService {

	/**
	 * xml 或 json 响应的默认编码
	 */
	protected static String encoding = BaseController.CHARSET;

	/**
	 * 配置项读取类
	 */
	protected static Configuration config;

	static {
		config = ConfigUtil.addConfig("config");
		config = ConfigUtil.addConfig("mail");

	}

	public static Configuration getConfig() {
		return config;
	}

	/**
	 * XML 字符串生成器
	 */
	@Autowired
	private XmlRender xmlRender;

	/**
	 * json 字符串生成器
	 */
	@Autowired
	private JsonRender jsonRender;

	@Override
	public String getContentType() {
		return getJsonRender().getContentType();
	}

	/**
	 * 返回转换 json 工具对象
	 *
	 * @return JsonRender
	 */
	public JsonRender getJsonRender() {
		jsonRender.setEncoding(encoding);

		return jsonRender;
	}

	/**
	 * 返回转换 xml 的工具对象
	 *
	 * @return XmlRender
	 */
	public XmlRender getXmlRender() {
		xmlRender.setEncoding(encoding);
		return xmlRender;
	}

	public void output(HttpServletResponse response, String msg, String contentType) {
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

	/**
	 * 返回 json 格式的成功或错误信息
	 *
	 * @param isSuccess
	 *            true 表示成功消息 false 表示错误消息
	 * @param msg
	 *            string which wants to convert to json
	 * @return json string
	 */
	protected String getJsonMsg(Boolean isSuccess, String msg) {
		StringBuffer result = new StringBuffer("");

		if (isSuccess) {
			if (StringUtils.isBlank(msg)) {
				msg = "ok";
			}
			result.append("{\"success\":\"").append(msg).append("\"}");
		} else {
			if (StringUtils.isBlank(msg)) {
				msg = "failed";
			}
			result.append("{\"error\":\"").append(msg).append("\"}");
		}

		return result.toString();
	}

	/**
	 * 返回 chartXML 格式的成功或错误信息
	 *
	 * @param isSuccess
	 *            true 表示成功消息 false 表示错误消息
	 * @param msg
	 *            tring which wants to convert to xml
	 * @return xml string
	 */
	protected String getXmlMsg(Boolean isSuccess, String msg) {

		StringBuffer result = new StringBuffer("");

		if (isSuccess) {
			if (StringUtils.isBlank(msg)) {
				msg = "ok";
			}
			result.append("<chart><success>").append(msg).append("</success></chart>");
		} else {
			if (StringUtils.isBlank(msg)) {
				msg = "failed";
			}
			result.append("<chart><error>").append(msg).append("</error></chart>");
		}

		return result.toString();
	}

}
