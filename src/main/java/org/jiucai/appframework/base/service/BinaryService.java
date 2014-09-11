package org.jiucai.appframework.base.service;

import java.io.OutputStream;

import org.jiucai.appframework.base.mapper.ParameterMapper;

/**
 * 二进制数据服务接口
 * <p>
 * 实现类 serviceId 命名规则 xxxBinaryService <br/>
 * 
 *  </p>
 * @author zhaidw
 *
 */
public interface BinaryService  {

	/**
	 * 处理二进制数据展现，比如 图片 SWF等
	 * @param paramMapper 请求参数映射器，支持参数map和baen，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @param out ServletOutputStream 输出流
	 */
	public abstract void handleRequest(ParameterMapper paramMapper , OutputStream out);
	
	

	/**
	 * 返回要下载的文件是否存在
	 * @param paramMapper 请求参数映射器，支持参数map和baen，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @return Boolean
	 */
	public abstract Boolean fileExists(ParameterMapper paramMapper);
}
