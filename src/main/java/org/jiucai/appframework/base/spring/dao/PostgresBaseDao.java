package org.jiucai.appframework.base.spring.dao;

import java.util.HashMap;
import java.util.Map;

public abstract class PostgresBaseDao extends BaseDao {
	
	/**
	 * PostgreSQL get Sequence nextval Value
	 * @param seqName 序列名
	 * @return 序列的下一个值
	 */
	protected Long getId(String seqName) {

		String sql = "select nextval( :seqName ) ";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seqName", seqName);
		return getDao().queryForObject(sql, param, Long.class);

	}
	
	
	/**
	 * 构造 Postgres 分页SQL
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
		pageSql.append(" select * from ( ");
		pageSql.append(sql);
		pageSql.append(" ) postgres_table_list limit ");
		pageSql.append( pageSize);
		pageSql.append(" offset ");
		pageSql.append( (pageNo-1) * pageSize);

		return pageSql.toString();

	}

}
