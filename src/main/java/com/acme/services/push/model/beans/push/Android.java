/**
 * 
 */
package com.acme.services.push.model.beans.push;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.model.beans.Model;


/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * @see <a href="https://www.push.com/documentation/REST_API/#android-push-object">Push Android push object</a>
 *
 *{
 *   "alert": (required, string) the notification message,
 *   "title": (required, string) the title that appears in the notification drawer,
 *   "extra": (optional, object) additional keys and values to be sent in the push,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under (must be an Android Push Message),
 *   "priority": (optional, integer) the notification priority value,
 *   "send_to_sync": (optional, if set to True we will throw an error if "alert" or "title" is set),
 *   "collapse_key": (optional, string) the collapse key for this message,
 *   // Specifying "default" in the sound field will play the standard notification sound
 *   "sound": (optional, string) the location of a custom notification sound within the app,
 *   "custom_uri": (optional, string) a web URL, or Deep Link URI,
 *   "summary_text": (optional, string),
 *   "time_to_live": (optional, integer (seconds)),
 *   "notification_id": (optional, integer),
 *   "push_icon_image_url": (optional, string) an image URL for the large icon,
 *   "accent_color": (optional, integer) accent color to be applied by the standard Style templates when presenting this notification, an RGB integer value
 *}
 *
 *
 */

@JsonRootName(value = "android_push")
public class Android extends Model implements Push{

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
	
	@JsonProperty("priority")
	private Integer priority;
	
	@JsonProperty("send_to_sync")
	private Object send_to_sync;
		
	@JsonProperty("collapse_key")
	private String collapse_key;
	
	@JsonProperty("sound")
	private String sound;
	
	@JsonProperty("custom_uri")
	@Pattern(regexp=Constants.REGEXP_VALID_URL)	
	private String custom_uri;
	
	@JsonProperty("summary_text")
	private String summary_text;

	@JsonProperty("time_to_live")
	private Integer time_to_live;
	
	@JsonProperty("notification_id")
	private Integer notification_id;
	
	@JsonProperty("asset_file_type")
	private String asset_file_type;
	
	@JsonProperty("push_icon_image_url")
	private String push_icon_image_url;
	
	@JsonProperty("accent_color")
	private Integer accent_color;

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
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the send_to_sync
	 */
	public Object getSend_to_sync() {
		return send_to_sync;
	}

	/**
	 * @param send_to_sync the send_to_sync to set
	 */
	public void setSend_to_sync(Object send_to_sync) {
		this.send_to_sync = send_to_sync;
	}

	/**
	 * @return the collapse_key
	 */
	public String getCollapse_key() {
		return collapse_key;
	}

	/**
	 * @param collapse_key the collapse_key to set
	 */
	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}

	/**
	 * @return the sound
	 */
	public String getSound() {
		return sound;
	}

	/**
	 * @param sound the sound to set
	 */
	public void setSound(String sound) {
		this.sound = sound;
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
	 * @return the summary_text
	 */
	public String getSummary_text() {
		return summary_text;
	}

	/**
	 * @param summary_text the summary_text to set
	 */
	public void setSummary_text(String summary_text) {
		this.summary_text = summary_text;
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

	/**
	 * @return the notification_id
	 */
	public Integer getNotification_id() {
		return notification_id;
	}

	/**
	 * @param notification_id the notification_id to set
	 */
	public void setNotification_id(Integer notification_id) {
		this.notification_id = notification_id;
	}

	/**
	 * @return the asset_file_type
	 */
	public String getAsset_file_type() {
		return asset_file_type;
	}

	/**
	 * @param asset_file_type the asset_file_type to set
	 */
	public void setAsset_file_type(String asset_file_type) {
		this.asset_file_type = asset_file_type;
	}

	/**
	 * @return the push_icon_image_url
	 */
	public String getPush_icon_image_url() {
		return push_icon_image_url;
	}

	/**
	 * @param push_icon_image_url the push_icon_image_url to set
	 */
	public void setPush_icon_image_url(String push_icon_image_url) {
		this.push_icon_image_url = push_icon_image_url;
	}

	/**
	 * @return the accent_color
	 */
	public Integer getAccent_color() {
		return accent_color;
	}

	/**
	 * @param accent_color the accent_color to set
	 */
	public void setAccent_color(Integer accent_color) {
		this.accent_color = accent_color;
	}

	
}
