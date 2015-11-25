package test.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMainLog extends BaseTest {

    protected static Logger logger = LoggerFactory.getLogger(TestMainLog.class);

    public static void main(String[] args) {
        logger.info("TestMainLog... info");
        logger.error("TestMainLog... error");
    }

}
