package test.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jiucai.appframework.base.executor.AppExecutorServiceFactory;
import org.junit.Test;

public class TestAppExecutorServiceFactoryAdd {

	private static ExecutorService s  = Executors.newFixedThreadPool(3);
	

	@Test
	public void doTest(){
		
		AppExecutorServiceFactory.add(s);
		
		AppExecutorServiceFactory.shutdown();

	}

}
