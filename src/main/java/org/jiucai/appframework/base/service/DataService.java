package org.jiucai.appframework.base.service;

import org.jiucai.appframework.base.mapper.ParameterMapper;

/**
 * 数据服务接口
 * 
 * 实现类 serviceId 命名规则 xxxDataService
 * 
 * @author zhaidw
 * 
 */
public interface DataService {

	public static final String DATA_TYPE_XML 	= "xml";
	public static final String DATA_TYPE_JSON 	= "json";
	public static final String DATA_TYPE_HTML = "html";

	/**
	 * 处理数据请求
	 * @param paramMapper 请求参数映射器，支持参数 map 和 bean，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @return 结果数据字符串
	 */
	public abstract String handleRequest(ParameterMapper paramMapper);

	/***
	 * 返回数据 ContentType： html ? xml ? json ? 
	 * @return String contentType
	 */
	public abstract String getContentType();
}
