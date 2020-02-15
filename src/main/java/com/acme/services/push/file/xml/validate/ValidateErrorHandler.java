/**
 * 
 */
package com.acme.services.push.file.xml.validate;

import javax.validation.constraints.NotNull;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 * Error handler implementation of the SAX (XML)<code>ErrorHandler</code>
 */
public class ValidateErrorHandler implements ErrorHandler {
	
	/** This method creates a custom (WARN)log message 
	 * 
	 * @param XML exception <code>SAXParseException</code> type class 
	 * @exception <code>SQLException</code> If an SQLException error occurs.
	 */	
    public void warning(@NotNull SAXParseException e) throws SAXException {
    	System.out.printf("WARN: Exception: %s, Msg: %s ", e.getClass().getCanonicalName(), e.getMessage());
        throw new SAXException(e.getMessage());        
    }

	/** This method creates a custom (ERROR)log message 
	 * 
	 * @param XML exception <code>SAXParseException</code> type class 
	 * @exception <code>SQLException</code> If an SQLException error occurs.
	 */	
    public void error(@NotNull SAXParseException e) throws SAXException {
    	System.out.printf("ERROR: Exception: %s, Msg: %s ", e.getClass().getCanonicalName(), e.getMessage());
        throw new SAXException(e.getMessage());        
    }

	/** This method creates a custom (FATAL)log message 
	 * 
	 * @param XML exception <code>SAXParseException</code> type class 
	 * @exception <code>SQLException</code> If an SQLException error occurs.
	 */	
    public void fatalError(@NotNull SAXParseException e) throws SAXException {
    	System.out.printf("FATAL: Exception: %s, Msg: %s ", e.getClass().getCanonicalName(), e.getMessage());
        throw new SAXException(e.getMessage());        
    }
}