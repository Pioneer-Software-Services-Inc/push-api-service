/**
 * 
 */
package com.acme.services.push.file.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.acme.services.push.file.DataTransferObject;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 * This regular interface defines file parser parent type interface.  
 * The purpose of this (interface) parent type is define abstract methods and provide common functions in order to to parse various input xml document 
 * and extract the data and then place its contents in a (convenience) data transfer object.
 *
 */
public interface FileParser {


	/** This method is tasked with parsing order input xml file and returning a map of  
	 * 
	 * @param data as a XML input <code>File</code> type class 
	 * @returns <code>DataTransferObject</code> Data Transfer Object pattern containing the organized data for populating thev request 
	 * object to send to push. 
	 * @exception <code>ParserConfigurationException</code> If an ParserConfigurationException error occurs.
	 * @exception <code>IOException</code> If an IOException error occurs.
	 * @exception <code>SAXException</code> If an SAXException error occurs.  
	 */	
	public List<Set<DataTransferObject>> parseXml(File xmlFile, String root) throws ParserConfigurationException, IOException, SAXException ;
}
