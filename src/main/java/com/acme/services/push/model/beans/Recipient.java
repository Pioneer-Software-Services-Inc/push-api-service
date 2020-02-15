/**
 * 
 */
package com.acme.services.push.model.beans;

import java.util.Hashtable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#recipient-object">Push recepient object</a>
 *
 *{
 * // Either "external_user_id" or "user_alias" is required. Requests must specify only one.
 * "external_user_id": (optional, string) External Id of user to receive message,
 * "user_alias": (optional, User Alias Object) User Alias of user to receive message, 
 * "trigger_properties": (optional, object) personalization key/value pairs for this user; see Trigger Properties
 *}
 *
 */
@JsonRootName(value = "recepient")
public class Recipient extends Model{

	@JsonProperty(value="user_alias")
	private UserAlias user_alias = new UserAlias();
	
	@JsonProperty("trigger_properties")
	private Hashtable<String, String> trigger_properties = new Hashtable<String, String>();
	
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
	 * @return the trigger_properties
	 */
	public Hashtable<String, String> getTrigger_properties() {
		return trigger_properties;
	}

	/**
	 * @param trigger_properties the trigger_properties to set
	 */
	public void setTrigger_properties(Hashtable<String, String> trigger_properties) {
		this.trigger_properties = trigger_properties;
	}
	
}
