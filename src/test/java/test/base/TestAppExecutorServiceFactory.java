package test.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jiucai.appframework.base.executor.AppExecutorService;
import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.jiucai.appframework.base.executor.impl.DefaultExecutorCommand;
import org.jiucai.appframework.base.executor.impl.DefaultExecutorService;
import org.junit.Test;

public class TestAppExecutorServiceFactory {

	
	private static ExecutorService s  = Executors.newFixedThreadPool(3);
	private static ExecutorService s2 = Executors.newSingleThreadExecutor();
	private static ExecutorService s3 = Executors.newCachedThreadPool();
	private static ExecutorService s4 = Executors.newScheduledThreadPool(4);
	
	static{
		// use commond mode
		AppExecutorService cmd = new DefaultExecutorCommand(DefaultExecutorService.getInstance());
		AppExecutorServiceFactory.addExecutorCommand(cmd);
		
		// direct use AppExecutorServiceFactory
		AppExecutorServiceFactory.add(s);
		AppExecutorServiceFactory.add(s2);
		AppExecutorServiceFactory.add(s3);
		AppExecutorServiceFactory.add(s4);
		
	}
	
	
	@Test
	public void doTest(){
		
		s.submit(new TestThread());

		AppExecutorServiceFactory.shutdown();
		
	}
	
}
