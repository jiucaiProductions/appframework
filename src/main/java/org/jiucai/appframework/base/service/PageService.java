package org.jiucai.appframework.base.service;

import java.util.Map;

/**
 * 页面服务接口
 * 
 * 实现类 serviceId 命名规则 xxxPageService
 * 
 * @author zhaidw
 */
public interface PageService {

	/**
	 * 
	 * @param param 请求参数，请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @param view 视图数据容器
	 * @return 视图文件路径
	 */
	public abstract String handleRequest(Map<String, Object> param, Map<String, Object> view);
}
