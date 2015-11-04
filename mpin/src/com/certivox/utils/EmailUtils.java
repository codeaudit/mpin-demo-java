/*
 Copyright 2014 CertiVox UK Ltd, All Rights Reserved.
 
 The CertiVox M-Pin Client and Server Libraries are free software: you can
 redistribute it and/or modify it under the terms of the BSD 3-Clause
 License - http://opensource.org/licenses/BSD-3-Clause
 
 For full details regarding our CertiVox terms of service please refer to
 the following links:
 
 * Our Terms and Conditions -
 http://www.certivox.com/about-certivox/terms-and-conditions/
 
 * Our Security and Privacy -
 http://www.certivox.com/about-certivox/security-privacy/
 
 * Our Statement of Position and Our Promise on Software Patents -
 http://www.certivox.com/about-certivox/patents/
*/


package com.certivox.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

	public static void sendConfirmationEmail(String toEmail, String url) {
		try {
			
			Config config = Config.getInstance();
			
			String host = config.get(Constants.SMTP_HOST);
			String port = config.get(Constants.SMTP_PORT);
			final String username  = config.get(Constants.SMTP_USERNAME);
			final String password = config.get(Constants.SMTP_PASSWORD);
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.enable","true");
			props.put("mail.smtp.host",host);
			props.put("mail.smtp.port",port);
			props.put("mail.debug","false");
	 
			Session session = Session.getInstance(props,new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

			Message message = new MimeMessage(session);		
			
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress(toEmail);  
			message.setRecipients(Message.RecipientType.TO, to);
			    
			message.setSubject("RPA DEMO M-Pin: New user activation");
			message.setContent("<br/><b>M-Pin Strong Authentication Platform</b><br/><br/>Your identity is now ready to activate:<br/><br/><a href=\""+url+"\" target=\"_blank\">Click this activation link and follow the instructions</a><br/><br/>Regards,<br/>The M-Pin Team at CertiVox", "text/html");				
			
			Transport.send(message);	
						
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
	}
}
