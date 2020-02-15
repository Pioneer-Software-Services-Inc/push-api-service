/**
 * 
 */
package com.acme.services.push.model.beans.push;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.model.beans.Model;


/**
 * @author cgordon
 * @created 08/22/2017
 * @version 1.0
 *
 *
 */
@JsonRootName(value = "sms_push")
public class Sms extends Model implements Push{
	
	
}
