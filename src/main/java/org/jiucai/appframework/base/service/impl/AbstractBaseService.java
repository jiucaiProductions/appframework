package org.jiucai.appframework.base.service.impl;

import java.io.OutputStream;
import java.util.Map;

import org.jiucai.appframework.base.mapper.ParameterMapper;
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
public abstract class AbstractBaseService implements PageService, DataService, UploadService,
		DownloadService, BinaryService {

	protected Logs log = LogUtil.getLog(getClass());
	
	protected static String EXCEPTION_MSG = "abstract service is NOT allowed be invoked directly.";

	@Override
	public String handleRequest(ParameterMapper paramMapper,
			Map<String, Object> view) {

		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String handleRequest(ParameterMapper paramMapper) {
		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String getContentType() {

		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String handleRequest(ParameterMapper paramMapper,
			MultipartHttpServletRequest request) {

		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public void handleRequest(ParameterMapper paramMapper, OutputStream out) {

		throw new RuntimeException(EXCEPTION_MSG);
	}

	@Override
	public String getFileName(ParameterMapper paramMapper) {
		throw new RuntimeException(EXCEPTION_MSG);
	}
	
	@Override
	public Boolean fileExists(ParameterMapper paramMapper) {

		throw new RuntimeException(EXCEPTION_MSG);
	}



}
