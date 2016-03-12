package org.jiucai.appframework.base.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonUtil: json的操作方法
 *
 * @author jiucai
 */
public class JsonUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        // /////// mapper 输出格式配置定义 /////////

        // 设置bean里没有的属性不解析,否则没有此项设置会出现异常
        mapper.getDeserializationConfig()
                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);

        // 日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
        mapper.getSerializationConfig().without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 不格式化输出
        mapper.getSerializationConfig().without(SerializationFeature.INDENT_OUTPUT);
        // 不输出null值
        mapper.getSerializationConfig().without(SerializationFeature.WRITE_NULL_MAP_VALUES);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    /**
     * 把 json 字符串转换为 bean 对象
     *
     * @param data
     *            json串
     * @param clazz
     *            bean 的class
     * @return 转化后的bean
     * @throws Exception
     *             Exception
     */
    public static <T> T getObject(String data, Class<T> clazz) throws Exception {
        return mapper.readValue(data, clazz);
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static String getString(Object data) throws Exception {
        String result = null;
        result = mapper.writeValueAsString(data);
        return result;
    }

    public JsonUtil() {
        super();
    }
}
