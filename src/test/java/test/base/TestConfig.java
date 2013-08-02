package test.base;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jiucai.appframework.base.util.ConfigUtil;

public class TestConfig extends TestCase {

	public static Test suite() {
		return new TestSuite(TestConfig.class);
	}

	public static void test() {
		ConfigUtil.addConfig("config");
		String value = ConfigUtil.getString("common.app.env.timestamp");

		System.out.println(value);
	}

	public static void main(String[] args) {
		test();
	}
}
