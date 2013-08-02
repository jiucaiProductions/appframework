package org.jiucai.appframework.base.spring.dao;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * DAO 基类
 * @author zhaidw
 *
 */
public abstract class BaseDao {

	protected Logs log = LogUtil.getLog(getClass());

	/**
	 * 由子类实现 NamedParameterJdbcOperations 的注入
	 * @return NamedParameterJdbcOperations
	 */
	public abstract NamedParameterJdbcOperations getDao();
	
}
