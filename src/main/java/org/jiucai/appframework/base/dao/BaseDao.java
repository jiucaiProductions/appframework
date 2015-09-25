package org.jiucai.appframework.base.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO 基类, 默认支持 SpringJdbc, Mybatis, Hibernate
 *
 * @author zhaidw
 *
 */
public abstract class BaseDao {

    protected Logger log = LoggerFactory.getLogger(getClass());

}
