package org.jiucai.appframework.base.spring.service;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 业务层基础类，提供 TransactionTemplate 允许在业务层使用编程式事务控制
 * 
 * @author zhaidw
 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.impl.AbstractBaseService} 
 */
@Deprecated
public abstract class BaseService {

	protected Logs log = LogUtil.getLog(getClass());

	/**
	 * 由子类实现 TransactionTemplate 的注入
	 * 
	 * @return TransactionTemplate
	 * @deprecated 已过时，请使用 {@link org.jiucai.appframework.base.service.impl.AbstractBaseService} 
	 */
	@Deprecated
	public abstract TransactionTemplate getTransactionTemplate();

}
