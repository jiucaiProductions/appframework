package org.jiucai.appframework.base.spring.web.render;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.stereotype.Service;


@Service("jsonRender")
public class JsonRender extends BaseRender {

	private String encoding;

	private static ObjectMapper mapper;

	static {

		mapper = new ObjectMapper();

		// /////// mapper 输出格式配置定义 /////////
		mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
		
		// 日期序列化需要 get方法加 @JsonSerialize(using=JsonDateSerializer.class)
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

		// 格式化输出
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
		
		// 不输出null值
		mapper.setSerializationInclusion(Inclusion.NON_NULL);

		// 不输出 Map entries with null values 
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,false);

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
	public String getString(Object data) throws Exception {
		String result = null;
		result = mapper.writeValueAsString(data);
		return result;
	}

}
