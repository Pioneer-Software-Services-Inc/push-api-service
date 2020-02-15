/**
 * 
 */
package com.acme.services.push.model.beans.push;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.model.beans.Model;


/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 * 
 * * * @see <a href="https://www.push.com/documentation/REST_API/#webhook-object-specification">Push Webhook object specification</a> 
 *
 *{
 *   "url": (required, string),
 *   "request_method": (required, string) one of "POST", "PUT", "DELETE", or "GET",
 *   "request_headers": (optional, Hash) key/value pairs to use as request headers,
 *   "body": (optional, string) if you want to include a JSON object, make sure to escape quotes and backslashes,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under
 *} 
 *
 */
@JsonRootName(value = "webhook_push")
public class WebHook extends Model implements Push{

}
