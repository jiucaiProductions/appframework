package org.jiucai.appframework.base.service.impl;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jiucai.appframework.base.spring.web.render.JsonRender;
import org.jiucai.appframework.base.spring.web.render.XmlRender;
import org.jiucai.appframework.base.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DefaultAppBaseService  extends AbstractBaseService {

	/**
	 * xml 或 json 响应的默认编码
	 */
	protected static String encoding = "UTF-8";

	/**
	 * XML 字符串生成器
	 */
	@Autowired
	private XmlRender xmlRender;

	/**
	 * json 字符串生成器
	 */
	@Autowired
	private JsonRender jsonRender;
	
	
	/**
	 * 配置项读取类
	 */
	protected static Configuration config;
	
	static{
		config = ConfigUtil.addConfig("config");
		config = ConfigUtil.addConfig("mail");
		
	}
	
	public static Configuration getConfig(){
		return config;
	}
	
	
	/**
	 * 返回转换 xml 的工具对象
	 * 
	 * @return
	 */
	public XmlRender getXmlRender() {
		xmlRender.setEncoding(encoding);
		return xmlRender;
	}

	/**
	 * 返回转换 json 工具对象
	 * 
	 * @return
	 */
	public JsonRender getJsonRender() {
		jsonRender.setEncoding(encoding);
		
		//设置bean里没有的属性不解析,否则没有此项设置会出现异常
		jsonRender.getObjectMapper().getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		jsonRender.getObjectMapper().getSerializationConfig().without(SerializationConfig.Feature.WRAP_ROOT_VALUE);
		
		//日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
		jsonRender.getObjectMapper().getSerializationConfig().without(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS);
		// 不格式化输出
		jsonRender.getObjectMapper().getSerializationConfig().without(SerializationConfig.Feature.INDENT_OUTPUT);
		// 不输出null值
		jsonRender.getObjectMapper().getSerializationConfig().without(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
		

		SerializationConfig sConfig = jsonRender.getObjectMapper().getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		
		jsonRender.getObjectMapper().setSerializationConfig(sConfig);
		
		return jsonRender;
	}
	
	/**
	 * 返回 json 格式的成功或错误信息
	 * 
	 * @param isSuccess
	 *            true 表示成功消息 false 表示错误消息
	 * @param msg
	 * @return
	 */
	protected String getJsonMsg(Boolean isSuccess, String msg) {
		StringBuffer result = new StringBuffer("");

		if (isSuccess) {
			if (StringUtils.isBlank(msg)) {
				msg = "ok";
			}
			result.append("{\"success\":\"").append(msg).append("\"}");
		} else {
			if (StringUtils.isBlank(msg)) {
				msg = "failed";
			}
			result.append("{\"error\":\"").append(msg).append("\"}");
		}

		return result.toString();
	}

	/**
	 * 返回 chartXML 格式的成功或错误信息
	 * 
	 * @param isSuccess
	 *            true 表示成功消息 false 表示错误消息
	 * @param msg
	 * @return
	 */
	protected String getXmlMsg(Boolean isSuccess, String msg) {

		StringBuffer result = new StringBuffer("");

		if (isSuccess) {
			if (StringUtils.isBlank(msg)) {
				msg = "ok";
			}
			result.append("<chart><success>").append(msg)
					.append("</success></chart>");
		} else {
			if (StringUtils.isBlank(msg)) {
				msg = "failed";
			}
			result.append("<chart><error>").append(msg)
					.append("</error></chart>");
		}

		return result.toString();
	}
	
	
	@Override
	public String getContentType(Map<String, String> param) {
		return getJsonRender().getContentType();
	}
	

}
