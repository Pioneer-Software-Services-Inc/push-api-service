/**
 * 
 */
package com.acme.services.push.model.beans.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.model.beans.Button;
import com.acme.services.push.model.beans.Model;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * * * @see <a href="https://www.push.com/documentation/REST_API/#in-app-message-object-specification">Push In-App message specification</a>
 *
 *{
 *   "type" : (optional, string) one of "SLIDEUP" OR "MODAL" or "FULL", defaults to "SLIDEUP",
 *   // For "SLIDEUP" messages, old versions of the SDK only support some of the features, so set this to false to avoid sending messages that would not make sense as an older version of the slideups (i.e. includes an image that is referenced by the message). This is not applicable for "MODAL" or "FULL" messages, which are always only sent to new versions
 *   "also_send_to_slideup_only_versions" : (optional, boolean), defaults to true,
 *   "message" : (required, string) 140 characters max,
 *   // all colors should be 8-digit hex strings plus a leading "0x", for example "0xFF00AA88"
 *   // see http://developer.android.com/reference/android/graphics/Color.html for specifications
 *   "message_text_color" : (optional, string) hex value for colors, defaults to black or white depending on message type,
 *   "header" : (optional, string) the header shown, not shown if excluded,
 *   "header_text_color" : (optional, string) hex value for colors, defaults to black or white depending on message type,
 *   "background_color" : (optional, string) hex value for colors, defaults to black or white depending on message type,
 *   "close_button_color" : (optional, string) hex value for colors, defaults to black
 *   "slide_from" : (optional, string) "TOP" OR "BOTTOM" for "STANDARD" messages, defaults to "BOTTOM",
 *   "message_close" : (optional, string) "SWIPE" OR "AUTO_DISMISS", defaults to "AUTO_DISMISS",
 *   // icon should be 4-digit hex string without the leading "0x" from http://fortawesome.github.io/Font-Awesome/cheatsheet/
 *   // for example, "f042" for the first icon, fa-adjust [&#xf042;]
 *   // if both image_url and icon are present, image_url will be used
 *   "icon": (optional, string) Font Awesome icon hex value,
 *   "icon_color" : (optional, string) hex value for colors, defaults to white,
 *   "icon_background_color" : (optional, string) hex value for colors, defaults to blue,
 *   "image_url" : (optional, string) url for image to show when type is "FULL", overrides "icon" if both are present,
 *   "buttons" : (optional, Array of Button Objects) buttons to show, at most 2 allowed,
 *   "extras" : (optional, valid Key Value Hash), extra hash,
 *   // click actions and uri are not applicable if there are buttons and type is MODAL or FULL
 *   "ios_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "android_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "windows_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "windows8_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "kindle_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "android_china_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "web_click_action" : (optional, string) "NONE" OR "NEWS_FEED" OR "URI", defaults to "NONE",
 *   "ios_uri" : (optional, string) valid http or protocol uri,
 *   "android_uri" : (optional, string) valid http or protocol uri,
 *   "windows_uri" : (optional, string) valid http or protocol uri,
 *   "windows8_uri" : (optional, string) valid http or protocol uri,
 *   "kindle_uri" : (optional, string) valid http or protocol uri,
 *   "android_china_uri" : (optional, string) valid http or protocol uri,
 *   "web_uri" : (optional, string) valid http or protocol uri,
 *   "message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under
 *}
 *
 */

@JsonRootName(value = "inapp_push")
public class InApp extends Model implements Push{
	
	private static final String REGEXP_VALID_CLICK_TYPE = "^[NONE|NEWS_FEED|URI]{1}$";
	private static final String REGEXP_VALID_FORM_TYPE = "^[SLIDEUP|MODAL|FULL]{1}$";	
	private static final String REGEXP_VALID_SWIPE_ACTION = "^[SWIPE|AUTO_DISMISS]{1}$";
	private static final String REGEXP_VALID_CLOSE_ACTION = "^[TOP|BOTTOM|STANDARD]{1}$";

	@Pattern(regexp=REGEXP_VALID_FORM_TYPE)
	@JsonProperty("type")	
	private String type;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("message_text_color")
	private String message_text_color;
	
	@JsonProperty("header")
	private String header;
	
	@JsonProperty("header_text_color")
	private String header_text_color;
	
	@JsonProperty("background_color")
	private String background_color;
	
	@JsonProperty("close_button_color")
	private String close_button_color;
	
	@JsonProperty("slide_from")
	@Pattern(regexp=REGEXP_VALID_CLOSE_ACTION)
	private String slide_from;
	
	@JsonProperty("message_close")	
	@Pattern(regexp=REGEXP_VALID_SWIPE_ACTION)	
	private String message_close;
	
	@JsonProperty("icon")
	private String icon;
	
	@JsonProperty("icon_color")
	private String icon_color;
	
	@JsonProperty("icon_background_color")
	private String icon_background_color;
	
	@JsonProperty("image_url")
	private String image_url;
	
	@JsonProperty("buttons")
	private List<Button> buttons = new ArrayList<Button>(2);
	
	@JsonProperty("extras")
	private HashMap<String, String> extras;
	
	@JsonProperty("ios_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)
	private String ios_click_action;
	
	@JsonProperty("android_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)
	private String android_click_action;
	
	@JsonProperty("windows_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)	
	private String windows_click_action;
	
	@JsonProperty("windows8_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)	
	private String windows8_click_action;
	
	@JsonProperty("kindle_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)	
	private String kindle_click_action;
	
	@JsonProperty("android_china_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)	
	private String android_china_click_action;

	@JsonProperty("web_click_action")
	@Pattern(regexp=REGEXP_VALID_CLICK_TYPE)	
	private String web_click_action;
	
	@JsonProperty("ios_uri")
	@Pattern(regexp=Constants.REGEXP_VALID_URL)
	private String ios_uri;
	
	@JsonProperty("android_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)	
	private String android_uri;
	
	@JsonProperty("windows_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)	
	private String windows_uri;
	
	@JsonProperty("windows8_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)	
	private String windows8_uri;
	
	@JsonProperty("kindle_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)	
	private String kindle_uri;
	
	@JsonProperty("android_china_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)
	private String android_china_uri;
	
	@JsonProperty("web_uri")	
	@Pattern(regexp=Constants.REGEXP_VALID_URL)
	private String web_uri;
	
	@JsonProperty("message_variation_id")	
	private String message_variation_id;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message_text_color
	 */
	public String getMessage_text_color() {
		return message_text_color;
	}

	/**
	 * @param message_text_color the message_text_color to set
	 */
	public void setMessage_text_color(String message_text_color) {
		this.message_text_color = message_text_color;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the header_text_color
	 */
	public String getHeader_text_color() {
		return header_text_color;
	}

	/**
	 * @param header_text_color the header_text_color to set
	 */
	public void setHeader_text_color(String header_text_color) {
		this.header_text_color = header_text_color;
	}

	/**
	 * @return the background_color
	 */
	public String getBackground_color() {
		return background_color;
	}

	/**
	 * @param background_color the background_color to set
	 */
	public void setBackground_color(String background_color) {
		this.background_color = background_color;
	}

	/**
	 * @return the close_button_color
	 */
	public String getClose_button_color() {
		return close_button_color;
	}

	/**
	 * @param close_button_color the close_button_color to set
	 */
	public void setClose_button_color(String close_button_color) {
		this.close_button_color = close_button_color;
	}

	/**
	 * @return the slide_from
	 */
	public String getSlide_from() {
		return slide_from;
	}

	/**
	 * @param slide_from the slide_from to set
	 */
	public void setSlide_from(String slide_from) {
		this.slide_from = slide_from;
	}

	/**
	 * @return the message_close
	 */
	public String getMessage_close() {
		return message_close;
	}

	/**
	 * @param message_close the message_close to set
	 */
	public void setMessage_close(String message_close) {
		this.message_close = message_close;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the icon_color
	 */
	public String getIcon_color() {
		return icon_color;
	}

	/**
	 * @param icon_color the icon_color to set
	 */
	public void setIcon_color(String icon_color) {
		this.icon_color = icon_color;
	}

	/**
	 * @return the icon_background_color
	 */
	public String getIcon_background_color() {
		return icon_background_color;
	}

	/**
	 * @param icon_background_color the icon_background_color to set
	 */
	public void setIcon_background_color(String icon_background_color) {
		this.icon_background_color = icon_background_color;
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
	 * @return the buttons
	 */
	public List<Button> getButtons() {
		return buttons;
	}

	/**
	 * @param buttons the buttons to set
	 */
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	/**
	 * @return the extras
	 */
	public HashMap<String, String> getExtras() {
		return extras;
	}

	/**
	 * @param extras the extras to set
	 */
	public void setExtras(HashMap<String, String> extras) {
		this.extras = extras;
	}

	/**
	 * @return the ios_click_action
	 */
	public String getIos_click_action() {
		return ios_click_action;
	}

	/**
	 * @param ios_click_action the ios_click_action to set
	 */
	public void setIos_click_action(String ios_click_action) {
		this.ios_click_action = ios_click_action;
	}

	/**
	 * @return the android_click_action
	 */
	public String getAndroid_click_action() {
		return android_click_action;
	}

	/**
	 * @param android_click_action the android_click_action to set
	 */
	public void setAndroid_click_action(String android_click_action) {
		this.android_click_action = android_click_action;
	}

	/**
	 * @return the windows_click_action
	 */
	public String getWindows_click_action() {
		return windows_click_action;
	}

	/**
	 * @param windows_click_action the windows_click_action to set
	 */
	public void setWindows_click_action(String windows_click_action) {
		this.windows_click_action = windows_click_action;
	}

	/**
	 * @return the windows8_click_action
	 */
	public String getWindows8_click_action() {
		return windows8_click_action;
	}

	/**
	 * @param windows8_click_action the windows8_click_action to set
	 */
	public void setWindows8_click_action(String windows8_click_action) {
		this.windows8_click_action = windows8_click_action;
	}

	/**
	 * @return the kindle_click_action
	 */
	public String getKindle_click_action() {
		return kindle_click_action;
	}

	/**
	 * @param kindle_click_action the kindle_click_action to set
	 */
	public void setKindle_click_action(String kindle_click_action) {
		this.kindle_click_action = kindle_click_action;
	}

	/**
	 * @return the android_china_click_action
	 */
	public String getAndroid_china_click_action() {
		return android_china_click_action;
	}

	/**
	 * @param android_china_click_action the android_china_click_action to set
	 */
	public void setAndroid_china_click_action(String android_china_click_action) {
		this.android_china_click_action = android_china_click_action;
	}

	/**
	 * @return the web_click_action
	 */
	public String getWeb_click_action() {
		return web_click_action;
	}

	/**
	 * @param web_click_action the web_click_action to set
	 */
	public void setWeb_click_action(String web_click_action) {
		this.web_click_action = web_click_action;
	}

	/**
	 * @return the ios_uri
	 */
	public String getIos_uri() {
		return ios_uri;
	}

	/**
	 * @param ios_uri the ios_uri to set
	 */
	public void setIos_uri(String ios_uri) {
		this.ios_uri = ios_uri;
	}

	/**
	 * @return the android_uri
	 */
	public String getAndroid_uri() {
		return android_uri;
	}

	/**
	 * @param android_uri the android_uri to set
	 */
	public void setAndroid_uri(String android_uri) {
		this.android_uri = android_uri;
	}

	/**
	 * @return the windows_uri
	 */
	public String getWindows_uri() {
		return windows_uri;
	}

	/**
	 * @param windows_uri the windows_uri to set
	 */
	public void setWindows_uri(String windows_uri) {
		this.windows_uri = windows_uri;
	}

	/**
	 * @return the windows8_uri
	 */
	public String getWindows8_uri() {
		return windows8_uri;
	}

	/**
	 * @param windows8_uri the windows8_uri to set
	 */
	public void setWindows8_uri(String windows8_uri) {
		this.windows8_uri = windows8_uri;
	}

	/**
	 * @return the kindle_uri
	 */
	public String getKindle_uri() {
		return kindle_uri;
	}

	/**
	 * @param kindle_uri the kindle_uri to set
	 */
	public void setKindle_uri(String kindle_uri) {
		this.kindle_uri = kindle_uri;
	}

	/**
	 * @return the android_china_uri
	 */
	public String getAndroid_china_uri() {
		return android_china_uri;
	}

	/**
	 * @param android_china_uri the android_china_uri to set
	 */
	public void setAndroid_china_uri(String android_china_uri) {
		this.android_china_uri = android_china_uri;
	}

	/**
	 * @return the web_uri
	 */
	public String getWeb_uri() {
		return web_uri;
	}

	/**
	 * @param web_uri the web_uri to set
	 */
	public void setWeb_uri(String web_uri) {
		this.web_uri = web_uri;
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
	 * @return the rEGEXP_VALID_CLICK_TYPE
	 */
	public static String getREGEXP_VALID_CLICK_TYPE() {
		return REGEXP_VALID_CLICK_TYPE;
	}

	/**
	 * @return the rEGEXP_VALID_FORM_TYPE
	 */
	public static String getREGEXP_VALID_FORM_TYPE() {
		return REGEXP_VALID_FORM_TYPE;
	}

	/**
	 * @return the rEGEXP_VALID_SWIPE_ACTION
	 */
	public static String getREGEXP_VALID_SWIPE_ACTION() {
		return REGEXP_VALID_SWIPE_ACTION;
	}

	/**
	 * @return the rEGEXP_VALID_CLOSE_ACTION
	 */
	public static String getREGEXP_VALID_CLOSE_ACTION() {
		return REGEXP_VALID_CLOSE_ACTION;
	}
	
}
