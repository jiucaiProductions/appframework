package test;

import org.apache.ibatis.session.SqlSession;
import org.jiucai.appframework.base.dao.impl.AbstractBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * your dao base class
 *
 * @author jiucai
 *
 */
public abstract class TestBaseDao extends AbstractBaseDao {

    // @Autowired
    // protected SqlSessionFactory mbtSqlSessionFactory;

    @Autowired
    private SqlSession mbtSqlSession;

    @Autowired
    private NamedParameterJdbcOperations appJdbcTemplate;

    @Override
    public NamedParameterJdbcOperations getDao() {
        return appJdbcTemplate;
    }

    public SqlSession getSqlSession() {
        return mbtSqlSession;
        // return mbtSqlSessionFactory.openSession();
    }

}
