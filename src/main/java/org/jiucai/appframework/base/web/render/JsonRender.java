package org.jiucai.appframework.base.web.render;

import org.jiucai.appframework.base.util.JsonUtil;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("jsonRender")
public class JsonRender extends BaseRender {

	private String encoding;

	private static ObjectMapper mapper;

	static {

		mapper = JsonUtil.getObjectMapper();

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
			log.error("output failed:", e);
			throw new RuntimeException(e);
		}

		return result;
	}

}
