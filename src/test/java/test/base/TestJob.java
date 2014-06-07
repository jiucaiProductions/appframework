package test.base;

import org.jiucai.appframework.base.util.DateTimeUtil;

public class TestJob extends BaseTest {

	public void execute() {
		log.info("TestJob-Thread-" + Thread.currentThread().getId() + ": "
				+ DateTimeUtil.getCurrentTime());
	}

}
