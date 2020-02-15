/**
 * 
 */
package com.acme.services.push.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/11/2017
 * @version 1.0
 * 
 * Audience type class definition for custom attributes and filter function(s).
 * 
 */
@JsonRootName(value = "audience")
public class Audience extends Model{
	
	@JsonProperty("custom_attribute")	
	private CustomAttribute custom_attribute = new CustomAttribute();

	/**
	 * @return the custom_attribute
	 */
	public CustomAttribute getCustom_attribute() {
		return custom_attribute;
	}

	/**
	 * @param custom_attribute the custom_attribute to set
	 */
	public void setCustom_attribute(CustomAttribute custom_attribute) {
		this.custom_attribute = custom_attribute;
	}
}
