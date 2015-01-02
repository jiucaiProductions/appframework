package org.jiucai.appframework.base.dao;


import org.apache.ibatis.session.SqlSession;
import org.hibernate.Session;
import org.jiucai.appframework.base.dao.support.HibernateDao;
import org.jiucai.appframework.base.dao.support.MybatisDao;
import org.jiucai.appframework.base.dao.support.SpringJdbcDao;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * DAO 基类, 默认支持 SpringJdbc, Mybatis, Hibernate
 * @author zhaidw
 *
 */
public abstract class BaseDao implements SpringJdbcDao, MybatisDao, HibernateDao {

	protected Logs log = LogUtil.getLog(getClass());

	@Override
	public Session getSession() {
		throw new RuntimeException("org.hibernate.Session is required");
	}

	@Override
	public SqlSession getSqlSession() {
		throw new RuntimeException("org.apache.ibatis.session.SqlSession is required");
	}

	@Override
	public NamedParameterJdbcOperations getDao() {
		throw new RuntimeException("org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations is required");
	}
	
}
