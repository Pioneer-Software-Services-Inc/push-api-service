/**
 * 
 */
package com.acme.services.push.file.xml.validate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.file.CharacterSet;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *  
 * Provides a framework for validation of input xml fileUtil and strings. 
 */
public class Validate {

	private static transient final Logger logger = LoggerFactory.getLogger(Validate.class);

	/** Performs xml validation of input xml. performs a simple Domain Object Model validation
	 * for xml well formed 
	 * 
	 * @param xml <code>String</code> to be parsed 
	 * @return boolean (primitive) flag of successful xml parse.
	 * @exception <code>IOException</code>
	 */	
	public boolean isValidXmlString(@NotNull String parseXml) throws IOException{

		if(!Optional.of(parseXml).isPresent()) return Boolean.FALSE;

		parseXml = getCleanedXml(parseXml);

		try(ByteArrayInputStream bos = new ByteArrayInputStream(parseXml.getBytes(CharacterSet.UTF_8))){
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(false);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ValidateErrorHandler());    
			// the "parse" method also validates XML, will throw an exception if misformatted
			builder.parse(new InputSource(bos));
		}catch(ParserConfigurationException | SAXException e){
			logger.error(" xml input fail (parse) validation not well formed. {}", e.getMessage());
			logger.error(parseXml);
			return false;
		}

		return true;
	}

	/** Performs xml validation of input xml file <File> by using a DOM validation 
	 * 
	 * @param xml <code>File</code> to be parsed 
	 * @return boolean (primitive) flag of successful xml parse.
	 * @exception <code>IOException</code>
	 */	
	public boolean isValidXmlFile(@NotNull File xmlFile) throws IOException{

		logger.debug(" xml file validation against for xml well formed:");

		return isValidXmlString(getCleanedXml(readEncodeXmlFile(xmlFile)));
	}

	/** 
	 * Uses a regular expression to remove potentially malicious elements from input XML 
	 * Remove problematic xml entities from the xml string so that you can parse it with java DOM / SAX libraries.
	 * 
	 * @param xml <code>String</code> to be parsed 
	 * @return <code>String</code> with cleaned XML.
	 */	
	private String getCleanedXml(@NotNull String xmlString) {

		Matcher m = Constants.XML_ENTITY_PATTERN.matcher(xmlString);
		Set<String> replaceSet = new HashSet<String>();
		
		while (m.find()) {
			String group = m.group(1);
			int val;
			if (group != null) {
				val = Integer.parseInt(group, 16);
				if (isInvalidXmlChar(val)) {
					replaceSet.add("&#x" + group + ";");
				}
			} else if ((group = m.group(2)) != null) {
				val = Integer.parseInt(group);
				if (isInvalidXmlChar(val)) {
					replaceSet.add("&#" + group + ";");
				}
			}
		}

		return replaceSet.stream().reduce(xmlString, (s, e) -> s.replaceAll(s, Constants._BLANK), (s1, s2) -> Constants._BLANK);
	}

	/**
	 * Helper method to calculate if a value is invalid
	 * 
	 * @param val as primitive int
	 * @return boolean flag indicating valid or not
	 */
	private boolean isInvalidXmlChar(int val) {
		if (val == 0x9 || val == 0xA || val == 0xD ||
				val >= 0x20 && val <= 0xD7FF ||	val >= 0x10000 && val <= 0x10FFFF) {
			return false;
		}
		return true;
	}

}