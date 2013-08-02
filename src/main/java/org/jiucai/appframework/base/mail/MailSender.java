package org.jiucai.appframework.base.mail;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jiucai.appframework.base.util.ConfigUtil;

/**
 * 邮件发送封装类
 * @author dangwei.zhai at 2010-10-29
 *
 */
public class MailSender {
	
	protected Log log= LogFactory.getLog(getClass());
	
	private ICallBack callBack;
	
	//支持中文的字符集都可以，gbk 和 utf-8都可以
	private String charset = "UTF-8";
	
	private List<EmailAttachment> attachList;
	
	private HtmlEmail mail;
	
	
	private String subject;
	private String msg;
	private String toMail;
	private String toUserName;
	private String fromMail;
	private String fromUserName;
	
	private String smtpHost;
	private int smtpPort;
	private String authUserName;
	private String authPassword;
	
	private static Configuration cfg;
	
	static{
		//获取邮件配置
		cfg = ConfigUtil.addConfig("mail");
		
	}
	
	public MailSender(){
		mail = new HtmlEmail();
		mail.setDebug(false);
		
	}
	
	public String getConfigString(String key ){
		return cfg.getString(key);
	}
	
	public int getConfigInteger(String key ){
		return cfg.getInt(key);
	}
	
	public String send() throws EmailException{
		String result;
		try{
			
			if( null == getToMail()){
				throw new EmailException("Receiver mail address required.");
			}
			
			if( null == getFromMail()){
				throw new EmailException("Sender mail address required.");
			}
			
			mail.setHostName(getSmtpHost());	
			if(getSmtpPort() > 0){
				mail.setSmtpPort(getSmtpPort());
			}
			
			mail.setAuthentication(getAuthUserName(), getAuthPassword());
			
			mail.setSubject(getSubject());
			mail.setHtmlMsg(getMsg());
			mail.setTextMsg("Your mail client does not support HTML format mail. Try Microsoft Outlook or other mail client. ");
			
//			mail.setFrom(GlobalConst.EMAIL_DEFAULT, "学士后平台", charset);

			mail.setFrom(getFromMail(), getFromUserName(), getCharset());
			mail.addTo(getToMail(), getToUserName(), getCharset());
			mail.setCharset(getCharset());
			
			
			if(null != getAttachList() && getAttachList().size() > 0){
				EmailAttachment attachment;
				for (int i = 0; i < getAttachList().size(); i++) {
					attachment = getAttachList().get(i);
					if( null != attachment.getPath() || null != attachment.getURL()){
						mail.attach(attachment);
					}
				}
				
			}
			
			result = mail.send();
			
		}catch(EmailException e){
			result = ExceptionUtils.getRootCauseMessage(e);
			System.err.println(result);
			//log.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
		
		if(null != callBack){
			callBack.execute(result);
		}
		
		return result;
		
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}

	public List<EmailAttachment> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<EmailAttachment> attachList) {
		this.attachList = attachList;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public HtmlEmail getmail() {
		return mail;
	}

	public void setMail(HtmlEmail mail) {
		this.mail = mail;
	}
	
	
	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getToMail() {
		return toMail;
	}


	public void setToMail(String toMail) {
		this.toMail = toMail;
	}


	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}


	public String getFromMail() {
		if(null== fromMail){
			fromMail = getConfigString("mail.from_mail");
		}
		return fromMail;
	}


	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}


	public String getFromUserName() {
		if(null == fromUserName){
			fromUserName = getConfigString("mail.from_name");
		}
		return fromUserName;
	}


	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}


	public ICallBack getCallBack() {
		return callBack;
	}


	public HtmlEmail getMail() {
		return mail;
	}

	public String getAuthPassword() {
		if(null == authPassword){
			authPassword = getConfigString("mail.password");
		}
		return authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public String getAuthUserName() {
		if(null == authUserName){
			authUserName = getConfigString("mail.user_name");
		}
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
		
	}

	public String getSmtpHost() {
		if(null == smtpHost){
			smtpHost = getConfigString("mail.smtp.host");
		}
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		if(smtpPort < 1){
			smtpPort = getConfigInteger("mail.smtp.port");
		}
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}
	
}
