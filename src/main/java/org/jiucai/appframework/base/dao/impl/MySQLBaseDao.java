package org.jiucai.appframework.base.dao.impl;

import org.jiucai.appframework.base.dao.BaseDao;


/**
 * MySQL dao 基类
 * @author jiucai
 *
 */
public abstract class MySQLBaseDao extends BaseDao {
	
	
	/**
	 * 构造 MySQL 分页SQL
	 * 
	 * @param sql 原始SQL
	 * @param pageNo 页码 默认 1
	 * @param pageSize 每页记录数 默认10
	 * @return 分页后的SQL
	 */
	protected String getPagedSQL(String sql, Long pageNo, Long pageSize) {

		if (null == pageNo || pageNo < 1) {
			pageNo = 1L;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = 10L;
		}

		StringBuffer pageSql = new StringBuffer();
		pageSql.append(sql);
		pageSql.append(" limit ");
		pageSql.append( pageSize);
		pageSql.append(" offset ");
		pageSql.append( (pageNo-1) * pageSize);

		return pageSql.toString();

	}

}
