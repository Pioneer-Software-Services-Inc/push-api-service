/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.model.beans.Recipient;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#sending-messages-via-api-triggered-delivery">Push send message via triggered delivery</a>
 *
 *{
 *  "app_group_id": (required, string) see App Group Identifier below,
 *  "campaign_id": (required, string) see Campaign Identifier,
 *  "trigger_properties": (optional, object) personalization key/value pairs that will apply to all users in this request,
 *  "broadcast": (required, boolean) see Broadcast -- must be set to True unless "recipients" object is included,
 *  "audience": (optional, Connected Audience Object) see Connected Audience,
 * // Including 'audience' will only send to users in the audience
 *  "recipients": (optional, array) [
 *  {
 *  "external_user_id": (required, string) External Id of user to receive message,
 *  "trigger_properties": (optional, object) personalization key/value pairs that will apply to this user (these key/value pairs will override any keys that conflict with trigger_properties above)
 *   },
 *   ...
 * ]
 *}
 *
 */
@JsonRootName(value = "trigger_send")
public class TriggerSend extends Activity{

	@NotNull
	@JsonProperty("app_group_id")
	private String app_group_id = Constants._BLANK;

	@NotNull
	@JsonProperty(value="campaign_id",required=true)
	private String campaign_id = Constants._BLANK;

	@JsonProperty("trigger_properties")
	private Hashtable<String, String> trigger_properties = new Hashtable<String, String>();

	@JsonProperty("recipients")
	private List<Recipient> recipients = new ArrayList<Recipient>();

	public TriggerSend() {
		propertyKeyRef = "trigger.send";
	}

	/**
	 * @return the app_group_id
	 */
	public String getApp_group_id() {
		return app_group_id;
	}

	/**
	 * @param app_group_id the app_group_id to set
	 */
	public void setApp_group_id(String app_group_id) {
		this.app_group_id = app_group_id;
	}

	/**
	 * @return the campaign_id
	 */
	public String getCampaign_id() {
		return campaign_id;
	}

	/**
	 * @param campaign_id the campaign_id to set
	 */
	public void setCampaign_id(String campaign_id) {
		this.campaign_id = campaign_id;
	}

	/**
	 * @return the trigger_properties
	 */
	public Hashtable<String, String>  getTrigger_properties() {
		return trigger_properties;
	}

	/**
	 * @param trigger_properties the trigger_properties to set
	 */
	public void setTrigger_properties(Hashtable<String, String>  trigger_properties) {
		this.trigger_properties = trigger_properties;
	}

	/**
	 * @return the recipients
	 */
	public List<Recipient> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}

}
