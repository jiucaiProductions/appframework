package org.jiucai.appframework.base.service.impl;

import java.io.OutputStream;
import java.util.Map;

import org.jiucai.appframework.base.service.BinaryService;
import org.jiucai.appframework.base.service.DataService;
import org.jiucai.appframework.base.service.DownloadService;
import org.jiucai.appframework.base.service.PageService;
import org.jiucai.appframework.base.service.UploadService;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 同时实现5种接口的基类
 * @author jiucai
 *
 */
public abstract class BaseService implements PageService, DataService, UploadService,
		DownloadService, BinaryService {

	protected Logs log = LogUtil.getLog(getClass());
	
	private final String EXCEPTION_MSG = "not implements yet";

	@Override
	public String handleRequest(Map<String, Object> param,
			Map<String, Object> view) {
		preProcess(param);
		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String handleRequest(Map<String, Object> param) {
		preProcess(param);
		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String getContentType(Map<String, Object> param) {

		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String handleRequest(Map<String, Object> param,
			MultipartHttpServletRequest request) {
		preProcess(param);
		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public void handleRequest(Map<String, Object> param, OutputStream out) {
		preProcess(param);
		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String getFileName(Map<String, Object> param) {
		throw new RuntimeException(EXCEPTION_MSG);
	}
	
	@Override
	public Boolean fileExists(Map<String, Object> param) {

		throw new RuntimeException(EXCEPTION_MSG);
	}
	
	/**
	 * 请求预处理
	 * @param param
	 */
	public  abstract void preProcess(Map<String, Object> param);



}
