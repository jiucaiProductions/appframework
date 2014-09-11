package org.jiucai.appframework.base.service;

import java.util.Map;

import org.jiucai.appframework.base.mapper.ParameterMapper;

/**
 * 页面服务接口
 * 
 * 实现类 serviceId 命名规则 xxxPageService
 * 
 * @author zhaidw
 */
public interface PageService {

	/**
	 * 处理页面请求
	 * @param paramMapper 请求参数映射器，支持参数 map 和 bean，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @param view 视图数据容器
	 * @return 视图文件路径
	 */
	public abstract String handleRequest(ParameterMapper paramMapper,  Map<String, Object> view);
	
}
