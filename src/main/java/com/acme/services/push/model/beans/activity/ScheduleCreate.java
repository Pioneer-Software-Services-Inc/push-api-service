/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import java.util.Hashtable;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.acme.services.push.model.beans.Schedule;
import com.acme.services.push.model.beans.push.Push;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#scheduling-messages">Push scheduled message create</a>
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
  },
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
 *}
 *}
 *
 */
@JsonRootName(value = "schedule_create")
public class ScheduleCreate extends Activity{

	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	private String app_group_id;

	@NotNull
	@JsonProperty(value="schedule_id",required=true)
	private String schedule_id;

	@JsonProperty("schedule")	
	private Schedule schedule;

	@JsonProperty("messages")	
	private Hashtable<String, Push> messages = new Hashtable<String, Push>();

	public ScheduleCreate() {
		propertyKeyRef = "schedule.create";
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
	 * @return the schedule_id
	 */
	public String getSchedule_id() {
		return schedule_id;
	}

	/**
	 * @param schedule_id the schedule_id to set
	 */
	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
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
