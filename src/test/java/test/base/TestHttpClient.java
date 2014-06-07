package test.base;

import java.util.HashMap;
import java.util.Map;

import org.jiucai.appframework.base.util.HttpClientUtil;
import org.junit.Test;

public class TestHttpClient extends BaseTest {

	@Test
	public void testClient(){
		String url = "https://auth.alipay.com/login/verifyId.json";
		
		try{
			Map<String, String> param = new HashMap<String, String>();

			String data = HttpClientUtil.get(url, param, "GBK");
			log.info("res data: " + data);
			
		}catch(Throwable e){
			
			log.error("HttpClientUtil get failed: ",e);
		}
		
		
	}
}
