package org.jiucai.appframework.base.executor.impl;

import org.jiucai.appframework.base.executor.AppExecutorService;

/**
 * 默认 线程池 命令实现类
 * 
 * @author zhaidw
 * 
 */
public class DefaultExecutorCommand implements AppExecutorService {

	protected DefaultExecutorService service;

	public DefaultExecutorCommand() {

	}

	public DefaultExecutorCommand(DefaultExecutorService service) {
		this.service = service;

	}

	public DefaultExecutorService getService() {
		return service;
	}

	public void setService(DefaultExecutorService service) {
		this.service = service;
	}

	@Override
	public void shutdown() {
		service.shutdown();

	}

	@Override
	public String getName() {
		return service.getName();
	}

}
