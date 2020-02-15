/**
 * 
 */
package com.acme.services.push.model.beans.push;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * * @see <a href="https://www.push.com/documentation/REST_API/#apple-push-object">Push Apple push object</a>
 *
 *{
 *  "badge": (optional, int) the badge count after this message,
 *   "alert": (required unless content-available is true, string or Apple Push Alert Object) the notification message,
 *   // Specifying "default" in the sound field will play the standard notification sound
 *   "sound": (optional, string) the location of a custom notification sound within the app,
 *   "extra": (optional, object) additional keys and values to be sent,
 *   "content-available": (optional, boolean) if set, Push will add the "content-available" flag to the push payload,
 *   "category": (optional, string) define a type of notification which contains actions a user can perform in response,
 *   "expiry": (optional, ISO 8601 date string) if set, push messages will expire at the specified datetime,
 *   "custom_uri": (optional, string) a web URL, or Deep Link URI,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under (must be an iOS Push Message),
 *   "asset_url": (optional, string) content URL for rich notifications for devices using iOS 10 or higher,
 *   "asset_file_type": (required if asset_url is present, string) file type of the asset - one of "aif", "gif", "jpg", "m4a", "mp3", "mp4", "png", or "wav",
 *   "collapse_id": (optional, string) To update a notification on the user's device once you've issued it, send another notification with the same collapse ID you used previously
 *   "mutable_content": (optional, boolean) if true, Push will add the mutable-content flag to the payload and set it to 1. The mutable-content flag is automatically set to 1 when sending a rich notification, regardless of the value of this parameter.
 *}
 *
 */
@JsonRootName(value = "apple_push")
public class Apple extends Model implements Push{

	@JsonProperty("badge")
	private Long badge;
	
	@JsonProperty("alert")
	private String alert;
	
	@JsonProperty("sound")
	private String sound;
	
	@JsonProperty("extra")
	private Object extra;
	
	@JsonProperty("content-available")
	private Boolean content_available;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("expiry")
	private String expiry = null;
	
	@JsonProperty("custom_uri")
	@Pattern(regexp=Constants.REGEXP_VALID_URL)
	private String custom_uri;
	
	@JsonProperty("message_variation_id")
	private String message_variation_id;
	
	@JsonProperty("asset_file_type")
	private String asset_file_type;
	
	@JsonProperty("collapse_id")
	private String collapse_id;
	
	@JsonProperty("mutable_content")
	private Boolean mutable_content;

	/**
	 * @return the badge
	 */
	public Long getBadge() {
		return badge;
	}

	/**
	 * @param badge the badge to set
	 */
	public void setBadge(Long badge) {
		this.badge = badge;
	}

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
	 * @return the content_available
	 */
	public Boolean getContent_available() {
		return content_available;
	}

	/**
	 * @param content_available the content_available to set
	 */
	public void setContent_available(Boolean content_available) {
		this.content_available = content_available;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the expiry
	 */
	public String getExpiry() {
		return expiry;
	}

	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(String expiry) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		this.expiry = df.format(new Date());
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
	 * @return the collapse_id
	 */
	public String getCollapse_id() {
		return collapse_id;
	}

	/**
	 * @param collapse_id the collapse_id to set
	 */
	public void setCollapse_id(String collapse_id) {
		this.collapse_id = collapse_id;
	}

	/**
	 * @return the mutable_content
	 */
	public Boolean getMutable_content() {
		return mutable_content;
	}

	/**
	 * @param mutable_content the mutable_content to set
	 */
	public void setMutable_content(Boolean mutable_content) {
		this.mutable_content = mutable_content;
	}
	
	
}
