package org.jiucai.appframework.base.mail;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;

/**
 * 邮件发送任务类，可以支持同步和异步发送邮件
 * 
 * @author dangwei.zhai at 2010-10-29
 * 
 */
public class MailTask implements ICallBack, Runnable {

	/**
	 * 异步发送邮件，需采用回调方法获取邮件发送结果
	 */
	public static final String SEND_ASYNC = "asynchronous";

	/**
	 * 同步发送邮件，通过 result 属性获取邮件发送结果
	 */
	public static final String SEND_SYNC = "synchronized";

	protected Log log = LogFactory.getLog(getClass());

	private MailSender sender;
	private ICallBack callBack;

	private String sendType;
	private String result;

	public MailTask() {

	}

	public MailTask(MailSender sender) {
		this.sender = sender;
	}

	public String send() throws EmailException {
		log.debug("send() called ,thread name: "
				+ Thread.currentThread().getName());

		if (null == sendType) {
			sendType = SEND_SYNC;
		}
		if (SEND_ASYNC.equals(sendType)) {
			Thread thread = new Thread(this);
			thread.start();

		} else if (SEND_SYNC.equals(sendType)) {
			result = sender.send();
		} else {
			System.err.println("Error sendType. ");
		}

		return result;

	}

	/**
	 * 默认回调方法
	 */
	public void execute(Object... objects) {
		result = (String) objects[0];
		log.debug("execute called ,thread name: "
				+ Thread.currentThread().getName() + " , result: " + result);
		Thread.interrupted();
	}

	public void run() {
		if (null == callBack) {
			sender.setCallBack(this);
		} else {
			sender.setCallBack(callBack);
		}
		try {
			if (SEND_ASYNC.equals(sendType)) {
				result = sender.send();
			} else {
				result = "Mail SendType is not " + SEND_ASYNC
						+ " , Thread canceled. ";
				throw new EmailException(result);
			}
		} catch (EmailException e) {
			result = ExceptionUtils.getRootCauseMessage(e);
			// log.error(result);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
	}

	public String getResult() {
		return result;
	}

	public ICallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

}
