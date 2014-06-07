package org.jiucai.appframework.base.domain;

import java.io.Serializable;

/**
 * 所有Domain对象的基类
 * @author zhaidw
 *
 */
public abstract class BaseDomain implements Serializable {

	private static final long serialVersionUID = 1809437478796265606L;
	private Long r;
	
	
	public BaseDomain() {
		super();
	}

	/**
	 * 数据库查询返回结果的唯一行号
	 * @return Long
	 */
	public Long getR() {
		return r;
	}

	public void setR(Long r) {
		this.r = r;
	}
	
}
