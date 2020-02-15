/**
 * 
 */
package com.acme.services.push.config;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Class defines the property keys for accessing the configuration file defined properties
 */
public final class PropertyKeys {
	
	public static final String ENDPOINT = ".endpoint";	
	public static final String METHOD = ".method";
	public static final String CONTENTTYPE = ".contenttype";
	public static final String THREAD_DELAY = "thread.delay";
	public static final String PENDING_FOLDER = "pending.dir";
	public static final String PROCESSED_FOLDER = "processed.dir";	
	public static final String PROCESSING_FOLDER = "processing.dir";
	public static final String ARCHIVE_FOLDER = "archive.dir";
	public static final String SERVICE_FOLDER_MAX_FILES = "service.dir.max.files";
	public static final String FAILED_FOLDER = "failed.dir";
	public static final String LOG_FOLDER = "log.dir";
	public static final String LOG_CONFIG = "log.config";
	public static final String MESSAGE_SEND_DELAY = "message.send.delay";
	public static final String MESSAGE_EXPIRE = "message.expire";	
	public static final String SUPPORTED_DEVICES = "application.supported.devices.definition";
	public static final String APPLICATION_GROUP_PREFIX = "application.group.";
	public static final String COMPANY_IDENTIFIER_PREFIX = "company.identifier.";
	public static final String CAMPAIGN_LIST_TYPES = "trigger.campaign.list.definition";
	public static final String CAMPAIGN_DEFINE_DEVICE_ID = "campaigns.definition.%s.%s.id";
	public static final String CAMPAIGN_DEFINE_MSG_VARIATION_ID = "campaigns.definition.%s.%s.msg.variation.id";
	public static final String SMTP_LOGGING_HOST = "service.email.SMTPHost";
	public static final String SMTP_LOGGING_FROM = "service.email.From";
	public static final String SMTP_LOGGING_TO = "service.email.To";
	public static final String SMTP_LOGGING_SUBJECT = "service.email.Subject";
	public static final String SMTP_LOGGING_BUFFER_SIZE = "service.email.BufferSize";
	public static final String SMTP_LOGGING_PORT = "service.email.Port";
	public static final String SMTP_LOGGING_LAYOUT = "service.email.layout";
	public static final String SMTP_LOGGING_PATTERN = "service.email.layout.ConversionPattern";
	public static final String SMTP_LOGGING_ENABLED = "service.email.enabled";
	public static final String DATA_MAP_ROOT_KEY = "data.mapping.reference.ROOT";
	public static final String DATA_MAP_ORDER_KEY = "data.mapping.reference.ORDER";	
	public static final String DATA_MAP_RECIPIENT_KEY = "data.mapping.reference.RECIPIENT";
	public static final String DATA_MAP_PRODUCT_DETAIL_KEY = "data.mapping.reference.PRODUCT_ITEM";
	public static final String DATA_MAP_ORDER_NUMBER_KEY = "data.mapping.required.ORDER_NUMBER";
	public static final String DATA_MAP_ORDER_DATE_KEY = "data.mapping.reference.ORDER_DATE";
	public static final String DATA_MAP_EXTERNAL_USER_ID_KEY = "data.mapping.required.EXTERNAL_USER_ID";
	public static final String DATA_MAP_DEVICE_CODE_KEY =	"data.mapping.required.DEVICE_CODE";	
	public static final String DATA_MAP_NOTIFICATION_TYPE_KEY = "data.mapping.required.NOTIFICATION_TYPE";		
	public static final String DATA_MAP_STORE_BRAND_KEY = "data.mapping.reference.STORE_BRAND";	
	public static final String DATA_MAP_NOTIFICATION_APPLE_KEY = "data.mapping.reference.notification.key.apple";
	public static final String DATA_MAP_NOTIFICATION_ANDROID_KEY = "data.mapping.reference.notification.key.android";
	public static final String DATA_MAP_BROADCAST_KEY = "data.mapping.reference.BROADCAST";	
	public static final String DATA_MAP_SMS_OPTIN_KEY = "data.mapping.reference.SMSOPT";
	public static final String DATA_MAP_USER_ALIAS_KEY = "data.mapping.required.USER_ALIAS";
	public static final String DATA_MAP_ORDER_ITEM_COUNT_LABEL_KEY = "data.mapping.reference.NUMITEMS";	
	public static final String ACTION_MAPPING_PREFIX = "data.transform.reference.";	
	public static final String DEVICE_MAPPING_PREFIX = "data.transform.reference.device.";	
	public static final String PAYLOAD_THRESHHOLD = "payload.threshhold.size";
	public static final String SERVICE_THREADS_MAX = "service.threads.maximum";	
	public static final String SERVICE_THREADS_MIN = "service.threads.initial";
	public static final String SERVICE_THREADS_POOL_DELAY = "service.threads.pool.delay";
	public static final String SERVICE_THREADS_KEEP_ALIVE = "service.threads.keepalive";
	public static final String SERVICE_THREADS_POOL_SLEEP = "service.threads.pool.sleep";
	public static final String SERVICE_THREADS_MONITOR_SLEEP = "service.threads.monitor.sleep";
	public static final String SERVICE_THREADS_MONITOR_INTERVAL = "service.threads.monitor.interval";	
	public static final String DATA_CLEAN_REPLACE_CHAR_LIST = "data.transform.replace.character.regex";
	public static final String SERVICE_MAX_ASYNC_CLIENTS = "service.http.async.max.clients";
	public static final String SERVICE_MAX_ASYNC_FUTURE_MAX_WAIT = "service.http.async.future.max.wait";
	public static final String SERVICE_SMS_DEFAULT_OPTIN = "trigger.sms.default.optin";
	public static final String SERVICE_DEFAULT_STORE_BRAND = "service.store.brand.default";
	public static final String SERVICE_DEFAULT_DEVICE_CODE = "service.reference.device.default";
	public static final String SERVICE_DEFAULT_NO_SMS_CAMPAIGN = "campaigns.definition.nosms.campaign.default";	
	public static final String DATA_SHOW_EXTERNAL_USER_ID_FLAG = "data.show.external_user_id.flag";
	public static final String DATA_FEED_REFERENCE_TYPE_LIST = "data.feed.type.reference.list";
	public static final String DATA_FEED_REFERENCE_KEY = "data.feed.type.reference.%s.id";
	public static final String DATA_FEED_REFERENCE_FEED_FIELDS_LIST = "data.feed.type.reference.%s.fields.list";
	public static final String DATA_FEED_REFERENCE_FIELD_VALUES = "data.feed.type.reference.%s.field.%s.value";
	public static final String DATA_FEED_REFERENCE_FIELD_DATA_ROOT = "data.feed.type.reference.%s.fields.data.root";
	public static final String DATA_FEED_REFERENCE_FIELD_DATA_SOURCE_MAPPING = "data.feed.type.reference.%s.fields.map.%s.code";
	public static final String DATA_FEED_REFERENCE_FIELD_DATA_SOURCE = "data.feed.type.reference.%s.fields.map.source.id";
	public static final String DATA_MAPPING_REFERENCE_FIELDS_BILLING = "data.mapping.reference.billing.fields";
	public static final String DATA_RECORD_DESCRIPTOR_ID = "data.record.%s.descriptor.id";
	public static final String DATA_RECORD_DESCRIPTOR_PRICE_ID = "data.record.%s.descriptor.%s.id";
	public static final String DATA_MAP_EMAIL_KEY = "data.feed.type.reference.%s.fields.data.EMAIL";
	public static final String DATA_MAP_FIRSTNAME_KEY = "data.feed.type.reference.%s.fields.data.FIRSTNAME";
	public static final String DATA_MAP_LASTNAME_KEY = "data.feed.type.reference.%s.fields.data.LASTNAME";
	public static final String DATA_MAP_PHONE_KEY = "data.feed.type.reference.%s.fields.data.PHONE";
	public static final String DATA_MAP_SOURCE_KEY = "data.feed.type.reference.%s.fields.data.SOURCE";	
	public static final String DATA_MAP_SMSOPT_KEY = "data.feed.type.reference.%s.fields.data.SMSOPT";
	
	public final static String getCompositeProperty(String ref, String key){
		
		return String.format(ref, key);
	}

	public final static String getCompositeProperty(String ref, String key1, String key2){
		
		return String.format(ref, key1, key2);
	}

	public final static String getCompositeProperty(String ref, String key1, String key2, String key3){
		
		return String.format(ref, key1, key2, key3);
	}

	/** Private no param constructor to ensure that this class is not instantiated; also final class so it cannot be extended. */
	private PropertyKeys(){
		
	}
}
