package test.base;


import org.jiucai.appframework.base.util.ConfigUtil;
import org.junit.Test;

public class TestConfig extends BaseTest {

	@Test
	public  void test() {
		ConfigUtil.addConfig("config");
		String value = ConfigUtil.getString("common.app.env.timestamp");
		log.info("common.app.env.timestamp: " + value);
	}

}
