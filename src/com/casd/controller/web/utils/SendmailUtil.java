package com.casd.controller.web.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendmailUtil  {
	
	// 设置服务器
    private  String KEY_SMTP = "mail.smtp.host";
    private   String VALUE_SMTP = "smtp.qq.com";
    // 服务器验证
    private  String KEY_PROPS = "mail.smtp.auth";
    private  boolean VALUE_PROPS = true;
    // 发件人用户名、密码
    private   String  SEND_USER = "2329980599@qq.com";
    private   String SEND_UNAME = "2329980599@qq.com";
    //授权码   genxcdcuxofocacj    rtfwxhcopshlecha
    private   String SEND_PWD = "rtfwxhcopshlecha";
    // 建立会话
    private   MimeMessage  message;
    private   Session  session;
    	
    
     // 初始化方法
    public SendmailUtil() {
    	try {
	        Properties props = System.getProperties();
	        props.setProperty(KEY_SMTP, VALUE_SMTP);
	        props.put(KEY_PROPS, "true");
	        
	        MailSSLSocketFactory sf = new MailSSLSocketFactory();
	        sf.setTrustAllHosts(true);
	        props.put("mail.smtp.ssl.enable", "true");
	        props.put("mail.smtp.ssl.socketFactory", sf);
	        props.put("mail.smtp.auth", "true");
	        session =  Session.getDefaultInstance(props, new Authenticator(){
	              protected PasswordAuthentication getPasswordAuthentication() {
	                  return new PasswordAuthentication(SEND_UNAME, SEND_PWD);
	              }});
	        session.setDebug(true);
	        message = new MimeMessage(session);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    }

	/**
     * 发送邮件
     * 
     * @param headName
     *            邮件头文件名
     * @param sendHtml
     *            邮件内容
     * @param receiveUser
     *            收件人地址
     */
    public  void doSendHtmlEmail(String headName, String sendHtml,
            String receiveUser) {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(SEND_USER);
            message.setFrom(from);
            //抄送人
            message.setRecipient(Message.RecipientType.CC,  new InternetAddress("2329980599@qq.com"));
            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(headName);
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    
    /***
     * @author mr Liao
     * 单据
     * 发送邮件提醒
     * 
     * 
     * */
    public void doSendHtmlEmail(String string){
    	
    }
    
    
    
    
    
}