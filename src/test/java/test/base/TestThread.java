package test.base;

import org.jiucai.appframework.base.util.DateTimeUtil;


public class TestThread extends BaseTest implements Runnable {

	@Override
	public void run() {
		log.info("TestThread-" + Thread.currentThread().getId() + ": " + DateTimeUtil.getCurrentTime() );
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			log.error("sleep failed",e);
		}

	}

}
