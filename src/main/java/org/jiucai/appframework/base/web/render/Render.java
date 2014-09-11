package org.jiucai.appframework.base.web.render;


public interface Render {

	/**
	 * 设置结果数据编码
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding);

	/**
	 * 设置返回数据的 HTTP ContentType
	 * 
	 * @return String
	 */
	public String getContentType();

	/**
	 * 根据对象构造要输出的字符串
	 * 
	 * @param data
	 * @return getString
	 * @throws Exception
	 */
	public String getString(Object data);

}
