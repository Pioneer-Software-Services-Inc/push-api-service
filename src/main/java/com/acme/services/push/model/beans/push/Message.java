/**
 * 
 */
package com.acme.services.push.model.beans.push;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Message to enclose Push message information for JSON purposes (Push complain) 
 *
 */
@JsonRootName(value = "message")
public class Message {

	@NotNull
	@JsonProperty(value="message",required=true)
	private Push message;

	public Message(Push message){
		
		this.message = message;
	}

	public Message(){
		
	}
	
	/**
	 * @return the message
	 */
	public Push getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Push message) {
		this.message = message;
	}
	
	
}
