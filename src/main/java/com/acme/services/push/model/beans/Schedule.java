/**
 * 
 */
package com.acme.services.push.model.beans;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 *
 * "schedule": {
 *   "time": (required, datetime as ISO 8601 string) time to send the message,
 *   "in_local_time": (optional, bool),
 *   "at_optimal_time": (optional, bool),
 * },
 */
@JsonRootName(value = "schedule")
public class Schedule {
	
	@NotNull
	@JsonProperty(value="time",required=true)
    private String time;
	
	@JsonProperty("in_local_time")
    private boolean in_local_time;
    
	@JsonProperty("at_optimal_time")
    private boolean at_optimal_time;

	public Schedule(){
		
	}

	/**
	 * Convenience constructor that accepts parameters
	 * 
	 * @param time <code>String</code>(required, datetime as ISO 8601 string) time to send the message,
	 * @param local <code>Boolean</code> flag local time
	 * @param optimal <code>Boolean</code> flag at optimal time
	 */
	public Schedule(String time, boolean local, boolean optimal){
		
		this.time = time;
		this.in_local_time = local;
		this.at_optimal_time = optimal;
	}
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the in_local_time
	 */
	public boolean isIn_local_time() {
		return in_local_time;
	}

	/**
	 * @param in_local_time the in_local_time to set
	 */
	public void setIn_local_time(boolean in_local_time) {
		this.in_local_time = in_local_time;
	}

	/**
	 * @return the at_optimal_time
	 */
	public boolean isAt_optimal_time() {
		return at_optimal_time;
	}

	/**
	 * @param at_optimal_time the at_optimal_time to set
	 */
	public void setAt_optimal_time(boolean at_optimal_time) {
		this.at_optimal_time = at_optimal_time;
	}
}
