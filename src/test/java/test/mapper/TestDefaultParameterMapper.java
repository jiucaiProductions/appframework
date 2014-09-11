package test.mapper;

import org.jiucai.appframework.base.mapper.AbstractParameterMapper;
import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.springframework.stereotype.Service;

import test.web.TestAppController;

@Service("testDefaultParameterMapper")
public class TestDefaultParameterMapper extends AbstractParameterMapper implements ParameterMapper {
	
	@Override
	public String getParamPrefix() {
		return TestAppController.REQ_PREFIX;
	}

}
