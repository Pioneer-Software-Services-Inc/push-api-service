/**
 * 
 */
package com.acme.services.push.file.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import static com.acme.services.push.facade.FunctionFacade.*;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.file.DataTransferObject;
import com.acme.services.push.model.beans.Attributes;
import com.acme.services.push.model.beans.UserAlias;

/**
 * @author cgordon
 * @created 10/17/2017
 * @version 1.0
 *
 * Concrete implementation of the <code>GenericFileParser</code> parent class that performs parse on the User Track file feed. 
 *
 */
public class TrackFileParser extends GenericFileParser{

	/** This method is tasked with parsing non order the input xml file and returning a map of  
	 * 
	 * @param data as a XML input <code>File</code> type class 
	 * @returns <code>DataTransferObject</code> Data Transfer Object pattern containing the organized data for populating thev request 
	 * object to send to push. 
	 * @exception <code>ParserConfigurationException</code> If an ParserConfigurationException error occurs.
	 * @exception <code>IOException</code> If an IOException error occurs.
	 * @exception <code>SAXException</code> If an SAXException error occurs.  
	 */	
	@SuppressWarnings("unchecked")
	public List<Set<DataTransferObject>> parseXml(@NotNull File xmlFile, final @NotNull String root) 
	throws ParserConfigurationException, IOException, SAXException{
		logger.info("\nProcess Non Order document started\n");

		final long start = System.nanoTime();

		List<Set<DataTransferObject>> data = null;
		Set<DataTransferObject> nodes = null;
		
		Hashtable<String, String> props = new Hashtable<String, String> ();
		List<String> fieldsKeys = getResourcePropertyList(String.format(DATA_FEED_REFERENCE_FEED_FIELDS_LIST, root));
        
        fieldsKeys.parallelStream().map(f -> {
        	Hashtable<String, String> values = new Hashtable<String, String> ();
        	values.put(f, getResourceProperty(String.format(DATA_FEED_REFERENCE_FIELD_VALUES, root, f)));
            return values ;
        }).forEach(map -> props.putAll(map)); 		
		
		DataTransferObject datafield = new DataTransferObject();
		datafield.setFields(props);
		
		nodes = new HashSet<DataTransferObject>();
		nodes.add(datafield);

		logger.debug("Root element : {}", root);
		String removeCharList = getResourceProperty(DATA_CLEAN_REPLACE_CHAR_LIST);
		List<Hashtable<String, String>> userDetails = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> nodeProps = new Hashtable<String, String>();

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		long records = 0;
		try {
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new FileInputStream(xmlFile));

			streamReader.nextTag();
			streamReader.nextTag();     
			
			String record = getResourceProperty(String.format(DATA_FEED_REFERENCE_FIELD_DATA_ROOT, root));

			logger.debug("Root record element : {}", record);
			while (streamReader.hasNext()) {

				if (streamReader.isStartElement()) {
					
					if(streamReader.getLocalName().equalsIgnoreCase(record)) {
						records++;
						if (!nodeProps.isEmpty()) userDetails.add(nodeProps);
						
						nodeProps = (Hashtable<String, String>) nodeProps.clone();
					}else {
						nodeProps.put(streamReader.getLocalName(), streamReader.getElementText().replaceAll(removeCharList, ""));
					}
				}
		
				streamReader.next();
			}
			if (!nodeProps.isEmpty()) userDetails.add(nodeProps); //add the last record
		}catch(Exception e) {
			logger.error("error reading file {}", e);
		}
		
		logger.info("node <list>: {} \n", printList(userDetails));
		
        List<Attributes> transformed = new ArrayList<Attributes>() ;
        userDetails.stream().map(map -> {
            Attributes newData = new Attributes();
            
            newData.setEmail(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_EMAIL_KEY, Constants.LABEL_TRACK_TYPE)))));
            newData.setFirst_name(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_FIRSTNAME_KEY, Constants.LABEL_TRACK_TYPE)))));
            newData.setLast_name(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_FIRSTNAME_KEY, Constants.LABEL_TRACK_TYPE)))));            
            newData.setPhone(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_PHONE_KEY, Constants.LABEL_TRACK_TYPE)))));
            newData.setSource(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_SOURCE_KEY, Constants.LABEL_TRACK_TYPE)))));
            newData.setSms_option(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_SMSOPT_KEY, Constants.LABEL_TRACK_TYPE)))));
            newData.setUser_alias(new UserAlias(String.valueOf(map.get(getResourceProperty(String.format(DATA_MAP_EMAIL_KEY, Constants.LABEL_TRACK_TYPE))))));
            return newData;
        }).forEach(map -> transformed.add(map)); 
		
        data = new ArrayList<Set<DataTransferObject>>();
        
		datafield.setAttributes(transformed);
		nodes.add(datafield);
		data.add(nodes);
		logger.info("node <list> post process: {} \n", printList(data));

		logger.debug("\n#######\n receiver generic push data added: {}, records added: {} \n#######\n", nodes, records);	
		
		logger.info(" Parse (Track) xml elapse time: {} (milliseconds) for file {}", 
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS), xmlFile.getName());
		logger.info("XML document parse ended\n");	
		
		return data;
	}
}
