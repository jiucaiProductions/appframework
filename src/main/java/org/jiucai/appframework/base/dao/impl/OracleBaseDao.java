package org.jiucai.appframework.base.dao.impl;

import java.util.HashMap;

import org.jiucai.appframework.base.dao.BaseDao;

/**
 * Oracle dao 基类
 * @author jiucai
 */
public abstract class OracleBaseDao extends BaseDao {
	
	/**
	 * Oracle get Sequence nextval Value
	 * @param seqName 序列名
	 * @return 序列的下一个值
	 */
	protected Long getId(String seqName) {

		StringBuffer sql = new StringBuffer("select ").append(seqName).append(".nextval from dual ");
		return getDao().queryForObject(sql.toString(), new HashMap<String, Object>(), Long.class);

	}
	
	
	/**
	 * 构造 Oracle 分页SQL
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
		pageSql.append(" select * from (");
		pageSql.append(" select rownum as r, oracle_table_list.*  from ( ");
		pageSql.append(sql);
		pageSql.append(" ) oracle_table_list where rownum <=");
		pageSql.append(pageNo * pageSize);
		pageSql.append(" )  where r >");
		pageSql.append((pageNo - 1) * pageSize);

		return pageSql.toString();

	}

}
