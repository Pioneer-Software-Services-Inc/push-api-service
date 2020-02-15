/**
 * 
 */
package com.acme.services.push.network;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.exception.Handler;

/**
 * @author cgordon
 * @created 08/10/2017
 * @version 1.0
 * 
 * 
 * send mail utility class for the purpose of sending email notification of any errors encountered
 *
 */
public class SendMail {

	private static transient final Logger logger = LoggerFactory.getLogger(SendMail.class);

	/**
	 * This static method accepts a <code>String</code> object email message body. 
	 * It uses the configuration file to setup email config fields. Email content is html formatted. 
	 * 
	 * @param <code>String</code> value of the email message body.
	 */	
	public static void send(String mesg) {

		logger.debug("attempting to send email notification...., {}", mesg);
		if(!Boolean.parseBoolean(getResourceProperty(SMTP_LOGGING_ENABLED)))
			return;

		// Recipient's email ID needs to be mentioned.
		String to = getResourceProperty(SMTP_LOGGING_TO);

		// Sender's email ID needs to be mentioned
		String from = getResourceProperty(SMTP_LOGGING_FROM);

		// email host address specify
		String host = getResourceProperty(SMTP_LOGGING_HOST);

		// email port number specify      
		String port = getResourceProperty(SMTP_LOGGING_PORT);

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail host server details
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.smtps.auth", "false");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(getResourceProperty(SMTP_LOGGING_SUBJECT));

			// Now set the actual message
			message.setContent(String.format("<h1>Email Message</h1><br><br><p>%s<p>",mesg), "text/html");

			logger.debug("verifying email notification message contents..");
			Handler.uncheckedVoid(() ->  message.getContent());
			
			// Send message
			Transport.send(message);
			logger.info("email notification send successfully...., {}", mesg);
		}catch (MessagingException mex) {
			logger.error("send email notification FAILED w/ exception...., {}", mex);
		}
	}
}
