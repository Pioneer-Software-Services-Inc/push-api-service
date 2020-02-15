/**
 * 
 */
package com.acme.services.push.file.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.file.DataTransferObject;

/**
 * @author cgordon
 * @created 10/16/2017
 * @version 1.0
 * 
 * The purpose of this class is to parse the track input xml (non-order) document and extract the data and then place its contents 
 * in a (convenience) data transfer object.
 *
 */
public abstract class GenericFileParser implements FileParser{

	protected static transient final Logger logger = LoggerFactory.getLogger(GenericFileParser.class);
	protected static final byte ONE = (byte)1;
	protected static final byte ZERO = (byte)0;
	

	/** This method is tasked with parsing order input xml file and returning a map of  
	 * 
	 * @param data as a XML input <code>File</code> type class 
	 * @returns <code>DataTransferObject</code> Data Transfer Object pattern containing the organized data for populating thev request 
	 * object to send to push. 
	 * @exception <code>ParserConfigurationException</code> If an ParserConfigurationException error occurs.
	 * @exception <code>IOException</code> If an IOException error occurs.
	 * @exception <code>SAXException</code> If an SAXException error occurs.  
	 */	
	public abstract List<Set<DataTransferObject>> parseXml(@NotNull File xmlFile, @NotNull final String root) throws ParserConfigurationException, IOException, SAXException;
	
	/**
	 * Converts map keys to lower case for simplicity and consistency. Error checks to ensure no null.
	 * 
	 * @param key <code>String</code> value to be converted to lower case
	 * @return <code>String</code> output converted key to lower case
	 */
	protected static String formatKey(String key){
		
		return key!=null? key.toLowerCase() : Constants._BLANK;
	}

	/**
	 * Converts map keys to lower case for simplicity and consistency. Error checks to ensure no null.
	 * also adds a prefix label to the input so as to conform to key value map required by Push
	 * 
	 * @param key <code>String</code> value to be converted to lower case
	 * @param recipient index int primitive 
	 * @return <code>String</code> output converted key to lower case
	 */
	protected static String formatKey(String key, int rec_index){
		
		return formatKey(String.format("REC_%s_%s", rec_index==ZERO? ONE : rec_index,  (key!=null? key : Constants._BLANK)));
	}

	/**
	 * Converts map keys to lower case for simplicity and consistency. Error checks to ensure no null.
	 * also adds a prefix label(s) to the input so as to conform to key value map required by Push 
	 * 
	 * @param key <code>String</code> value to be converted to lower case
	 * @param recipient index int primitive
	 * @param product item index int primitive
	 * @return <code>String</code> output converted key to lower case
	 */
	protected static String formatKey(String key, int rec_index, int itm_index){
		
		return formatKey(String.format("REC_%s_ITEM_%s_%s", rec_index==ZERO? ONE : rec_index, itm_index, (key!=null? key : Constants._BLANK)));
	}
	
}
