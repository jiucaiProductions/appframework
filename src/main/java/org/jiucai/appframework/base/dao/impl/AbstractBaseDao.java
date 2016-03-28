package org.jiucai.appframework.base.dao.impl;

import org.jiucai.appframework.base.dao.BaseDao;
import org.jiucai.appframework.base.dao.support.SpringJdbcDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public abstract class AbstractBaseDao extends BaseDao implements SpringJdbcDao {

    @Override
    public NamedParameterJdbcOperations getDao() {
        throw new RuntimeException(
                "org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations is required");
    }

}
