/**
 * 
 */
package com.acme.services.push.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.constants.Constants;

/**
 * @author cgordon
 * @created 08/11/2017
 * @version 1.0
 * 
 * @see <a href="https://www.push.com/documentation/REST_API/#custom-attribute-filter">Push custom attribute filter</a> 
 * 
 * Custom Attribute type definition for use in the Audience data type for custom attributes and filter function(s).
 * 
 * {
 *  "custom_attribute":
 *    {
 *      "custom_attribute_name": (String) the name of the custom attribute to filter on,
 *      "comparison": (String) one of the allowed comparisons to make against the provided value,
 *      "value": (String, Numeric, Boolean) the value to be compared using the provided comparison
 *    }
 *}
 * 
 */
@JsonRootName(value = "audience")
public class CustomAttribute extends Model{

	@JsonProperty("custom_attribute_name")
	private String custom_attribute_name = Constants._BLANK;

	@JsonProperty("comparison")
	private String comparison = Constants._BLANK;

	@JsonProperty("value")
	private String value = Constants._BLANK;

	/**
	 * @return the custom_attribute_name
	 */
	public String getCustom_attribute_name() {
		return custom_attribute_name;
	}

	/**
	 * @param custom_attribute_name the custom_attribute_name to set
	 */
	public void setCustom_attribute_name(String custom_attribute_name) {
		this.custom_attribute_name = custom_attribute_name;
	}

	/**
	 * @return the comparison
	 */
	public String getComparison() {
		return comparison;
	}

	/**
	 * @param comparison the comparison to set
	 */
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
