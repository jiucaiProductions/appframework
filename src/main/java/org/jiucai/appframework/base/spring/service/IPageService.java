package org.jiucai.appframework.base.spring.service;

import java.util.Map;

/**
 * 页面服务接口
 * 
 * <p>
 * 实现类 serviceId 命名规则 xxxPageService
 * 
 *  </p>
 * @author zhaidw
 *  @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.PageService} 
 */
@Deprecated
public interface IPageService {

	/**
	 * 
	 * @param param 请求参数，请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @param view 视图数据容器
	 * @return 视图文件路径
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.PageService#handleRequest} 
	 */
	@Deprecated 
	public abstract String handleRequest(Map<String, Object> param, Map<String, Object> view);
}
