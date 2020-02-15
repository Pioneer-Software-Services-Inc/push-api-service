/**
 * 
 */
package com.acme.services.push.model.beans;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Model abstract [super] class defines toString() method for output of JSON defined contents 
 * Very useful for defining common behaviors for all Model objects
 */
public abstract class Model extends JSONObject{

	protected String toJsonString() {
		
		try {
			return new ObjectMapper().writeValueAsString(this).replaceAll("\\\\", "");
		} catch (JsonProcessingException e) {
		}

		return "";
	}
}
