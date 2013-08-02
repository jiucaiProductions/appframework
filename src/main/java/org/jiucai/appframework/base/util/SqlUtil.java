package org.jiucai.appframework.base.util;

public class SqlUtil {

	/**
	 * 计算页数
	 * 
	 * @param resultSize 记录数
	 * @param pageSize  每页显示的记录数
	 * @return 总页数
	 */
	public static final Long getPageCount(Long resultSize, Long pageSize) {

		Long pageCount = 1L;

		if (resultSize % pageSize == 0) {
			pageCount = resultSize / pageSize;
		} else {
			pageCount = (resultSize / pageSize) + 1;
		}

		return pageCount;
	}

	/**
	 * 计算当前页
	 * 
	 * @param pageStart
	 * @param pageSize
	 * @return 当前页码
	 */
	public static final Long getPage(Long pageStart, Long pageSize) {
		Long page = 1l;
		page = ((pageStart - 1) / pageSize) + 1;
		return page;
	}

	/**
	 * 获取每页展示数据记录数
	 * 
	 * @return 每页的记录数
	 */
	public static final Long getPageSize() {
		return 10L;
	}

}
