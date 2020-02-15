/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.acme.services.push.model.beans.Action;
import com.acme.services.push.model.beans.Model;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Defines abstract type class Activity super class of all activity operations eg TriggerSend | ScheduleCreate etc
 */
public abstract class Activity extends Model implements Action{
	
	@JsonIgnore
	protected transient String propertyKeyRef = "trigger.send";
	
	public abstract String getPropertyKeyRef();

}
