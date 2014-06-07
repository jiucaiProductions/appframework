package test.base;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScheduler {

	public static void main(String[] args) throws Throwable {
		String[] configFiles = { "classpath:spring/applicationContext.xml",
				"classpath:spring/applicationDbContext.xml",
				"classpath:spring/defaultJobConfig.xml" };

		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(configFiles);
		
		// add a shutdown hook for the above context...
		ctx.registerShutdownHook();

		int count = ctx.getBeanDefinitionCount();
		System.out.println("getBeanDefinitionCount: " + count);

		ctx.close();

	}
}
