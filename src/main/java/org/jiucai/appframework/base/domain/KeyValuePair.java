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

    // 字段标识
    protected String code;

    // 父级字段标识
    protected String parentCode;

    //
    protected String calcCode;
    protected String aliasCode;

    // 显示文本
    protected String text;

    // 跨列属性
    protected Integer colspan;

    // 跨行属性
    protected Integer rowspan;

    // 对齐属性: left,center,right
    protected String align;

    // CSS样式名称
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
     * KeyValuePair
     *
     * @param code
     *            code
     * @param text
     *            text
     */
    public KeyValuePair(String code, String text) {
        super();
        this.code = code;
        this.text = text;
    }

    /**
     * 需要构造可排序的数据表格时使用
     *
     * @param code
     *            code
     * @param text
     *            text
     * @param sortable
     *            是否需要排序的标注 1排序，0不排序
     */
    public KeyValuePair(String code, String text, String sortable) {
        super();
        this.code = code;
        this.text = text;
        this.sortable = sortable;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public String getAlign() {
        return align;
    }

    public String getCalcCode() {
        return calcCode;
    }

    public String getClassName() {
        return className;
    }

    public String getCode() {
        return code;
    }

    public Integer getColspan() {
        return colspan;
    }

    public Integer getDataColumn() {
        return dataColumn;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public String getSequence() {
        return sortable;
    }

    public String getText() {
        return text;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCalcCode(String calcCode) {
        this.calcCode = calcCode;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public void setDataColumn(Integer dataColumn) {
        this.dataColumn = dataColumn;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public void setSequence(String sequence) {
        sortable = sequence;
    }

    public void setText(String text) {
        this.text = text;
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
