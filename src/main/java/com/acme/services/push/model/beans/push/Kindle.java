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
 * @see <a href="https://www.push.com/documentation/REST_API/#kindlefireos-push-object">Push Kindle Fireos push object</a>
 *
 *{
 *    "alert": (required, string) the notification message,
 *    "title": (required, string) the title that appears in the notification drawer,
 *    "extra": (optional, object) additional keys and values to be sent in the push,
 *    "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under (must be an Kindle/FireOS Push Message),
 *    "priority": (optional, integer) the notification priority value,
 *    "collapse_key": (optional, string) the collapse key for this message,
 *    // Specifying "default" in the sound field will play the standard notification sound
 *    "sound": (optional, string) the location of a custom notification sound within the app,
 *    "custom_uri": (optional, string) a web URL, or Deep Link URI
 *}
 *
 *
 */
@JsonRootName(value = "kindle_push")
public class Kindle extends Model implements Push{

}
