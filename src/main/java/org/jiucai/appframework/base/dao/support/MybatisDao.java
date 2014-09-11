package org.jiucai.appframework.base.dao.support;

import org.apache.ibatis.session.SqlSession;

public interface MybatisDao {

	  /**
	   * Users should use this method to get a SqlSession to call its statement methods
	   * This is SqlSession is managed by spring. Users should not commit/rollback/close it
	   * because it will be automatically done.
	   *
	   * @return Spring managed thread safe SqlSession
	   */
	  public SqlSession getSqlSession();
}
