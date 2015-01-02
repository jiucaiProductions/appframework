package test.zdw.service;

import java.util.Map;

import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.jiucai.appframework.base.service.DataService;
import org.jiucai.appframework.base.service.impl.DefaultAppBaseService;
import org.springframework.stereotype.Service;

import test.domain.TestDomain;


@Service("zdwTestDataService")
public class ZdwTestDataService extends DefaultAppBaseService implements DataService {

    @Override
	public String handleRequest(ParameterMapper paramMapper){
    	
    	Map<String, String> reqParam = paramMapper.getParamMap();
    	TestDomain domain = paramMapper.getParamBean(TestDomain.class);
    	
        log.info("reqParam: " + reqParam);
        log.info("domain: " + domain);
        
        String msg = getJsonRender().getString(domain);
        
        return msg;

    }

}
