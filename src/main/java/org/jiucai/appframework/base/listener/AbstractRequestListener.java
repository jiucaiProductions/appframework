package org.jiucai.appframework.base.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.jiucai.appframework.base.util.RequestHolder;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

/***
 * 请求监听器，把 request 对象放在静态类中供java类调用
 * 
 * @author zhaidw
 * 
 */
public abstract class AbstractRequestListener implements ServletRequestListener {

	protected Logs log = LogUtil.getLog(getClass());

	public AbstractRequestListener() {
		super();
		log.info("AbstractRequestListener inited.");

	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {

		HttpServletRequest request = (HttpServletRequest) event
				.getServletRequest();

		RequestHolder.setRequest(request);

	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {

		RequestHolder.getRequestHolder().remove();

	}

}
