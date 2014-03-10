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
	public void testExecutorService(){
		
		ExecutorService executorService = ExecutorServiceUtil.getExecutorService();
		log.info("executorService: " + executorService);
		
		executorService.submit(new TestThread());
		executorService.submit(new TestThread());
		executorService.submit(new TestThread());
		executorService.submit(new TestThread());
		executorService.submit(new TestThread());
		
		ExecutorService executorService2 = ExecutorServiceUtil.getExecutorService();
		log.info("executorService: " + executorService2);
		

		ExecutorServiceUtil.shutdown();
	}
	


}
