/**
 * 
 */
package com.acme.services.push.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *  
 * Creates custom notification for email status to support notification email list 
 */
public class Status{

	private static transient final Logger logger = LoggerFactory.getLogger(Status.class);

	/**
	 * This adds a message to the log4J file appender which eventually is used to 
	 * construct the body of notification email.
	 * 
	 * @param message message body of email
	 */
	public static void trace(String message) {
		
		logger.info(message);
	}
}
