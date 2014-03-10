package test.base;

import org.jiucai.appframework.base.util.DateTimeUtil;


public class TestThread extends BaseTest implements Runnable {

	@Override
	public void run() {
		log.info("MyThread-" + Thread.currentThread().getId() + ": " + DateTimeUtil.getCurrentTime() );

	}

}
