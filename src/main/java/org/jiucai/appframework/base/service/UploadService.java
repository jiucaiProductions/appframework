package org.jiucai.appframework.base.service;

import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传服务接口
 *
 * 实现类 serviceId 命名规则 xxxUploadService
 *
 * @author zhaidw
 *
 */
public interface UploadService {

    /***
     * 返回数据 ContentType： html ? xml ? json ?
     * 
     * @return String ContentType
     */
    public abstract String getContentType();

    /**
     * 处理上传请求
     * 
     * @param paramMapper
     *            请求参数映射器，支持参数map和baen，里面包含所有的 request pararamter
     *            参数，多个重名的参数获取到的是使用英文逗号分隔的字符串
     * @param request
     *            请MultipartHttpServletRequest
     * @return 结果数据字符串
     */
    public abstract String handleRequest(ParameterMapper paramMapper,
            MultipartHttpServletRequest request);
}
