package test.qht.service;

import java.util.Map;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.stereotype.Service;

import test.domain.TestDomain;


@Service("testTPageService")
public class TestTPageService implements PageService<TestDomain> {
    private static final Logs LOG = LogUtil.getLog(TestTPageService.class);
    @Override
    public String handleRequest(Map<String, String> param, Map<String, Object> view, TestDomain t) {
        LOG.debug(param);
        LOG.debug(t.toString());
        return null;
    }

}
