package org.jiucai.appframework.base.web.render;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Service("jsonRender")
public class JsonRender extends BaseRender {

	private String encoding;

	private static ObjectMapper mapper;

	static {

		mapper = new ObjectMapper();

		// /////// mapper 输出格式配置定义 /////////
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		
		// 日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

		// 格式化输出
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		
		// 不输出null值
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		// 不输出 Map entries with null values 
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false);

	}

	public JsonRender() {
		super();
	}

	public JsonRender(String encoding) {

		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}

	@Override
	public String getContentType() {
		// http://tools.ietf.org/html/rfc4627
		// The MIME media type for JSON text is application/json.
		// return "application/json; charset=" + encoding;
		return "application/json; charset=" + encoding;
	}

	@Override
	public String getString(Object data) {
		String result = null;
		try {
			result = mapper.writeValueAsString(data);
		} catch (Throwable e) {
			log.error("output failed:",e);
			throw new RuntimeException(e);
		}
		
		return result;
	}

}
