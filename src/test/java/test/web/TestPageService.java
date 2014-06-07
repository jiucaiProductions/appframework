package test.web;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("testPageService")
public class TestPageService extends TestAbstractBaseService {

	public String handleRequest(Map<String, String> param, Map<String, Object> view){
		
		view.put("req", param);
		
		String a = param.get("a");
		
		log.info("a:" + a);
		
		String[] arr = a.split(",");
		
		if(null != arr){
			log.info("a arr:" + arr.length);
			
			for (int i = 0; i < arr.length; i++) {
				log.info("a[" + i + "]: " + arr[i]);
			}
		}
		
		return "test";
	}
}
