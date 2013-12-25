package org.jiucai.appframework.common.mail;

import java.io.Serializable;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.util.ConfigUtil;
import org.jiucai.appframework.base.util.LocalIPUtil;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;


/**
 * 发邮件线程方法
 * 
 * @author gary
 * 
 */
public class MailThread implements Runnable, Serializable {

	private static final long serialVersionUID = -6405111041020961222L;

	protected static Logs log = LogUtil.getLog(MailSender.class);

	private static String fromTitle;
	private static String mailFrom;

	private String[] mailTo;// 接收人邮件地址
	private String title;// 邮件title
	private String message;// 邮件信息
	private String mailTemplate;// 邮件信息

	static {
		
		Configuration  config = ConfigUtil.addConfig("mail");
		
		fromTitle = config.getString("mail.from_name");
		mailFrom = config.getString("mail.from_mail");
	}
	
	protected static String getDefaultMailTemplate(final String title, final String message){
		
		StringBuffer sb = new StringBuffer("<div style=\"font-size:12px; color: #000000; background-color: #fbfce4; border: 1px solid #eeeeee;\" >\n");

		sb.append("<h1 style=\"font-size: 14px; font-weight:bold;\">").append(title).append("</h1>\n");
		sb.append("<br/>\n");
		
		sb.append("<pre style=\"white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;\">\n");
		
		sb.append(message);
		
		sb.append("</pre>\n");
		
		sb.append("<br/>\n");
		sb.append("<br/>\n------");
		sb.append("<br/>\n");
		
		
		sb.append("<br/>\n邮件来源 : ");
		sb.append(LocalIPUtil.getLocalIP());
		
		sb.append("<br/>\n");
		sb.append("<br/>\n");
		sb.append("</div>\n");
		
		return sb.toString();
	}

	public MailThread() {

	}

	public MailThread(final String[] mailAdds, final String title,final String message) {
		this.title = title;
		this.mailTo = mailAdds;
		this.message = message;
	}
	

	public String getMailTemplate() {
		
		if(StringUtils.isBlank(mailTemplate)){
			mailTemplate = getDefaultMailTemplate(title,message);
			
		}
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public void run() {
		try {
			
			final MailSender sender = new MailSender();
			
			String mailContent = getDefaultMailTemplate(title,message);
			
			sender.sendMailAsHtml(fromTitle, mailFrom, null, mailTo, null,title, mailContent);
			
			log.info("mail sent successed:" + title);
			
		} catch (Exception e) {
			
			log.error("send mail error: " + ExceptionUtils.getFullStackTrace(e));
		}

	}

	public String[] getMailTo() {
		return mailTo;
	}

	public void setMailTo(String[] mailTo) {
		this.mailTo = mailTo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
