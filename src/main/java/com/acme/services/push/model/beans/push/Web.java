/**
 * 
 */
package com.acme.services.push.model.beans.push;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.model.beans.Model;


/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * * @see <a href="https://www.push.com/documentation/REST_API/#web-push-object">Push Web push object</a>
 *
 *{
 *   "alert": (required, string) the notification message,
 *   "title": (required, string) the title that appears in the notification drawer,
 *   "extra": (optional, object) additional keys and values to be sent in the push,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under (must be an Kindle/FireOS Push Message),
 *   "custom_uri": (optional, string) a web URL,
 *   "image_url": (optional, string) url for image to show,
 *   "time_to_live": (optional, integer (seconds))
 *}
 *
 */
@JsonRootName(value = "web_push")
public class Web extends Model implements Push{
	
	@NotNull
	@JsonProperty(value="alert",required=true)
	private String alert;
	
	@NotNull
	@JsonProperty(value="title",required=true)
	private String title;

	@JsonProperty("extra")
	private Object extra;
	
	@JsonProperty("message_variation_id")	
	private String message_variation_id;
	
	@JsonProperty("custom_uri")	
	private String custom_uri;
	
	@JsonProperty("image_url")	
	private String image_url;
	
	@JsonProperty("time_to_live")	
	private Integer time_to_live;

	/**
	 * @return the alert
	 */
	public String getAlert() {
		return alert;
	}

	/**
	 * @param alert the alert to set
	 */
	public void setAlert(String alert) {
		this.alert = alert;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the extra
	 */
	public Object getExtra() {
		return extra;
	}

	/**
	 * @param extra the extra to set
	 */
	public void setExtra(Object extra) {
		this.extra = extra;
	}

	/**
	 * @return the message_variation_id
	 */
	public String getMessage_variation_id() {
		return message_variation_id;
	}

	/**
	 * @param message_variation_id the message_variation_id to set
	 */
	public void setMessage_variation_id(String message_variation_id) {
		this.message_variation_id = message_variation_id;
	}

	/**
	 * @return the custom_uri
	 */
	public String getCustom_uri() {
		return custom_uri;
	}

	/**
	 * @param custom_uri the custom_uri to set
	 */
	public void setCustom_uri(String custom_uri) {
		this.custom_uri = custom_uri;
	}

	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * @return the time_to_live
	 */
	public Integer getTime_to_live() {
		return time_to_live;
	}

	/**
	 * @param time_to_live the time_to_live to set
	 */
	public void setTime_to_live(Integer time_to_live) {
		this.time_to_live = time_to_live;
	}
	
}
