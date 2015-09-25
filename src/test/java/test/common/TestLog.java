package test.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    static Logger logger = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.info("test info");
        logger.error("test error");

    }

}
