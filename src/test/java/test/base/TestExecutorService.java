package test.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		ExecutorService executorService = ExecutorServiceUtil.getExecutorService("test",3);
		log.info("executorService: " + executorService);
		
		TestThread t = new TestThread();
		
		executorService.submit(t);
		log.info("executorService: " + executorService);
		
		executorService.submit(t);
		log.info("executorService: " + executorService);
		
		executorService.submit(t);
		log.info("executorService: " + executorService);
		
		executorService.submit(t);
		
		log.info("executorService: " + executorService);
		
		executorService.submit(t);
		
		ExecutorService executorService2 = ExecutorServiceUtil.getExecutorService();
		log.info("executorService: " + executorService2);
		

		//ExecutorServiceUtil.shutdown();
		
		log.info("executorService: " + executorService);
		
		log.info("executorService: " + executorService);
		
		Thread.sleep(1000);
		
		log.info("executorService: " + executorService);
		
		
		ExecutorService executor2 = Executors.newFixedThreadPool(6);
		executor2.submit(t);
		log.info("executor2: " + executor2);
		
		
		
	}
	


}
