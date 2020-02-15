/**
 * 
 */
package com.acme.services.push.file.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.acme.services.push.file.DataTransferObject;

/**
 * @author cgordon
 * @created 10/18/2017
 * @version 1.0
 *
 * Null Object design pattern as safety against passing around and handling null values.
 */
public class NullParser extends GenericFileParser{

	@Override
	public List<Set<DataTransferObject>> parseXml(File xmlFile, String root)
			throws ParserConfigurationException, IOException, SAXException {

		return new ArrayList<Set<DataTransferObject>>();
	}

}
