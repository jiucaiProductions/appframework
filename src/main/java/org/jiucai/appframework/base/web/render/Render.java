package org.jiucai.appframework.base.web.render;

/**
 * Render
 *
 * @author zhaidangwei
 *
 */
public interface Render {

    /**
     * 设置返回数据的 HTTP ContentType
     *
     * @return String ContentType
     */
    public String getContentType();

    /**
     * 根据对象构造要输出的字符串
     *
     * @param data
     *            object to convert
     * @return getString
     */
    public String getString(Object data);

    /**
     * 设置结果数据编码
     *
     * @param encoding
     *            output encoding
     */
    public void setEncoding(String encoding);

}
