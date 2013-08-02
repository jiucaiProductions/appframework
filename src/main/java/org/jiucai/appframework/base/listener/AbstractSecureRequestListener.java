package org.jiucai.appframework.base.listener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

/***
 * 请求监听器，对 request 所有参数进行安全处理
 * 
 * @author zhaidw
 * 
 */
public abstract class AbstractSecureRequestListener extends AbstractRequestListener {

	public AbstractSecureRequestListener() {
		super();
		log.info("AbstractSecureRequestListener inited.");

	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {

		super.requestInitialized(event);

		HttpServletRequest request = (HttpServletRequest) event
				.getServletRequest();

		trimRequestParam(request);
		doSecureRequest(request);

	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {

		super.requestDestroyed(event);

	}

	/**
	 * 过滤请求参数中的左右空格
	 * 
	 * @param request
	 */
	protected synchronized void trimRequestParam(HttpServletRequest request) {


		Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();

		if (null != paramNames) {
			String paramName, paramValue;
			String[] paramValues;
			List<String> tempValues;
			while (paramNames.hasMoreElements()) {
				paramName = paramNames.nextElement();

				paramValues = request.getParameterValues(paramName);

				if (null != paramValues && paramValues.length > 0) {

					tempValues = new ArrayList<String>();

					for (int i = 0; i < paramValues.length; i++) {
						if (null != paramValues[i]) {
							paramValue = paramValues[i].trim();
							
							tempValues.add(paramValue);
						}
					}

					if (paramValues.length == 1) {
						request.setAttribute(getReqPrefix() + paramName,tempValues.get(0));
					} else {
						request.setAttribute(getReqPrefix() + paramName,tempValues);
					}

				}

			}
		}

	}

	/**
	 * 应用可自行实现的扩展方法
	 * 
	 * @param request
	 */
	public abstract void doSecureRequest(HttpServletRequest request);

	/**
	 * 放在 reqesut attribute 中的 parameter 的前缀
	 * 
	 * @return String
	 */
	public abstract String getReqPrefix();

}
