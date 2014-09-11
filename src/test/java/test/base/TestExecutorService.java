package test.base;

import java.util.concurrent.ExecutorService;

import org.jiucai.appframework.base.executor.util.ExecutorServiceUtil;
import org.junit.Before;
import org.junit.Test;

public class TestExecutorService extends BaseTest {
	
	@Before
	public void init() {
		log.info("init..");
	}
	
	@Test
	public void testExecutorService() throws Exception{
		
		ExecutorService executorService = ExecutorServiceUtil.getExecutorService("test",5);
		log.info("executorService: " + executorService);

		for (int i = 0; i < 5; i++) {
			TestThread t = new TestThread();
			t.setIndex(i);
			executorService.submit(t);
		}

		ExecutorServiceUtil.shutdown();
		log.info("executorService after shutdown : " + executorService);
		
		
		
	}
	


}
