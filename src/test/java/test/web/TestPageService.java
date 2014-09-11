package test.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jiucai.appframework.base.mapper.ParameterMapper;
import org.springframework.stereotype.Service;

@Service("testPageService")
public class TestPageService extends TestAbstractBaseService {

	public String handleRequest(ParameterMapper paramMapper , Map<String, Object> view){
		
		Map<String, String> param = paramMapper.getParamMap();
		view.put("req", param);
		
		String a = param.get("a");
		log.info("a:" + a);
		
		if(StringUtils.isNotBlank(a)){
			
			String[] arr = a.split(",");
			
			if(null != arr){
				log.info("a arr:" + arr.length);
				
				for (int i = 0; i < arr.length; i++) {
					log.info("a[" + i + "]: " + arr[i]);
				}
			}
		}
		
		return "test";
	}
}
