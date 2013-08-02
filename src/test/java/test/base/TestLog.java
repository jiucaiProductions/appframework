package test.base;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;

public class TestLog extends TestCase {

	protected static Logs logger = LogUtil.getLog(TestLog.class);

	protected Logs log = LogUtil.getLog(getClass());

	public static Test suite() {
		return new TestSuite(TestLog.class);
	}

	public void testLog() {
		log.info("method testLog.");
	}

	public static void testLogger() {
		logger.info("method testLogger.");
	}

	public static void main(String[] args) {

		testLogger();

		TestLog t1 = new TestLog();

		t1.testLog();
	}

}
