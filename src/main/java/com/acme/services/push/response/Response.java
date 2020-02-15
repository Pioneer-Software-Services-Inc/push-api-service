/**
 * 
 */
package com.acme.services.push.response;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Parent class for the various response type subclasses (Error | Status)
 *
 */
public abstract class Response {

	@JsonIgnore
	public static transient final String ERROR_CONTENT = "{\"error\": \"%s\", \"message\": \"%s\"}";
	
	@JsonIgnore
	public static transient final String STATUS_CONTENT = "{\"status\": \"%s\", \"message\": \"%s\"}";
	
	@NotNull	
	@JsonProperty(value="code",required=true)	
	protected int code;
	
	@NotNull
	@JsonProperty(value="message",required=true)
	protected String message = "";
	
	@JsonProperty("errors")
	protected List<String> errors = null;
	
	/**
	 * default no - argument constructor for sub classes
	 */	
	public Response(){
		
	}
	
	/**
	 * Convenience constructor set class variables with one call.
	 * 
	 * @param message the message to set
	 * @param errors the errors to set 
	 */	
	public Response(int code, String message, List<String> errors){
		this.code = code;
		this.message = message;
		this.errors = errors;
	}
	
	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public static final String SUCCESS = "success";
	public static final String QUEUED = "queued";
	
	public static final short OK = 200;
	public static final short CREATED = 201;
	public static final short ACCEPTED = 202;
	public static final short NO_CONTENT = 204;
	public static final short RESET_CONTENT = 205;
	public static final short PARTIAL_CONTENT = 206;
	public static final short MULTI_STATUS = 207;
	public static final short ALREADY_REPORTED = 208;
	public static final short IM_USED = 226;	
	public static final short BAD_REQUEST = 400;
	public static final short UNAUTHORIZED = 401;
	public static final short SERVER_ERROR = 500;
		
	public static final Set<Short> hasNoContentCodes = new HashSet<Short>(Arrays.asList(CREATED, BAD_REQUEST, UNAUTHORIZED, SERVER_ERROR));
	public static final Set<Short> successCodes = new HashSet<Short>(Arrays.asList(OK, CREATED, ACCEPTED));
}
