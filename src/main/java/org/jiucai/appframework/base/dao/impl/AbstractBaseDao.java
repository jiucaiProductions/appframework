package org.jiucai.appframework.base.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Session;
import org.jiucai.appframework.base.dao.BaseDao;
import org.jiucai.appframework.base.dao.support.HibernateDao;
import org.jiucai.appframework.base.dao.support.MybatisDao;
import org.jiucai.appframework.base.dao.support.SpringJdbcDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public abstract class AbstractBaseDao extends BaseDao implements SpringJdbcDao, MybatisDao,
        HibernateDao {

    @Override
    public NamedParameterJdbcOperations getDao() {
        throw new RuntimeException(
                "org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations is required");
    }

    @Override
    public Session getSession() {
        throw new RuntimeException("org.hibernate.Session is required");
    }

    @Override
    public SqlSession getSqlSession() {
        throw new RuntimeException("org.apache.ibatis.session.SqlSession is required");
    }
}
