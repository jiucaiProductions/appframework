package org.jiucai.appframework.base.mail;

/**
 * 通用回调接口
 * @author dangwei.zhai at 2010-10-27
 *
 */
public interface ICallBack {

	/**
	 * 执行回调方法
	 * @param objects 将处理后的结果作为参数返回给回调方法
	 */
	public void execute(Object ...objects);
}
