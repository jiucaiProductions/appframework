package test.zdw.service;

import java.util.Map;

import org.jiucai.appframework.base.annotation.PageServiceMapping;
import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.jiucai.appframework.base.service.impl.DefaultAppBaseService;
import org.springframework.stereotype.Service;

import test.domain.TestDomain;

@Service("zdwTest2PageService")
public class ZdwTest2PageService extends DefaultAppBaseService {

    @Override
    @PageServiceMapping("/zdwTest2")
    public String handleRequest(ParameterMapper paramMapper, Map<String, Object> view) {

        Map<String, String> reqParam = paramMapper.getParamMap();
        TestDomain domain = paramMapper.getParamBean(TestDomain.class);

        log.info("reqParam: " + reqParam);
        log.info("domain: " + domain);

        view.put("reqParam", reqParam);
        view.put("domain", domain);

        return "test/test_zdw";
    }

}
