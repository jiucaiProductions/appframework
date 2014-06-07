package org.jiucai.appframework.base.spring.service;

import java.io.OutputStream;
import java.util.Map;

/**
 * 二进制数据服务接口
 * <p>
 * 实现类 serviceId 命名规则 xxxBinaryService <br/>
 * 
 *  </p>
 * @author zhaidw
 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.BinaryService} 
 */
@Deprecated
public interface IBinaryService {
	

	/**
	 * 
	 * @param param  请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @param out ServletOutputStream 输出流
	 * @deprecated 已过时，请使用  {@link org.jiucai.appframework.base.service.BinaryService#handleRequest} 
	 */
	@Deprecated
	public abstract void handleRequest(Map<String, Object> param , OutputStream out);
	
	

	/**
	 * 返回要下载的文件是否存在
	 * @param param
	 * @return Boolean
	 * @deprecated 已过时，请使用  {@link org.jiucai.appframework.base.service.BinaryService#fileExists} 
	 */
	@Deprecated
	public abstract Boolean fileExists(Map<String, Object> param);
	
}
