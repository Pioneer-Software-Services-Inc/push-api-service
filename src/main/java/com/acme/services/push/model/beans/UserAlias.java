/**
 * 
 */
package com.acme.services.push.model.beans;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.services.push.constants.Constants;

import javax.validation.constraints.Pattern;

/**
 * @author cgordon
 * @created 09/05/2017
 * @version 1.0
 *
 * @see <a href="https://www.push.com/documentation/REST_API/#user-alias-object">Push User Alias Object specification</a>
 *
 *{
 *  "alias_name" : (required, string),
 *  "alias_label" : (required, string)
 *}
 */
@JsonRootName(value = "user_alias")
public class UserAlias extends Model{

	@NotNull
	@JsonProperty(value="alias_name",required=true)
	private String alias_name  = Constants._BLANK;

	@NotNull
	@Pattern(regexp=Constants.REGEXP_VALID_ALIAS_LABEL)
	@JsonProperty(value="alias_label",required=true)
	private String alias_label = Constants.DEFAULT_ALIAS_LABEL;

	/**
	 * No param constructor 
	 */
	public UserAlias(){};
	
	/**
	 * Constructor override for convenience
	 * 
	 * @param alias <code>String</code>
	 * @param label <code>String</code>
	 */
	public UserAlias(String alias, String label){
		alias_name = alias;
		alias_label = label;
	};
	
	/**
	 * Constructor override for convenience
	 * 
	 * @param alias <code>String</code>
	 */
	public UserAlias(String alias){
		alias_name = alias;
	}
	
	/**
	 * @return the alias_name
	 */
	public String getAlias_name() {
		return alias_name;
	}

	/**
	 * @param alias_name the alias_name to set
	 */
	public void setAlias_name(String alias_name) {
		this.alias_name = alias_name;
	}

	/**
	 * @return the alias_label
	 */
	public String getAlias_label() {
		return alias_label;
	}

	/**
	 * @param alias_label the alias_label to set
	 */
	public void setAlias_label(String alias_label) {
		this.alias_label = alias_label;
	}

	//@Override
	//public String toString() {
		
	//	return super.toJsonString().replaceAll(":\"{\"", ":{\"").replaceAll("}\",","},");
	//}
}