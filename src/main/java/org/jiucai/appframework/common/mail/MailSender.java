package org.jiucai.appframework.common.mail;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.jiucai.appframework.base.util.ConfigUtil;
import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送类,支持文本和HTML格式,多个附件
 *
 * @author gary
 * @version 1.0
 */
public class MailSender {

    protected static Logs log = LogUtil.getLog(MailSender.class);

    private final JavaMailSenderImpl javaMailSender;
    private final MimeMessageHelper mimeMessageHelper;
    // 邮件服务器信息
    private static String host;
    private static String protocol;
    private static Integer port;
    // 邮件服务器验证信息
    private static String userName;
    private static String password;
    private static Boolean isNeedAuth;

    static {

        Configuration config = ConfigUtil.addConfig("mail");

        host = config.getString("mail.smtp.host");
        protocol = config.getString("mail.smtp.protocol");
        port = config.getInt("mail.smtp.port", 25);

        userName = config.getString("mail.user_name");
        password = config.getString("mail.password");
        isNeedAuth = config.getBoolean("mail.smtp.is_need_auth", true);

    }

    /**
     * 使用默认参数发送邮件
     *
     * @throws Exception
     *             Exception
     */
    public MailSender() throws Exception {
        this(true);
    }

    /**
     * 使用发送邮件是否带附件
     *
     * @param isMultipart
     *            isMultipart
     * @throws Exception
     *             Exception
     */
    private MailSender(final boolean isMultipart) throws Exception {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setPort(port);
        if (isNeedAuth) {// 如果需要服务器需要验证
            javaMailSender.setUsername(userName);
            javaMailSender.setPassword(password);
            final Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            javaMailSender.setJavaMailProperties(p);
        }
        mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), isMultipart,
                "GBK");
    }

    /**
     * 发送html格式邮件,不带附件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mailCCs
     *            抄送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @exception Exception
     *                Exception
     */
    public void sendMailAsHtml(final String fromTitle, final String mailFrom,
            final String[] toTitles, final String[] mailTos, final String[] mailCCs,
            final String subject, final String strText) throws Exception {
        sendMail(fromTitle, mailFrom, toTitles, mailTos, mailCCs, subject, strText, true, null,
                true);
    }

    /**
     * 发送html格式邮件,不带附件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mailCCs
     *            抄送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @param isCC
     *            抄送还是暗送
     *
     * @exception Exception
     *                Exception
     */
    public void sendMailAsHtml(final String fromTitle, final String mailFrom,
            final String[] toTitles, final String[] mailTos, final String[] mailCCs,
            final String subject, final String strText, final boolean isCC) throws Exception {
        sendMail(fromTitle, mailFrom, toTitles, mailTos, mailCCs, subject, strText, true, null,
                isCC);
    }

    /**
     * 发送html邮件,带附件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mailCCs
     *            抄送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @param fileTable
     *            附件表key为文件名value为File对象
     * @exception Exception
     *                Exception
     */
    public void sendMailAsHtmlHasAtts(final String fromTitle, final String mailFrom,
            final String[] toTitles, final String[] mailTos, final String[] mailCCs,
            final String subject, final String strText, final Hashtable<String, File> fileTable)
            throws Exception {
        sendMail(fromTitle, mailFrom, toTitles, mailTos, mailCCs, subject, strText, true,
                fileTable, true);
    }

    /**
     * 发送普通文本邮件,不带附件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mailCCs
     *            抄送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @exception Exception
     *                Exception
     */
    public void sendMailAsText(final String fromTitle, final String mailFrom,
            final String[] toTitles, final String[] mailTos, final String[] mailCCs,
            final String subject, final String strText) throws Exception {
        sendMail(fromTitle, mailFrom, toTitles, mailTos, mailCCs, subject, strText, false, null,
                true);
    }

    /**
     * 发送文本邮件,带附件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mailCCs
     *            抄送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @param fileTable
     *            附件表key为文件名value为File对象
     *
     * @exception Exception
     *                Exception
     */
    public void sendMailAsTextHasAtts(final String fromTitle, final String mailFrom,
            final String[] toTitles, final String[] mailTos, final String[] mailCCs,
            final String subject, final String strText, final Hashtable<String, File> fileTable)
            throws Exception {
        sendMail(fromTitle, mailFrom, toTitles, mailTos, mailCCs, subject, strText, false,
                fileTable, true);
    }

    /**
     * 发送邮件
     *
     * @param fromTitle
     *            发件人昵称
     * @param mailFrom
     *            发件人地址
     * @param toTitles
     *            收件人昵称
     * @param mailTos
     *            收件人地址
     * @param mails
     *            抄送或暗送地址
     * @param subject
     *            主题
     * @param strText
     *            邮件内容
     * @param html
     *            邮件格式
     * @param fileTable
     *            附件表key为文件名value为File对象
     * @exception Exception
     *                Exception
     */
    private void sendMail(final String fromTitle, final String mailFrom, final String[] toTitles,
            final String[] mailTos, final String[] mails, final String subject,
            final String strText, final boolean html, final Hashtable<String, File> fileTable,
            final boolean isCC) throws Exception {
        if (null != mailTos) {
            if (null == toTitles) {
                mimeMessageHelper.setTo(mailTos);
            } else {
                for (int i = 0; i < mailTos.length; i++) {
                    if (i > toTitles.length || toTitles.length == 0) {
                        mimeMessageHelper.addTo(mailTos[i]);
                    } else {
                        mimeMessageHelper.addTo(mailTos[i], toTitles[i]);
                    }
                }
            }
        }
        if (null != mailFrom) {
            if (null != fromTitle) {
                mimeMessageHelper.setFrom(mailFrom, fromTitle);
            } else {
                mimeMessageHelper.setFrom(mailFrom);
            }
        }
        if (null != mails) {
            if (isCC) {
                mimeMessageHelper.setCc(mails);
            } else {
                mimeMessageHelper.setBcc(mails);
            }
        }
        if (null != subject) {
            mimeMessageHelper.setSubject(subject);
        }
        if (null != strText) {
            if (html) {
                mimeMessageHelper.setText(strText, true);
            } else {
                mimeMessageHelper.setText(strText, false);
            }
        }
        if (null != fileTable) {
            final Enumeration<String> e = fileTable.keys();
            while (e.hasMoreElements()) {
                final String key = e.nextElement();
                final File value = fileTable.get(key);
                mimeMessageHelper.addAttachment(key, value);
            }
        }

        int retryTime = 3000;
        // 重试3次
        for (int i = 0; i < 3; i++) {
            try {
                log.info("进行第 " + (i + 1) + " 次邮件发送尝试...");
                javaMailSender.send(mimeMessageHelper.getMimeMessage());
                log.info("邮件发送成功");
                break;
            } catch (Throwable e) {
                log.error("邮件发送失败 " + retryTime + " 秒后重试...", e);
                Thread.sleep(retryTime);
            }

        }

    }

}
