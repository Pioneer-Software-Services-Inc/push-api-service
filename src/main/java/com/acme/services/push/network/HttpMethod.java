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
 *  This Java class interface defines constants for Http Method types.
 *  <p>
 */
public @interface HttpMethod {
	  final String GET = "GET";
	  final String POST = "POST";
	  final String PUT = "PUT";
	  final String DELETE = "DELETE";
	  final String HEAD = "HEAD";
	  final String OPTIONS = "OPTIONS";

	  public static final Set<String> validMethodTypes = new HashSet<String>(Arrays.asList(HttpMethod.POST,HttpMethod.GET));
	  public static final String DEFAULT_METHOD = HttpMethod.POST;
	  
	  String value();
}