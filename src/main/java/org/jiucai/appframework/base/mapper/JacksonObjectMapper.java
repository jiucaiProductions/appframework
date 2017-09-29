package org.jiucai.appframework.base.mapper;

import org.jiucai.appframework.base.util.JsonUtil;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("jacksonObjectMapper")
public class JacksonObjectMapper {

	private static ObjectMapper mapper;

	static {

		mapper = JsonUtil.getObjectMapper();
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

}
