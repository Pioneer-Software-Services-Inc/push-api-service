/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.acme.services.push.model.beans.Schedule;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#update-api-triggered-campaign-schedule">Push scheduled update</a>
 *{
 *  "app_group_id": (required, string) see App Group Identifier below,
 *  "campaign_id": (required, string) see Campaign Identifier,
 *  "schedule_id": (required, string) the schedule_id to update (obtained from the response to create schedule),
 *  "schedule": {
 *   // required, see create schedule documentation
 * }
 *}
 *
 */
@JsonRootName(value = "trigger_schedule_update")
public class TriggerScheduleUpdate extends Activity{
	
	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	private String app_group_id;

	@NotNull
	@JsonProperty(value="campaign_id",required=true)
	private String campaign_id;

	@NotNull
	@JsonProperty(value="schedule_id",required=true)
	private String schedule_id;

	@NotNull
	@JsonProperty(value="schedule",required=true)
	private Schedule schedule;
	
	public TriggerScheduleUpdate(){
		propertyKeyRef = "trigger.schedule.update";
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
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}
	
}
