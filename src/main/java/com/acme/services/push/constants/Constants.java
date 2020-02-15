/**
 * 
 */
package com.acme.services.push.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.MessageScheduleCreate;
import com.acme.services.push.model.beans.activity.ScheduleCreate;
import com.acme.services.push.model.beans.activity.ScheduleDelete;
import com.acme.services.push.model.beans.activity.TriggerScheduleCreate;
import com.acme.services.push.model.beans.activity.TriggerScheduleDelete;
import com.acme.services.push.model.beans.activity.TriggerScheduleUpdate;
import com.acme.services.push.model.beans.activity.TriggerSend;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * constants are maintained here. general policy to not have or minimize rogue string literals throughout the application
 *
 */
public final class Constants {
	
	public static final String _WELL_FORMED_EMPTY_DOCLOCK = "<?xml version='1.0'?><_/>";
	public final static String _DELIMITER = "::";	
	public static final String LOCK = ".lck";
	public static final String _WHITESPACE = " ";
	public static final String _BLANK = "";
	public static final String _YES = "Y";
	public static final String _NO = "N";
	public static final String _EMPTY = "EMPTY_COLLECTION";
	public static final String DATEFORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm'Z'";
	public static final char _PIPE = '|';
	public static final char _UNDERSCORE = '_';
	public static final byte ZERO = (byte) 0x0;
	public static final String UTF8_BOM = "\uFEFF";	
	public static final Pattern XML_ENTITY_PATTERN = Pattern.compile("\\&\\#(?:x([0-9a-fA-F]+)|([0-9]+))\\;");	
	
	public static final String PROPERTIES_FILE_NAME = "config.properties";
	public static final String DEFAULT_PROCESSED_FOLDER = "/opt/IBM/services/xml/processed/"; 
	public static final String DEFAULT_PROCESSING_FOLDER =  "/opt/IBM/services/xml/processing/";
	public static final String PROPERTIES_EXT = ".properties";

	public static final short DEFAULT_TIMEOUT = (short) 0x1000;
	public static final short DEFAULT_THREAD_SLEEP = (short) 0x100;
	public static final String DEFAULT_ALIAS_LABEL = "email";
	public static final byte EXCEPTION_CODE_IO = (byte) 0x2;
	public static final byte EXCEPTION_CODE_GENERAL = (byte) 0x3;
	public static final byte EXCEPTION_CODE_ARG_UNDERFLOW = (byte) 0x4;
	
	public static final short HTTP_KEEP_ALIVE_TIME = (short) 1 * 30 * 1000;
	public static final short HTTP_SO_TIMEOUT = (short) 2000;
	public static final short MAX_PAYLOAD_LENGTH = (short) 8192 ; /** OWASP security recommended ceiling on payload size*/public static final short HTTP_CONNECTION_TIME_OUT = (short) 1 * 30 * 1000;
	
	public static final String DEFAULT_ENDPOINT = "https://api.push.com/campaigns/trigger/send";
	public static final String STRING_PUSH_OBJECTS_PACKAGE = "com.acme.services.push.model.beans.push.";
	public static final String DATA_MAPPING_REFERENCE_PREFIX = "data.mapping.reference.";
		
	public static final String LABEL_TEMP_FOLDER = "appTemp";
	public static final String FACTORY_PARSE_TYPE = "PARSE";
	public static final String FACTORY_PROCESS_TYPE = "PROCESS";
	public static final String FAILED_ARCHIVE = "failed.zip";
	public static final String PROCESSED_ARCHIVE = "processed.zip";	
	
	public static final String FACTORY_PROCESS_ORDER = "ORDER";
	public static final String FACTORY_PROCESS_GENERIC = "GENERIC";
	
	public static final String REGEXP_VALID_XML = "[^\\x20-\\x7e\\x0A]";
	public static final String REGEXP_VALID_EMAIL = "^(.+)@(.+)$";	
	public static final String REGEXP_VALID_PHONE = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
	public static final String REGEXP_VALID_FLAG = "^[Y|N]{1}$";
	public static final String REGEXP_VALID_ALIAS_LABEL = "^[email|phone]{1}$";
	public static final String REGEXP_TRUE = "^[Y|YES|T|TRUE|1]{1}$";
	public static final String REGEXP_VALID_URL = "^(http://|https://)?(www.)?([a-zA-Z0-9@:%_\\+.~#?&//=-]+).[a-zA-Z0-9@:%_\\+.~#?&//=-]+]*.[a-z]{3}.?([a-zA-Z0-9@:%_\\+.~#?&//=-]+)?$";	
	
	public final static String LABEL_TRIGGER_PROPERTIES = "trigger_properties";
	public final static String LABEL_ATTRIBUTES = "attributes";	
	public final static String LABEL_RECEPIENT = "recipient";
	public final static String LABEL_RECIPIENTS = "recipients";
	public final static String LABEL_ACTION = "action";
	public final static String LABEL_FIELDS = "fields";
	public final static String LABEL_PROPERTY = "property";	
	public final static String LABEL_PROPERTIES = "properties";	
	public final static String LABEL_CAMPAIGN = "campaign";
	public final static String LABEL_DEVICE = "device";	
	public final static String LABEL_ORDER_NUMBER = "order_number";	
	public final static String LABEL_ORDER_DATE = "order_date";
	public final static String LABEL_BROADCAST = "broadcast";
	public final static String LABEL_ACTIVITY = "activity";
	public final static String LABEL_EXTERNAL_USER_ID = "external_user_id";
	public final static String LABEL_USER_EMAIL = "user_email";
	public final static String LABEL_STORE_BRAND = "store_brand";	
	public final static String LABEL_ENTITY = "entity";
	public final static String LABEL_UNKNOWN = "unknown";
	public final static String LABEL_PRICE = "PRICE";	
	public final static String LABEL_SMS_OPTIN = "sms_optin";
	public final static String LABEL_PUSH_PROPS = "item_data";	
	public final static String LABEL_TOTAL_ITEMS= "numitems";	
	public final static String LABEL_TOTAL_RECIPIENTS= "num_recipients";
	public final static String LABEL_CALCULATED_ORDER_TOTAL= "order_total";
	public final static String LABEL_APP_GROUP_ID= "app_group_id";
	public final static String LABEL_EMAIL = "email";	
	public final static String LABEL_USER_ALIAS = "user_alias";
	public final static String LABEL_ORDER_TYPE = "ORDERS";	
	public final static String LABEL_TRACK_TYPE = "TRACK";
	public final static String LABEL_SUCCESS_FLAG = "HAS_ERRORS";
	public final static String LABEL_KEY_ORDER="ORDER";	
	public final static String LABEL_KEY_TRACK="TRACK";	

	public final static Map<String, Class<? extends Activity>> actionMap 
			= new HashMap<String, Class<? extends Activity>>() {
		
		private static final long serialVersionUID = 1L;
        {
            put("TRIGGER_SEND", TriggerSend.class);
            put("TRIGGER_SCHEDULE_CREATE", TriggerScheduleCreate.class);
            put("TRIGGER_SCHEDULE_UPDATE", TriggerScheduleUpdate.class);
            put("TRIGGER_SCHEDULE_DELETE", TriggerScheduleDelete.class);
            put("SCHEDULE_DELETE", ScheduleDelete.class);
            put("SCHEDULE_CREATE", ScheduleCreate.class);
            put("MESSAGE_SCHEDULE_CREATE", MessageScheduleCreate.class);
        };
	};
	
	public static final Set<String> factoryTypes = new HashSet<String>(Arrays.asList(FACTORY_PARSE_TYPE,FACTORY_PROCESS_TYPE));
	public static final String PARSE_RESOURCE_PACKAGE = "com.acme.services.push.file.xml";
	public static final String PROCESS_RESOURCE_PACKAGE = "com.acme.services.push.thread.tasks";
	
	public final static Map<String, String> packageMap 
	= new HashMap<String, String>() {

	private static final long serialVersionUID = 1L;
	{
	    put(FACTORY_PARSE_TYPE, PARSE_RESOURCE_PACKAGE);
	    put(FACTORY_PROCESS_TYPE, PROCESS_RESOURCE_PACKAGE);
	};
	};
	
	public final static String ERROR_SERVICE_MESSAGE = "Service not Found"; 

	/**
	 *  Declare class final and constructor private to defeat instantiation and extension
	 */
	private Constants(){
		
	}
}
