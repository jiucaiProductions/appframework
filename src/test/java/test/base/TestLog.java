package test.base;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.junit.Test;

public class TestLog extends BaseTest {

	protected static Logs logger = LogUtil.getLog(TestLog.class);

	@Test
	public void testLog() {
		log.info("test log");
	}

	@Test
	public void testLogger() {
		logger.info("test logger");
	}


}
