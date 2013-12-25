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
	 * 计算页数
	 * 
	 * @param resultSize 记录数
	 * @param pageSize 每页显示的记录数
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
	 * @return 当前页码
	 */
	public static final Long getPage(Long pageStart, Long pageSize) {

		return SqlUtil.getPage(pageStart, pageSize);
	}

	/**
	 * 获取每页展示数据记录数
	 * 
	 * @return 每页的记录数
	 */
	public static final Long getPageSize() {
		return SqlUtil.getPageSize();
	}

}
