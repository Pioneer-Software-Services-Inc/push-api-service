/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.acme.services.push.model.beans.Recipient;
import com.acme.services.push.model.beans.Schedule;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#schedule-api-triggered-campaign">Push scheduled create</a>
 *
 *{
 *  "app_group_id": (required, string) see App Group Identifier,
 *  "campaign_id": (required, string) see Campaign Identifier,
 * // Including 'recipients' will send only to the provided user ids if they are in the campaign's segment
 *  "recipients": (optional, Array of Recipient Object),
 * // for any keys that conflict between these trigger properties and those in a Recipient Object, the value from the
 * // Recipient Object will be used
 *  "audience": (optional, Connected Audience Object) see Connected Audience,
 * // Including 'audience' will only send to users in the audience
 *  "trigger_properties": (optional, object) personalization key/value pairs for all users in this send; see Trigger Properties,
 *  "schedule": {
 *  "time": (required, datetime as ISO 8601 string) time to send the message,
 *  "in_local_time": (optional, bool),
 *  "at_optimal_time": (optional, bool),
 * }
 *}
 *
 */
@JsonRootName(value = "trigger_schedule_create")
public class TriggerScheduleCreate extends Activity{

	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	private String app_group_id;

	@NotNull
	@JsonProperty(value="campaign_id",required=true)
	private String campaign_id;

	@JsonProperty("recipients")	
	private List<Recipient> recipients = new ArrayList<Recipient>();

	@JsonProperty("audience")	
	private Object audience;

	@JsonProperty("trigger_properties")	
	private Hashtable<String, String> trigger_properties = new Hashtable<String, String>();

	@JsonProperty("schedule")	
	private Schedule schedule;
	
	public TriggerScheduleCreate() {
		propertyKeyRef = "trigger.send.create";
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
	 * @return the audience
	 */
	public Object getAudience() {
		return audience;
	}

	/**
	 * @param audience the audience to set
	 */
	public void setAudience(Object audience) {
		this.audience = audience;
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

	/**
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}

}
