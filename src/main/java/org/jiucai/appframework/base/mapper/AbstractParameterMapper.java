package org.jiucai.appframework.base.mapper;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jiucai.appframework.base.helper.AppRequestHelper;
import org.jiucai.appframework.base.helper.AppRequestHolder;
import org.jiucai.appframework.base.service.BaseService;

public abstract class AbstractParameterMapper extends BaseService implements ParameterMapper {
	
	public Map<String, String> getParamMap(){
		 
		 Map<String, String> reqParam = AppRequestHelper.parseRequest(AppRequestHolder.getRequest(),getParamPrefix());
		 
		 return reqParam;
	}
	


	public <D> D getParamBean(Class<D> c) {
		D d1= null;
		Map<String, String> reqParam  = getParamMap();
		try {
			d1= (D) c.newInstance();
			BeanUtils.populate(d1, reqParam);
		} catch (Exception e) {
			log.error("create instance failed for " + c.getName() ,e);
		}
		return d1;
	}

}
