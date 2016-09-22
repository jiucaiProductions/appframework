package org.jiucai.appframework.base.mapper;

import java.util.Map;

/**
 * 请求参数映射器
 * 实现类的命名规则： xxxParameterMapper
 *
 * @author jiucai
 */
public interface ParameterMapper {

    public <T> T getParamBean(Class<T> beanClass);

    /**
     * 返回 request paramter 对应的map
     *
     * @return 保存了request paramter 的 map
     */
    public Map<String, String> getParamMap();

    /**
     * 返回 request paramter 前缀, 一般从 顶级 Controller 获取
     *
     * @return 放在 request attribute 中的请求参数的前缀
     */
    public String getParamPrefix();
}
