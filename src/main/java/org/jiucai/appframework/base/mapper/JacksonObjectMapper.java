package org.jiucai.appframework.base.mapper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component("jacksonObjectMapper")
public class JacksonObjectMapper {

    private static ObjectMapper mapper;

    static {

        mapper = new ObjectMapper();

        // /////// mapper 输出格式配置定义 /////////
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        // 日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 格式化输出
        mapper.configure(SerializationFeature.INDENT_OUTPUT, false);

        // 不输出null值
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 不输出 Map entries with null values
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
