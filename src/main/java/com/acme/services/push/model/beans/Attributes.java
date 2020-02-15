/**
 * 
 */
package com.acme.services.push.model.beans;

import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.services.push.constants.Constants;

/**
 *
 * @author cgordon
 * @created 10/16/2017
 * @version 1.0
 *
 * @param <T> user defined type parameter key 
 * @param <O> user defined type parameter value object
 *
 * Custom implementation of the <code>Hashtable</code> ADT for specific need of the Push application.
 * 
 * 
 * User Attributes Object Specification
 * 
 * @see <a href="https://www.push.com/documentation/REST_API/#user-attributes-object-specification">Push Email object specification</a>
 * {
 *    // One of "external_id" or "user_alias" is required
 *    "external_id" : (optional, string) see External User ID below,
 *    "user_alias" : (optional, User Alias Object),
 *    // Setting this flag to true will put the API in "Update Only" mode. Attributes objects regarding
 *    // external_ids which Push is unaware of will return a non-fatal error. See Server Responses for details.
 *    // When using a "user_alias", "Update Only" mode is always true.
 *    "_update_existing_only" : (optional, boolean),
 *    // See note below regarding anonymous push token imports
 *    "push_token_import" : (optional, boolean).
 *    // Push User Profile Fields
 *    "first_name" : "Jon",
 *    "email" : "bob@example.com",
 *    // Custom Attributes
 *    "my_custom_attribute" : value,
 *    "my_custom_attribute_2" : {"inc" : int_value},
 *    "my_array_custom_attribute":[ "Value1", "Value2" ],
 *    // Adding a new value to an array custom attribute
 *    "my_array_custom_attribute" : { "add" : ["Value3"] },
 *    // Removing a value from an array custom attribute
 *    "my_array_custom_attribute" : { "remove" : [ "Value1" ]},
 * }
 * 
 * 
 */
public class Attributes extends Model{
	
	@JsonProperty(value="user_alias")		
	private UserAlias user_alias;

	@JsonProperty(value="first_name")		
	private String first_name;

	@JsonProperty(value="last_name")		
	private String last_name;

	@JsonProperty(value="source")		
	private String source;
	
	@Pattern(regexp=Constants.REGEXP_VALID_PHONE)
	@JsonProperty(value="phone")		
	private String phone;
	
	@Pattern(regexp=Constants.REGEXP_VALID_EMAIL)
	@JsonProperty(value="email")		
	private String email;

	@Pattern(regexp=Constants.REGEXP_VALID_FLAG)	
	@JsonProperty(value="sms_optin")		
	private String sms_option;

	/**
	 * @return the user_alias
	 */
	public UserAlias getUser_alias() {
		return user_alias;
	}

	/**
	 * @param user_alias the user_alias to set
	 */
	public void setUser_alias(UserAlias user_alias) {
		this.user_alias = user_alias;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the sms_option
	 */
	public String getSms_option() {
		return sms_option;
	}

	/**
	 * @param sms_option the sms_option to set
	 */
	public void setSms_option(String sms_option) {
		this.sms_option = sms_option;
	}
	
	/**
	 * @throws CloneNotSupportedException 
	 */
	public Attributes clone() throws CloneNotSupportedException {
		return (Attributes)super.clone();
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}	
}	