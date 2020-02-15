package com.acme.services.push.network;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 *  <p>
 *  
 *  This Java class interface defines constants for Content types.
 *  <p>
 */
public @interface ContentType {
	  final String JSON = "application/json";
	  final String HTML = "text/html";
	  final String PLAIN = "text/plain";
	  final String XML = "application/xml";

	  public static final Set<String> validContentTypes = new HashSet<String>(Arrays.asList(ContentType.JSON,ContentType.XML));
	  public static final String DEFAULT_CONTENTTYPE = ContentType.JSON;
	  
	  String value();
}