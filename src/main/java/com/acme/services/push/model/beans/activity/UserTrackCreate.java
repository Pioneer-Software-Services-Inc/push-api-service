/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import java.util.List;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.model.beans.Attributes;

/**
 * @author cgordon
 * @created 10/13/2017
 * @version 1.0
 *
 * The purpose of this Push is for no specific data pushes to Push REST API endpoint.
 * 
 * @see <a href="https://www.push.com/documentation/REST_API/#user-track-request">Push Email object specification</a> 
 * 
 * User Track Request Definition
 * 
 * POST https://api.push.com/users/track
 * Content-Type: application/json
 * {
 *    "app_group_id" : (required, string) see App Group Identifier below,
 *    "attributes" : (optional, array of Attributes Object),
 *    "events" : (optional, array of Event Object),
 *    "purchases" : (optional, array of Purchase Object)
 * }
 * 
 */
@JsonRootName(value = "user_track")
public class UserTrackCreate extends Activity{

	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	public String appGroupId = Constants._BLANK;
	
	@JsonProperty(value="attributes")
	public List<Attributes> attributes;

	public UserTrackCreate() {
		propertyKeyRef = "user.track";
	}
	
	/**
	 * @return the appGroupId
	 */
	public String getAppGroupId() {
		return appGroupId;
	}

	/**
	 * @param appGroupId the appGroupId to set
	 */
	public void setAppGroupId(String appGroupId) {
		this.appGroupId = appGroupId;
	}

	/**
	 * @return the attributes
	 */
	public List<Attributes>  getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attributes>  attributes) {
		this.attributes = attributes;
	}	
	
	/**
	 * @return the propertyKeyRef
	 */
	@Override
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}
}
