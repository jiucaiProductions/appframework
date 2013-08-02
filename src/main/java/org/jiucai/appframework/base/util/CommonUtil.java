package org.jiucai.appframework.base.util;

/**
 * 
 * @author zhaidw
 *
 * @deprecated since 1.1.4 renamed to {@link org.jiucai.appframework.base.util.SqlUtil}
 */
@Deprecated
public class CommonUtil {

	/**
	 * 构造Oracle分页SQL
	 * 
	 * @param sql
	 *            原始SQL
	 * @param page
	 *            页码 默认 1
	 * @param pageSize
	 *            每页记录数 默认10
	 * @return
	 */
	public static String getPagedSQL(String sql, Long page, Long pageSize) {

		if (null == page || page < 1) {
			page = 1L;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = 10L;
		}

		StringBuffer pageSql = new StringBuffer();
		pageSql.append(" select /*+ FIRST_ROWS */ *  from (");
		pageSql.append(" select rownum as r, oracle_table_list.*  from ( ");
		pageSql.append(sql);
		pageSql.append(" ) oracle_table_list where rownum <=");
		pageSql.append(page * pageSize);
		pageSql.append(" )  where r >");
		pageSql.append((page - 1) * pageSize);

		return pageSql.toString();

	}

	/**
	 * 计算页数
	 * 
	 * @param resultSize
	 *            记录数
	 * @param pageSize
	 *            每页显示的记录数
	 * @return 总页数
	 */
	public static final Long getPageCount(Long resultSize, Long pageSize) {

		return SqlUtil.getPageCount(resultSize, pageSize);
	}

	/**
	 * 计算当前页
	 * 
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	public static final Long getPage(Long pageStart, Long pageSize) {

		return SqlUtil.getPage(pageStart, pageSize);
	}

	/**
	 * 获取每页展示数据记录数
	 * 
	 * @return
	 */
	public static final Long getPageSize() {
		return SqlUtil.getPageSize();
	}

}
