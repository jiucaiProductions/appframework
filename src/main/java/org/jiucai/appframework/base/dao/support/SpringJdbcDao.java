package org.jiucai.appframework.base.dao.support;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public interface SpringJdbcDao {
	  /**
	   * Users should use this method to get a NamedParameterJdbcOperations to call its statement methods
	   * This is NamedParameterJdbcOperations is managed by spring.
	   *
	   * @return Spring managed NamedParameterJdbcOperations
	   */
	public NamedParameterJdbcOperations getDao();
	
}
