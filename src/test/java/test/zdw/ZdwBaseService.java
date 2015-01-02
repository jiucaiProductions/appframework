package test.zdw;

import org.jiucai.appframework.base.service.BaseService;
import org.jiucai.appframework.base.util.DateTimeUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ZdwBaseService extends BaseService {

	protected static String[] configLocations = new String[] { "classpath:spring/application*.xml" };
	protected static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			configLocations);

	public static <T> T getBean(String beanId, Class<T> t) {
		return context.getBean(beanId, t);
	}

	public static String getTime() {
		return DateTimeUtil.getCurrentTime();
	}

}
