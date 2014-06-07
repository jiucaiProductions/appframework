package org.jiucai.appframework.base.spring.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.service.BinaryService;
import org.jiucai.appframework.base.service.DataService;
import org.jiucai.appframework.base.service.DownloadService;
import org.jiucai.appframework.base.service.PageService;
import org.jiucai.appframework.base.service.UploadService;
import org.jiucai.appframework.base.spring.helper.SpringHelper;
import org.jiucai.appframework.common.util.EscapeUtil;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 应用前端统一控制器
 * 
 * <pre>
 * 页面请求 /ControllerPATH/p?sid=pageServiceId&pid=1
 * 数据请求 /ControllerPATH/d?sid=dataServiceId&pid=1
 * </pre>
 * 
 * @author zhaidw
 * 
 */
public abstract class AbstractBaseController extends BaseController {

	protected static ClassLoader loader = Thread.currentThread()
			.getContextClassLoader();

	/**
	 * binary service 404 的图片文件classpath 路径
	 */
	protected static String IMG_404 = "static/404";

	/**
	 * Request 请求中的 服务ID 参数名
	 */
	protected static String REQ_SID = "sid";

	public String get404ImageClasspath() {
		return IMG_404;
	}

	public String getRequestServiceId() {
		return REQ_SID;
	}

	/**
	 * 下载请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void upload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String resultData = null;
		String contentType = "application/json; charset=UTF-8";
		String dataType = DataService.DATA_TYPE_JSON;
		Map<String, String> reqParam = parseRequest(request);
		String serviceId = reqParam.get(getRequestServiceId());
		Boolean errFlag = false;

		if (null != serviceId) {
			UploadService service = null;
			try {
				service = SpringHelper.getBean(serviceId + "UploadService",
						UploadService.class);

				if (null != service) {
					resultData = service.handleRequest(reqParam, request);
					contentType = service.getContentType(reqParam);
				} else {
					errFlag = true;
					resultData = formatMsg(dataType, "service " + serviceId
							+ " not exists.");
				}

			} catch (Exception e) {
				log.error("upload service failed: " + ExceptionUtils.getFullStackTrace(e));
				String errMsg = ExceptionUtils.getRootCauseMessage(e);
				errFlag = true;
				resultData = formatMsg(dataType, "service " + serviceId
						+ " failed: " + errMsg);
			}

		} else {
			errFlag = true;
			resultData = formatMsg(dataType, "service id is required.");

		}

		if (errFlag) {
			log.error("upload service failed: " + resultData);
		}

		output(response, resultData, contentType);

	}

	/**
	 * 下载请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String errorMsg = "";
		Map<String, String> reqParam = parseRequest(request);
		String serviceId = reqParam.get(getRequestServiceId());
		Boolean errFlag = false;
		String fileName = null;
		Boolean isFileExists = false;

		if (null != serviceId) {
			DownloadService service = null;
			try {
				service = SpringHelper.getBean(serviceId + "DownloadService",
						DownloadService.class);

				if (null != service) {

					fileName = service.getFileName(reqParam);
					isFileExists = service.fileExists(reqParam);

					log.info("download file name:" + fileName);

					if (isFileExists && StringUtils.isNotBlank(fileName)) {

						// 下载的文件名中不能包含空格，否则浏览器无法解析
						fileName = fileName.trim().replace(" ", "");

						if (request.getHeader("User-Agent").toUpperCase()
								.indexOf("MSIE") > 0) {
							fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
						} else {
							fileName = new String(fileName.getBytes("UTF-8"),
									"ISO8859-1");// 非IE浏览器
						}

						response.setContentType(service
								.getContentType(reqParam));

						response.addHeader("Cache-Control", "must-revalidate");
						response.addHeader("Cache-Control", "no-cache");
						response.addHeader("Cache-Control", "no-store");

						response.setDateHeader("Expires", 0);

						response.setHeader("Pragma", "no-cache");
						response.setHeader("Connection", "close");

						response.setHeader("Content-Disposition",
								"attachment;filename=" + fileName);

						service.handleRequest(reqParam,
								response.getOutputStream());

					} else {
						errFlag = true;
						errorMsg = "file not exists or download file name is null.";
						response.sendError(HttpServletResponse.SC_NOT_FOUND,
								errorMsg);
						return;
					}

				} else {
					errFlag = true;
					errorMsg = "service " + serviceId + " not exists.";
				}

			} catch (Exception e) {

				log.error("download service failed: " + ExceptionUtils.getFullStackTrace(e));

				String errMsg = ExceptionUtils.getRootCauseMessage(e);
				errFlag = true;
				errorMsg = "service " + serviceId + " failed: " + errMsg;
			}

		} else {
			errFlag = true;
			errorMsg = "service id is required.";
		}

		if (errFlag) {
			log.error("download service failed: " + errorMsg);

			request.setAttribute("failed_msg", "请求的文件不存在或已经被删除。");
			// request.getRequestDispatcher("/common/error/session_failed.jsp").forward(request,
			// response);

			String path = request.getContextPath();
			String basePath = new StringBuffer(request.getScheme())
					.append("://").append(request.getServerName()).append(":")
					.append(request.getServerPort()).append(path).toString();

			response.sendRedirect(basePath + "/common/error/session_failed.jsp");
			return;
		}

	}

	/**
	 * 二进制请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void binary(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorMsg = "";
		Map<String, String> reqParam = parseRequest(request);
		String serviceId = reqParam.get(getRequestServiceId());
		Boolean errFlag = false;
		Boolean isFileExists = false;

		if (null != serviceId) {
			BinaryService service = null;
			try {
				service = SpringHelper.getBean(serviceId + "BinaryService",
						BinaryService.class);

				if (null != service) {

					isFileExists = service.fileExists(reqParam);

					if (isFileExists) {

						response.addHeader("Cache-Control", "must-revalidate");
						response.addHeader("Cache-Control", "no-cache");
						response.addHeader("Cache-Control", "no-store");

						response.setDateHeader("Expires", 0);

						response.setHeader("Pragma", "no-cache");
						response.setHeader("Connection", "close");

						service.handleRequest(reqParam,
								response.getOutputStream());

					} else {
						errFlag = true;
						errorMsg = "file not exists.";

						response.addHeader("Cache-Control", "must-revalidate");
						response.addHeader("Cache-Control", "no-cache");
						response.addHeader("Cache-Control", "no-store");

						response.setDateHeader("Expires", 0);

						response.setHeader("Pragma", "no-cache");
						response.setHeader("Connection", "close");

						output404File(response.getOutputStream());

					}

				} else {
					errFlag = true;
					errorMsg = "service " + serviceId + " not exists.";
				}

			} catch (Exception e) {

				log.error("binary service failed: " + ExceptionUtils.getFullStackTrace(e));

				String errMsg = ExceptionUtils.getRootCauseMessage(e);
				errFlag = true;
				errorMsg = "service " + serviceId + " failed: " + errMsg;
			}

		} else {
			errFlag = true;
			errorMsg = "service id is required.";
		}

		if (errFlag) {
			log.error("binary service failed: " + errorMsg);

			request.setAttribute("failed_msg", "请求的文件不存在或已经被删除。");

			return;
		}

	}

	/**
	 * 页面请求
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String page(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String errorMsg = "";

		String templatePage = "error/404";
		Boolean errFlag = false;
		PageService service = null;

		Map<String, String> reqParam = parseRequest(request);
		String serviceId = reqParam.get(getRequestServiceId());

		if (null != serviceId) {

			try {

				service = SpringHelper.getBean(serviceId + "PageService",
						PageService.class);

				if (null != service) {
					templatePage = service.handleRequest(reqParam, model);
				} else {
					errFlag = true;
					errorMsg = "service " + serviceId + " not exists.";
				}

			} catch (Exception e) {
				log.error("page service failed: " + ExceptionUtils.getFullStackTrace(e));

				String errMsg = ExceptionUtils.getRootCauseMessage(e);
				errFlag = true;
				errorMsg = "service " + serviceId + " failed: " + errMsg;
			}

		} else {
			errFlag = true;
			errorMsg = "service id is required.";
		}

		if (errFlag) {
			log.error("page service failed: " + errorMsg);

			model.put("errorMsg", "系统忙，请稍后再试。");
		}

		return templatePage;

	}

	/**
	 * 数据请求
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void data(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String resultData = null;
		String contentType = "application/json; charset=UTF-8";
		String dataType = DataService.DATA_TYPE_JSON;
		Map<String, String> reqParam = parseRequest(request);
		String serviceId = reqParam.get(getRequestServiceId());
		Boolean errFlag = false;

		if (null != serviceId) {

			DataService service = null;
			try {
				service = SpringHelper.getBean(serviceId + "DataService",
						DataService.class);

				if (null != service) {
					resultData = service.handleRequest(reqParam);
					contentType = service.getContentType(reqParam);
				} else {
					errFlag = true;
					resultData = formatMsg(dataType, "service " + serviceId
							+ " not exists.");
				}

			} catch (Exception e) {
				log.error("data service failed: " + ExceptionUtils.getFullStackTrace(e));
				String errMsg = ExceptionUtils.getRootCauseMessage(e);
				errFlag = true;
				resultData = formatMsg(dataType, "service " + serviceId
						+ " failed: " + errMsg);
			}

		} else {
			errFlag = true;
			resultData = formatMsg(dataType, "service id is required.");
		}

		if (errFlag) {
			log.error("data service failed: " + resultData);
		}

		output(response, resultData, contentType);

	}

	protected String formatMsg(String fmt, String data) {
		String result = data;

		if (DataService.DATA_TYPE_JSON.equalsIgnoreCase(fmt)) {
			
			result = result.replaceAll("\\\n","<br/>");
			result = result.replaceAll("\\\r","");
			result = result.replaceAll("\\\t","&nbsp;&nbsp;&nbsp;&nbsp;");

			result = new StringBuffer("{\"error\": \"")
					.append(EscapeUtil.escape(data)).append("\"}").toString();

		} else if (DataService.DATA_TYPE_XML.equalsIgnoreCase(fmt)) {

			result = new StringBuffer("<error>")
					.append(EscapeUtil.escape(data)).append("<error>")
					.toString();

		} else {
			// default
		}

		return result;
	}

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

	protected void output404File(OutputStream out) {

		try {
			File file = getFileFromClasspath(get404ImageClasspath());
			FileInputStream in = new FileInputStream(file);
			IOUtils.copy(in, out);
			out.close();
			in.close();
		} catch (Throwable e) {
			log.error("output404File failed:", e);
		}
	}


	public abstract String getReqPrefix();

	public Map<String, String> parseRequest(HttpServletRequest request){
		
		Map<String, String> param = new HashMap<String, String>();
		Object reqAttr = null;
		String attrName, paraName;

		Enumeration<String> attrs = request.getAttributeNames();

		while (attrs.hasMoreElements()) {
			attrName = (String) attrs.nextElement();

			if (null != attrName && attrName.startsWith(getReqPrefix())) {

				paraName = attrName.substring(attrName.indexOf(getReqPrefix())+ getReqPrefix().length(),attrName.length());

				reqAttr = request.getAttribute(attrName);
				String attrVal = "";
				if(null != reqAttr){
					attrVal = reqAttr.toString();
				}
				param.put(paraName, attrVal);
			}
		}
		return param;
		
	}

}
