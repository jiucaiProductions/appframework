package org.jiucai.appframework.base.service;

import java.io.OutputStream;

import org.jiucai.appframework.base.mapper.ParameterMapper;

/**
 * 文件下载服务接口
 * 
 * 实现类 serviceId 命名规则 xxxDownloadService
 * 
 * @author zhaidw
 * 
 */
public interface DownloadService {

	/**
	 * 处理下载请求
	 * @param paramMapper 请求参数映射器，支持参数map和baen，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @param out ServletOutputStream 输出流
	 */
	public abstract void handleRequest(ParameterMapper paramMapper, OutputStream out);

	/***
	 * 返回数据 ContentType
	 * @return String ContentType
	 */
	public abstract String getContentType();
	
	/**
	 * 浏览器下载对话框显示文件名，建议不超过 17 个汉字。
	 * <p>
	 * 当中文文字超过17个时，IE6 无法下载文件。这是IE的bug，参见微软的知识库文章 <a href="http://support.microsoft.com/default.aspx?kbid=816868">KB816868</a> 。
	 * 原因可能是IE在处理 Response Header 的时候，对header的长度限制在150字节左右。
	 * 而一个汉字编码成UTF-8是9个字节，那么17个字便是153个字节，所以会报错。
	 * </p>
	 * @param paramMapper 请求参数映射器，支持参数 map 和 bean，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @return 浏览器下载对话框显示文件名
	 */
	public abstract String getFileName(ParameterMapper paramMapper);
	
	/**
	 * 返回要下载的文件是否存在
	 * @param paramMapper 请求参数映射器，支持参数 map 和 bean，里面包含所有的 request pararamter 参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
	 * @return Boolean 文件是否存在
	 */
	public abstract Boolean fileExists(ParameterMapper paramMapper);
}
