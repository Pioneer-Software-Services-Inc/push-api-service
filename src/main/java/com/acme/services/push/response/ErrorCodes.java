/**
 * 
 */
package com.acme.services.push.response;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Push defined return codes:
 *
 *	400 Bad Request - Bad syntax.
 *	400 No Recipients - There are no external IDs or segment IDs or no push tokens in the request
 *	400 Invalid Campaign ID - No Messaging API Campaign was found for the campaign ID you provided
 *	400 Message Variant Unspecified - You provide a campaign ID but no message variation ID
 *	400 Invalid Message Variant - You provided a valid campaign ID, but the message variation ID doesnt match any of that campaigns messages
 *	400 Mismatched Message Type - You provided a message variation of the wrong message type for at least one of your messages
 *	400 Invalid Extra Push Payload - You provide the extra key for either apple_push or android_push but it is not a dictionary
 *	400 Max input length exceeded - Caused by: More than 50 external ids
 *	400 No message to send - No payload is specified for the message
 *	400 Slideup Message Length Exceeded - Slideup message > 140 characters
 *	400 Apple Push Length Exceeded - JSON payload > 1912 bytes
 *	400 Android Push Length Exceeded - JSON payload > 4000 bytes
 *	400 Bad Request - Cannot parse send_at datetime
 *	400 Bad Request - in_local_time is true but time has passed in your company�s time zone
 *	401 Unauthorized - Unknown or missing app group id
 *	403 Forbidden - Rate plan doesnt support or account is otherwise inactivated
 *	404 Not Found - Unknown App Group ID
 *	429 Rate limited - Over rate limit
 *	5XX - Internal server error, you should retry your request with exponential backoff  
 *
 */
public final class ErrorCodes {

	public static final int ERROR_BAD_REQUEST = 400;
	public static final int ERROR_UNAUTHORIZED = 401;
	public static final int ERROR_FORBIDDEN = 403;
	public static final int ERROR_NOT_FOUND = 404;
	public static final int ERROR_RATE_LIMITED = 429;
	public static final int ERROR_SERVER_ERROR = 500;

	public static final String MSG_BAD_REQUEST_SYNTAX = "Bad Request - Bad syntax.";
	public static final String MSG_BAD_REQUEST_RECEPIENT = "No Recipients - There are no external IDs or segment IDs or no push tokens in the request";
	public static final String MSG_BAD_REQUEST_INV_CAMPAIGN = "Invalid Campaign ID - No Messaging API Campaign was found for the campaign ID you provided";
	public static final String MSG_BAD_REQUEST_VARIATION = "Message Variant Unspecified - You provide a campaign ID but no message variation ID";
	public static final String MSG_BAD_REQUEST_BAD_VARIATION = "Invalid Message Variant - You provided a valid campaign ID, but the message variation ID doesn�t match any of that campaign�s messages";
	public static final String MSG_BAD_REQUEST_MESSAGE = "Mismatched Message Type - You provided a message variation of the wrong message type for at least one of your messages";
	public static final String MSG_BAD_REQUEST_PUSH = "Invalid Extra Push Payload - You provide the �extra� key for either �apple_push� or �android_push� but it is not a dictionary";
	public static final String MSG_BAD_REQUEST_LENGTH = "Max input length exceeded - Caused by:";
	public static final String MSG_BAD_REQUEST_EXCESS_IDS = "More than 50 external ids";
	public static final String MSG_BAD_REQUEST_PAYLOAD = "No message to send - No payload is specified for the message";
	public static final String MSG_BAD_REQUEST_SLIDEUP = "Slideup Message Length Exceeded - Slideup message > 140 characters";
	public static final String MSG_BAD_REQUEST_APPLE_LEN = "Apple Push Length Exceeded - JSON payload > 1912 bytes";
	public static final String MSG_BAD_REQUEST_ANDROID_LEN = "Android Push Length Exceeded - JSON payload > 4000 bytes";
	public static final String MSG_BAD_REQUEST_PARSE = "Bad Request - Cannot parse send_at datetime";
	public static final String MSG_BAD_REQUEST_TIMEZONE = "Bad Request - in_local_time is true but time has pased in your company�s time zone";
	public static final String MSG_UNAUTHORIZED_BAD_SYNTAX = "Unauthorized - Unknown or missing app group id";
	public static final String MSG_FORBIDDEN_BAD_SYNTAX = "Forbidden - Rate plan doesn�t support or account is otherwise inactivated";
	public static final String MSG_NOT_FOUND_BAD_SYNTAX = "Not Found - Unknown App Group ID";
	public static final String MSG_RATE_LIMITED_BAD_SYNTAX = "Rate limited - Over rate limit";
	public static final String MSG_SERVER_ERROR_BAD_SYNTAX = "5XX - Internal server error, you should retry your request with exponential backoff";	

	private ErrorCodes(){
		
	}
}
