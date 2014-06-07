package org.jiucai.appframework.base.spring.service;

import java.io.OutputStream;
import java.util.Map;

/**
 * 文件下载服务接口
 * 
 * 实现类 serviceId 命名规则 xxxDownloadService
 * 
 * @author zhaidw
 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DownloadService} 
 */
@Deprecated
public interface IDownloadService {

	/**
	 * 
	 * @param param  请求参数map，里面包含所有的 request pararamter 参数，多个重名的参数取出后需要转换为List使用
	 * @param out ServletOutputStream 输出流
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DownloadService.handleRequest} 
	 */
	@Deprecated
	public abstract void handleRequest(Map<String, Object> param, OutputStream out);

	/***
	 * 返回数据 ContentType
	 * 
	 * @return String
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DownloadService.getContentType} 
	 */
	@Deprecated
	public abstract String getContentType(Map<String, Object> param);
	
	/**
	 * 浏览器下载对话框显示文件名，建议不超过 17 个汉字。
	 * <p>
	 * 当中文文字超过17个时，IE6 无法下载文件。这是IE的bug，参见微软的知识库文章 <a href="http://support.microsoft.com/default.aspx?kbid=816868">KB816868</a> 。
	 * 原因可能是IE在处理 Response Header 的时候，对header的长度限制在150字节左右。
	 * 而一个汉字编码成UTF-8是9个字节，那么17个字便是153个字节，所以会报错。
	 * </p>
	 * 
	 * @return 浏览器下载对话框显示文件名
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DownloadService.getFileName} 
	 */
	@Deprecated
	public abstract String getFileName(Map<String, Object> param);
	
	/**
	 * 返回要下载的文件是否存在
	 * @param param
	 * @return Boolean
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.DownloadService.fileExists} 
	 */
	@Deprecated
	public abstract Boolean fileExists(Map<String, Object> param);
	
	
}
