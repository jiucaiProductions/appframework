package test.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog extends BaseTest {

    protected static Logger logger = LoggerFactory.getLogger(TestLog.class);

    @Test
    public void testLog() {
        log.info("test log");
    }

    @Test
    public void testLogger() {
        logger.info("test logger");
    }

}
