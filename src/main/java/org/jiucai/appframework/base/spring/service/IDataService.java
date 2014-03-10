package org.jiucai.appframework.base.spring.service;

import java.util.Map;

/**
 * 数据服务接口
 * 
 * 实现类 serviceId 命名规则 xxxDataService
 * 
 * @author zhaidw
 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DataService} 
 */
@Deprecated
public interface IDataService {
	
	public static final String DATA_TYPE_XML 	= "xml";
	public static final String DATA_TYPE_JSON 	= "json";
	public static final String DATA_TYPE_HTML 	= "html";

	/**
	 * 
	 * @param param  请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @return 结果数据字符串
	 * 	 @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DataService#handleRequest} 
	 */
	public abstract String handleRequest(Map<String, Object> param);

	/***
	 * 返回数据 ContentType： html ? xml ? json ? 
	 * 
	 * @return String
	 * 	@deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DataService#getContentType} 
	 */
	public abstract String getContentType(Map<String, Object> param);
	
}
