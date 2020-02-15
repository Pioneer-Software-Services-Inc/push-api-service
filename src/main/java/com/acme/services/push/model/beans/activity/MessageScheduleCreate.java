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
import com.acme.services.push.constants.Constants;
import com.acme.services.push.model.beans.Schedule;
import com.acme.services.push.model.beans.push.Push;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#scheduling-messages">Push message schedule create</a>
 *
 *{
 *  "app_group_id": (required, string) see App Group Identifier,
 *  // You will need to include at least one of 'segment_id', 'external_user_ids', and 'audience'
 *  // Including 'segment_id' will send to members of that segment
 *  // Including 'external_user_ids' will send to those users
 *  // Including both will send to the provided user ids if they are in the segment
 *  "external_user_ids": (optional, array of strings) see External User ID,
 *  "audience": (optional, Connected Audience Object) see Connected Audience,
 *  "segment_id": (optional, string) see Segment Identifier,
 *  "campaign_id": (optional, string) see Campaign Identifier,
 *  "override_messaging_limits": (optional, bool) ignore global rate limits for campaigns, defaults to false,
 *  "recipient_subscription_state": (optional, string) use this to send messages to only users who have opted in ('opted_in'), only users who have subscribed or are opted in ('subscribed') or to all users, including unsubscribed users ('all'), the latter being useful for transactional email messaging. Defaults to 'subscribed',
 *  "schedule": {
   *  "time": (required, datetime as ISO 8601 string) time to send the message,
   *  "in_local_time": (optional, bool),
   *  "at_optimal_time": (optional, bool),
 * },
 *  "messages": {
   *  "apple_push": (optional, Apple Push Object),
   *  "android_push": (optional, Android Push Object),
   *  "windows_push": (optional, Windows Phone 8 Push Object),
   *  "windows8_push": (optional, Windows Universal Push Object),
   *  "kindle_push": (optional, Kindle/FireOS Push Object),
   *  "web_push": (optional, Web Push Object),
   *  "in_app_message" : (optional, In-App Message Object)
   *  "email": (optional, Email object)
   *  "webhook": (optional, Webhook object)
 * }
 *}
 *
 */
@JsonRootName(value = "message_schedule_create")
public class MessageScheduleCreate extends Activity{

	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	private String app_group_id  = Constants._BLANK;
	
	@JsonProperty("external_user_ids")	
	private List<String> external_user_ids = new ArrayList<String>();

	@JsonProperty("audience")	
	private Object audience;
	
	@JsonProperty("segment_id")	
	private String segment_id;
	
	@JsonProperty("campaign_id")	
	private String campaign_id;
	
	@JsonProperty("override_messaging_limits")	
	private boolean override_messaging_limits;
	
	@JsonProperty("recipient_subscription_state")	
	private String recipient_subscription_state;
	
	@JsonProperty("schedule")	
	private Schedule schedule;
	
	@JsonProperty("messages")	
	private Hashtable<String, Push> messages = new Hashtable<String, Push>();
	
	public MessageScheduleCreate() {
		propertyKeyRef = "message.schedule.create";
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
	 * @return the external_user_ids
	 */
	public List<String> getExternal_user_ids() {
		return external_user_ids;
	}

	/**
	 * @param external_user_ids the external_user_ids to set
	 */
	public void setExternal_user_ids(List<String> external_user_ids) {
		this.external_user_ids = external_user_ids;
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
	 * @return the segment_id
	 */
	public String getSegment_id() {
		return segment_id;
	}

	/**
	 * @param segment_id the segment_id to set
	 */
	public void setSegment_id(String segment_id) {
		this.segment_id = segment_id;
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
	 * @return the override_messaging_limits
	 */
	public boolean isOverride_messaging_limits() {
		return override_messaging_limits;
	}

	/**
	 * @param override_messaging_limits the override_messaging_limits to set
	 */
	public void setOverride_messaging_limits(boolean override_messaging_limits) {
		this.override_messaging_limits = override_messaging_limits;
	}

	/**
	 * @return the recipient_subscription_state
	 */
	public String getRecipient_subscription_state() {
		return recipient_subscription_state;
	}

	/**
	 * @param recipient_subscription_state the recipient_subscription_state to set
	 */
	public void setRecipient_subscription_state(String recipient_subscription_state) {
		this.recipient_subscription_state = recipient_subscription_state;
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
	 * @return the messages
	 */
	public Hashtable<String, Push> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Hashtable<String, Push> messages) {
		this.messages = messages;
	}

	/**
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}
	
}
