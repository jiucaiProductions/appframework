package org.jiucai.appframework.base.domain;

import java.io.Serializable;

/**
 * 数据格式 键值对
 * 
 * 2012-02-20 by zhaidw 增加 跨行跨列 属性
 * 
 * @author zhaidw
 * 
 */
public class KeyValuePair implements Serializable {

	private static final long serialVersionUID = 4687824767537555977L;
	
	/**
	 * 列可排序
	 */
	public static final String SORTABLE_ENABLED = "1";
	
	/**
	 * 列不可排序
	 */
	public static final String SORTABLE_DISABLED = "0";
	
	//字段标识
	protected String code;
	
	//父级字段标识
	protected String parentCode;

	//
	protected String calcCode;
	protected String aliasCode;

	//显示文本
	protected String text;

	//跨列属性
	protected Integer colspan;
	
	//跨行属性
	protected Integer rowspan;

	//对齐属性: left,center,right
	protected String align;
	
	//CSS样式名称
	protected String className;

	/**
	 * 是否是数据字段 1 是， 0 否
	 */
	protected Integer dataColumn;
	
	/**
	 * 是否为可排序字段 1是，0否
	 */
	protected String sortable;

	
	public KeyValuePair() {
		super();
	}

	/**
	 * 
	 */
	public KeyValuePair(String code, String text) {
		super();
		this.code = code;
		this.text = text;
	}
	
	/**
	 * 需要构造可排序的数据表格时使用 sortable 是否需要排序的标注  1排序，0不排序
	 */
	public KeyValuePair(String code, String text, String sortable) {
		super();
		this.code = code;
		this.text = text;
		this.sortable = sortable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getCalcCode() {
		return calcCode;
	}

	public void setCalcCode(String calcCode) {
		this.calcCode = calcCode;
	}

	public String getAliasCode() {
		return aliasCode;
	}

	public void setAliasCode(String aliasCode) {
		this.aliasCode = aliasCode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getDataColumn() {
		return dataColumn;
	}

	public void setDataColumn(Integer dataColumn) {
		this.dataColumn = dataColumn;
	}
	
	public String getSequence() {
		return sortable;
	}

	public void setSequence(String sequence) {
		this.sortable = sequence;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeyValuePair [code=");
		builder.append(code);
		builder.append(", parentCode=");
		builder.append(parentCode);
		builder.append(", calcCode=");
		builder.append(calcCode);
		builder.append(", aliasCode=");
		builder.append(aliasCode);
		builder.append(", text=");
		builder.append(text);
		builder.append(", colspan=");
		builder.append(colspan);
		builder.append(", rowspan=");
		builder.append(rowspan);
		builder.append(", align=");
		builder.append(align);
		builder.append(", className=");
		builder.append(className);
		builder.append(", dataColumn=");
		builder.append(dataColumn);
		builder.append(", sequence=");
		builder.append(sortable);
		builder.append("]");
		return builder.toString();
	}

}
