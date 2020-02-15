/**
 * 
 */
package com.acme.services.push.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 * Interface definition of the Action(s) available to the job service
 *
 */
public interface Action {

	@JsonIgnore	
	public abstract String getPropertyKeyRef();
	
}
