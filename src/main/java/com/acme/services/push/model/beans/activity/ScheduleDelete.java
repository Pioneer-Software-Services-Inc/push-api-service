/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 * * @see <a href="https://www.push.com/documentation/REST_API/#delete-message-schedule">Push message schedule delete</a> 
 * 
 *{
 * "app_group_id": (required, string) see App Group Identifier below,
 * "schedule_id": (required, string) the schedule_id to delete (obtained from the response to create schedule)
 *} 
 *
 */
@JsonRootName(value = "schedule_delete")
public class ScheduleDelete extends Activity{

	@NotNull
	@JsonProperty(value="app_group_id",required=true)
	private String app_group_id;
	
	@NotNull
	@JsonProperty(value="schedule_id",required=true)
	private String schedule_id;
	
	@JsonIgnore
	private String propertyKeyRef = "schedule.delete";

	public ScheduleDelete() {
		propertyKeyRef = "schedule.delete";
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
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}
	
}
