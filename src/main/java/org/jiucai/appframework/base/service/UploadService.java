package org.jiucai.appframework.base.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传服务接口
 * 
 * 实现类 serviceId 命名规则 xxxUploadService
 * 
 * @author zhaidw
 * 
 */
public interface UploadService  {

	/**
	 * 
	 * @param param  请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @return 结果数据字符串
	 */
	public abstract String handleRequest(Map<String, Object> param,MultipartHttpServletRequest request);

	/***
	 * 返回数据 ContentType： html ? xml ? json ? 
	 * 
	 * @return String
	 */
	public abstract String getContentType(Map<String, Object> param);
}
