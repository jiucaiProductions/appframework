package org.jiucai.appframework.base.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * JsonUtil: json的操作方法
 * @author jiucai
 */
public class JsonUtil {
	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();

		// /////// mapper 输出格式配置定义 /////////
		
		//设置bean里没有的属性不解析,否则没有此项设置会出现异常
		mapper.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.getSerializationConfig().without(SerializationConfig.Feature.WRAP_ROOT_VALUE);
		
		//日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
		mapper.getSerializationConfig().without(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS);
		// 不格式化输出
		mapper.getSerializationConfig().without(SerializationConfig.Feature.INDENT_OUTPUT);
		// 不输出null值
		mapper.getSerializationConfig().without(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
		

		SerializationConfig sConfig = mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		
		mapper.setSerializationConfig(sConfig);
	
	}

	public JsonUtil() {
		super();
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}
	
	public static String getString(Object data) throws Exception {
		String result = null;
		result = mapper.writeValueAsString(data);
		return result;
	}
	
	/**
	 * 把 json 字符串转换为 bean 对象
	 * @param data json串
	 * @param clazz bean 的class
	 * @return 转化后的bean
	 * @throws Exception
	 */
	public static <T> T getObject(String data, Class<T> clazz) throws Exception {
		return mapper.readValue(data, clazz);
	}
}
