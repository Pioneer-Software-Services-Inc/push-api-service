/**
 * 
 */
package com.acme.services.push.model.beans.activity;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Null Object software design pattern for removing or simplifying null pointer object problems.
 */
@JsonRootName(value = "invalid_activity")
public class NullActivity extends Activity{
	
	public NullActivity() {
		propertyKeyRef = "trigger.invalid.activity";
	}
	
	/**
	 * @return the propertyKeyRef
	 */
	public String getPropertyKeyRef() {
		return propertyKeyRef;
	}

}
