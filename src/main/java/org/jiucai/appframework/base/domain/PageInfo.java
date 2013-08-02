package org.jiucai.appframework.base.domain;

import java.io.Serializable;

/**
 * 分页对象
 * @author zhaidw
 *
 */
public class PageInfo implements Serializable {

	private static final long serialVersionUID = -1763402983405186186L;
	private Long resultSize;
	private Long pageCount;
	private Long pageNo;
	private Long pageSize;

	public PageInfo() {
		super();
	}

	public PageInfo(Long resultSize, Long pageCount, Long pageNo,
			Long pageSize) {
		super();
		this.resultSize = resultSize;
		this.pageCount = pageCount;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Long getResultSize() {
		return resultSize;
	}

	public void setResultSize(Long resultSize) {
		this.resultSize = resultSize;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "PageInfo [resultSize=" + resultSize + ", pageCount="
				+ pageCount + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ "]";
	}

}
