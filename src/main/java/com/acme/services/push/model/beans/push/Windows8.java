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
 * * * @see <a href="https://www.push.com/documentation/REST_API/#windows-phone-8-push-object">Push Windows 8 push object</a>
 *
 *{
 *   "push_type": (optional, string) must be "toast",
 *   "toast_title": (optional, string) the notification title,
 *   "toast_content": (required, string) the notification message,
 *   "toast_navigation_uri": (optional, string) page uri to send user to,
 *   "toast_hash": (optional, object) additional keys and values to send,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under (must be a Windows Phone 8 Push Message)
 *}
 *
 */
@JsonRootName(value = "windows8_push")
public class Windows8 extends Model implements Push{
	

	@JsonProperty("push_type")
	private String push_type;
	
	@JsonProperty("toast_title")
	private String toast_title;

	@NotNull	
	@JsonProperty(value="toast_content",required=true)
	private String toast_content;
	
	@JsonProperty("toast_navigation_uri")
	private String toast_navigation_uri;
	
	@JsonProperty("toast_hash")	
	private Object toast_hash;
	
	@JsonProperty("message_variation_id")
	private String message_variation_id;

	/**
	 * @return the push_type
	 */
	public String getPush_type() {
		return push_type;
	}

	/**
	 * @param push_type the push_type to set
	 */
	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}

	/**
	 * @return the toast_title
	 */
	public String getToast_title() {
		return toast_title;
	}

	/**
	 * @param toast_title the toast_title to set
	 */
	public void setToast_title(String toast_title) {
		this.toast_title = toast_title;
	}

	/**
	 * @return the toast_content
	 */
	public String getToast_content() {
		return toast_content;
	}

	/**
	 * @param toast_content the toast_content to set
	 */
	public void setToast_content(String toast_content) {
		this.toast_content = toast_content;
	}

	/**
	 * @return the toast_navigation_uri
	 */
	public String getToast_navigation_uri() {
		return toast_navigation_uri;
	}

	/**
	 * @param toast_navigation_uri the toast_navigation_uri to set
	 */
	public void setToast_navigation_uri(String toast_navigation_uri) {
		this.toast_navigation_uri = toast_navigation_uri;
	}

	/**
	 * @return the toast_hash
	 */
	public Object getToast_hash() {
		return toast_hash;
	}

	/**
	 * @param toast_hash the toast_hash to set
	 */
	public void setToast_hash(Object toast_hash) {
		this.toast_hash = toast_hash;
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
}
