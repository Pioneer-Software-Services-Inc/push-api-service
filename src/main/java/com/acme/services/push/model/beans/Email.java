/**
 * 
 */
package com.acme.services.push.model.beans;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.services.push.constants.Constants;

import javax.validation.constraints.Pattern;


/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * @see <a href="https://www.push.com/documentation/REST_API/#email-object-specification">Push Email Object specification</a>
 *
 *{
 *  app_id": (required, string) see App Identifier above,
 *  subject": (optional, string),
 *  from": (required, valid email address in the format "Display Name <email@address.com>"),
 *  reply_to": (optional, valid email address in the format "email@address.com" - defaults to your app group's default reply to if not set),
 *  body": (required unless email_template_id is given, valid HTML),
 *  plaintext_body": (optional, valid plaintext, defaults to autogenerating plaintext from "body" when this is not set),
 *  email_template_id": (optional, string) If provided, we will use the subject/body values from the given email template UNLESS they are specified here, in which case we will override the provided template,
 *  message_variation_id": (optional, string) used when providing a campaign_id to specify which message variation this message should be tracked under,
 *  extras": (optional, valid Key Value Hash), extra hash - for SendGrid customers, this will be passed to SendGrid as Unique Arguments,
 *  headers": (optional, valid Key Value Hash), hash of custom extensions headers. Currently, only supported for SendGrid customers
 *}
 */
@JsonRootName(value = "email")
public class Email extends Model{

	@NotNull
	@JsonProperty(value="app_id",required=true)
	private String app_id  = Constants._BLANK;

	@JsonProperty("subject")	
	private String subject = Constants._BLANK;

	@NotNull
	@Pattern(regexp=Constants.REGEXP_VALID_EMAIL)
	@JsonProperty(value="from",required=true)
	private String from = Constants._BLANK;

	@JsonProperty("reply_to")	
	@Pattern(regexp=Constants.REGEXP_VALID_EMAIL)	
	private String reply_to = Constants._BLANK;

	@NotNull
	@JsonProperty(value="body",required=true)
	private String body = Constants._BLANK;

	@JsonProperty("plaintext_body")	
	private String plaintext_body = Constants._BLANK;

	@JsonProperty("email_template_id")	
	private String email_template_id = Constants._BLANK;

	@JsonProperty("message_variation_id")	
	private String message_variation_id = Constants._BLANK;

	@JsonProperty("extras")	
	private String extras = Constants._BLANK;

	@JsonProperty("headers")	
	private String headers = Constants._BLANK;

	/**
	 * @return the app_id
	 */
	public String getApp_id() {
		return app_id;
	}

	/**
	 * @param app_id the app_id to set
	 */
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the reply_to
	 */
	public String getReply_to() {
		return reply_to;
	}

	/**
	 * @param reply_to the reply_to to set
	 */
	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the plaintext_body
	 */
	public String getPlaintext_body() {
		return plaintext_body;
	}

	/**
	 * @param plaintext_body the plaintext_body to set
	 */
	public void setPlaintext_body(String plaintext_body) {
		this.plaintext_body = plaintext_body;
	}

	/**
	 * @return the email_template_id
	 */
	public String getEmail_template_id() {
		return email_template_id;
	}

	/**
	 * @param email_template_id the email_template_id to set
	 */
	public void setEmail_template_id(String email_template_id) {
		this.email_template_id = email_template_id;
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
	 * @return the extras
	 */
	public String getExtras() {
		return extras;
	}

	/**
	 * @param extras the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}

	/**
	 * @return the headers
	 */
	public String getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(String headers) {
		this.headers = headers;
	}	

}
