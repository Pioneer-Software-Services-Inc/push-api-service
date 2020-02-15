/**
 * 
 */
package com.acme.services.push.file.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.facade.FunctionFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.config.PropertyKeys;
import static com.acme.services.push.constants.Constants.*;
import static com.acme.services.push.facade.ParserFacade.*;
import com.acme.services.push.file.DataTransferObject;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 10/16/2017
 * @version 1.0
 * 
 * The purpose of this class is to parse the order input xml document and extract the data and then place its contents 
 * in a (convenience) data transfer object.
 *
 */
public class OrderFileParser extends GenericFileParser{
	
	/** This method is tasked with parsing order input xml file and returning a map of  
	 * 
	 * @param data as a XML input <code>File</code> type class 
	 * @returns <code>DataTransferObject</code> Data Transfer Object pattern containing the organized data for populating thev request 
	 * object to send to push. 
	 * @exception <code>ParserConfigurationException</code> If an ParserConfigurationException error occurs.
	 * @exception <code>IOException</code> If an IOException error occurs.
	 * @exception <code>SAXException</code> If an SAXException error occurs.  
	 */	
	public List<Set<DataTransferObject>> parseXml(@NotNull File xmlFile, @NotNull final String root) 
	throws ParserConfigurationException, IOException, SAXException{

		logger.info("\n");
		logger.info("XML document parse started\n");
		final long start = System.nanoTime();
		
		DocumentBuilderFactory dbFactory = getOwaspDocumentBuilderFactory();
		DocumentBuilder dBuilder;
		
		final String xmlData = readEncodeXmlFile(xmlFile);
		logger.debug("xml clean input: {}", xmlData);
		
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
		doc.getDocumentElement().normalize();
		
		final String action_ref = getResourceProperty(PropertyKeys.DATA_MAP_ORDER_KEY);
		final String action = getResourceProperty(String.format("%s%s", ACTION_MAPPING_PREFIX, action_ref));
		logger.debug("document root element -> resolved action : {}", action);
		
		List<Set<DataTransferObject>> data = parseXml(doc, action_ref);
		
		logger.info(" Parse (Order) xml elapse time: {} (milliseconds) for file {}", 
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS), xmlFile.getName());
		logger.info("XML document parse ended\n");	
		
		return data;
	}	
	
	/** This method is tasked with parsing  the input xml file and returning a map of  
	 * 
	 * @param data document <code>Document</code> type class 
	 * @param action type reference <code>String</code> type class 
	 * @returns <code>DataTransferObject</code> Data Transfer Object pattern containing the organized data for populating thev request 
	 * object to send to push. 
	 * @exception <code>ParserConfigurationException</code> If an ParserConfigurationException error occurs.
	 * @exception <code>IOException</code> If an IOException error occurs.
	 * @exception <code>SAXException</code> If an SAXException error occurs.  
	 */	
	public List<Set<DataTransferObject>> parseXml(@NotNull Document doc, @NotNull final String action_ref) 
	throws ParserConfigurationException, IOException, SAXException{
		logger.info("\n");
		logger.info("Process Order document started\n");
		
		final String action = getResourceProperty(String.format("%s%s", ACTION_MAPPING_PREFIX, action_ref));
		
		DataTransferObject row = null;
		List<Set<DataTransferObject>> data = null; 
		Hashtable<String, String> fields = null; 		
		Set<DataTransferObject> itegrp = new HashSet<DataTransferObject>();		
				
		final String campaign_key = getResourceProperty(DATA_MAP_NOTIFICATION_TYPE_KEY);
		final String device_key = getResourceProperty(DATA_MAP_DEVICE_CODE_KEY);			
		final String broadcast = getResourceProperty(DATA_MAP_BROADCAST_KEY);
		final String order_number_key = getResourceProperty(DATA_MAP_ORDER_NUMBER_KEY);
		final String external_user_id_key = getResourceProperty(DATA_MAP_EXTERNAL_USER_ID_KEY);
		final String recipient_key = getResourceProperty(DATA_MAP_RECIPIENT_KEY);
		final String products_details_key = getResourceProperty(DATA_MAP_PRODUCT_DETAIL_KEY);	
		final String sms_optin_key = getResourceProperty(DATA_MAP_SMS_OPTIN_KEY);
		final String store_brand_key = getResourceProperty(DATA_MAP_STORE_BRAND_KEY);
		final String order_date_key = getResourceProperty(DATA_MAP_ORDER_DATE_KEY);
		final String user_alias_key = getResourceProperty(DATA_MAP_USER_ALIAS_KEY);
		
		logger.debug("resolved action : {}", action);
		
		logger.debug("Root element : {}", doc.getDocumentElement().getNodeName()); //ORDERS
		NodeList nodeList = doc.getElementsByTagName(action_ref);
	
		for (int i_order = ZERO; i_order < nodeList.getLength(); i_order++) {

			logger.debug("xml tree traversal from root..\n");
			Element order = ((Element)nodeList.item(i_order));
			
			logger.debug(" order detail.. key : ref: {}, val: {}", order_number_key, (order).getElementsByTagName(order_number_key).item(ZERO).getTextContent());
			logger.debug(" order detail..campaign.. value : {}", (order).getElementsByTagName(campaign_key).item(ZERO).getTextContent());

			final String device_ref = (order).getElementsByTagName(device_key).item(ZERO).getTextContent();
			
			final String device = new Object() {
			
				List<String> supportedDevices = getResourcePropertyList(SUPPORTED_DEVICES);
				
				public String getDevice() {
					return supportedDevices.stream()
							.filter(s -> device_ref.equals(getResourceProperty((DEVICE_MAPPING_PREFIX.concat(s)))))
							.findFirst().orElse(getResourceProperty(SERVICE_DEFAULT_DEVICE_CODE));
				}
			}.getDevice();

			String external_user_id = (order).getElementsByTagName(external_user_id_key)==null || (order).getElementsByTagName(external_user_id_key).item(ZERO)==null? 
					_BLANK :
						(order).getElementsByTagName(external_user_id_key).item(ZERO).getTextContent();

			String smsOptIn = (order).getElementsByTagName(sms_optin_key)==null || (order).getElementsByTagName(sms_optin_key).item(ZERO)==null? 
					getResourceProperty(SERVICE_SMS_DEFAULT_OPTIN) :
						(order).getElementsByTagName(sms_optin_key).item(ZERO).getTextContent();

			String brand = (order).getElementsByTagName(store_brand_key)==null || (order).getElementsByTagName(store_brand_key).item(ZERO)==null? 
					getResourceProperty(SERVICE_DEFAULT_STORE_BRAND) :
						(order).getElementsByTagName(store_brand_key).item(ZERO).getTextContent();

			String email = (order).getElementsByTagName(user_alias_key)==null || (order).getElementsByTagName(user_alias_key).item(ZERO)==null? 
							_BLANK : (order).getElementsByTagName(user_alias_key).item(ZERO).getTextContent();

			logger.debug(" calculated from xml device: {}", device);
			
			int total_items = (order).getElementsByTagName(getResourceProperty(DATA_MAP_PRODUCT_DETAIL_KEY)).getLength();

			fields = new Hashtable<String, String> ();
			fields.put(LABEL_ACTION, action);
			fields.put(LABEL_BROADCAST, broadcast);
			fields.put(LABEL_TOTAL_ITEMS, String.valueOf(total_items));
			fields.put(LABEL_DEVICE, device);
			fields.put(LABEL_CAMPAIGN, (order).getElementsByTagName(campaign_key).item(ZERO).getTextContent() );
			fields.put(LABEL_ORDER_NUMBER, (order).getElementsByTagName(order_number_key).item(ZERO).getTextContent());
			fields.put(LABEL_ORDER_DATE, (order).getElementsByTagName(order_date_key)!=null? 
					(order).getElementsByTagName(order_date_key).item(ZERO).getTextContent() : _BLANK);
			
			Boolean show_ext_id = Boolean.parseBoolean(getResourceProperty(DATA_SHOW_EXTERNAL_USER_ID_FLAG));
			
			fields.put(LABEL_EXTERNAL_USER_ID, show_ext_id? external_user_id : _BLANK);	
			fields.put(LABEL_SMS_OPTIN, smsOptIn==null||smsOptIn.isEmpty()? 
					getResourceProperty(SERVICE_SMS_DEFAULT_OPTIN) : smsOptIn); 
			
			fields.put(LABEL_STORE_BRAND, brand==null||brand.isEmpty()? 
					getResourceProperty(SERVICE_DEFAULT_STORE_BRAND) : brand); 

			fields.put(LABEL_USER_EMAIL, email==null||email.isEmpty()? _BLANK : email); 

			List<String> billingFields = getResourcePropertyList(PropertyKeys.DATA_MAPPING_REFERENCE_FIELDS_BILLING);
			
			final Hashtable<String, String> tmp = new Hashtable<String, String>(); 
			billingFields.parallelStream()
					.filter(d -> !StringUtils.isAllBlank(d))
					.map(e -> 	{
						logger.debug("billing key={}", e);
						Hashtable<String, String> values = new Hashtable<String, String>(); 
						String key=e.replaceAll(_WHITESPACE, _BLANK);
						values.put(key.toLowerCase(), 
							(order).getElementsByTagName(key)!=null && (order).getElementsByTagName(key).item(ZERO)!=null? 
							(order).getElementsByTagName(key).item(ZERO).getTextContent() : _BLANK);
						
						return values;
					}).forEach(map -> tmp.putAll(map));
			
			fields.putAll(tmp);
			//for(String key : billingFields) {
			//	logger.debug("billing key={}", key);
			//	
			//	if(StringUtils.isAllBlank(key)) continue;
			//	key=key.replaceAll(_WHITESPACE, _BLANK);
			//	fields.put(key.toLowerCase(), (order).getElementsByTagName(key)!=null && (order).getElementsByTagName(key).item(ZERO)!=null? 
			//			(order).getElementsByTagName(key).item(ZERO).getTextContent() : _BLANK);
			//}
			
			NodeList recipientsNodeList = (order).getElementsByTagName(recipient_key);
			String removeCharList = getResourceProperty(DATA_CLEAN_REPLACE_CHAR_LIST);

			Hashtable<String, String> recipientProps = new Hashtable<String, String>();

			long records=0;
			for (int i_recipient = ZERO; i_recipient < recipientsNodeList.getLength(); i_recipient++) {
				
				records++;
				if(!(recipientsNodeList.item(i_recipient) instanceof Element)) continue;
				NodeList recipient_fields = ((Element)recipientsNodeList.item(i_recipient)).getChildNodes();

				for (int i_recipient_field = ZERO; i_recipient_field < recipient_fields.getLength(); i_recipient_field++) {

					if(!(recipient_fields.item(i_recipient_field) instanceof Element)) continue;

					if(((Element)recipient_fields.item(i_recipient_field)).getTagName()!=null 
							&& !((Element)recipient_fields.item(i_recipient_field)).getTagName().equalsIgnoreCase(products_details_key)){
						Element dta = ((Element)recipient_fields.item(i_recipient_field)) ;
						if(dta==null) continue;
						logger.debug(" recipient data: key={} | value={}", formatKey(dta.getTagName(), i_recipient), dta.getTextContent().replaceAll(removeCharList, ""));
						recipientProps.put(formatKey(dta.getNodeName(), i_recipient), dta.getTextContent().replaceAll(removeCharList, ""));
					}
				}
				
				int itm_index = 1;
				for (int i_recipient_field = ZERO; i_recipient_field < recipient_fields.getLength(); i_recipient_field++) {

					row = new DataTransferObject();
					
					if(!(recipient_fields.item(i_recipient_field) instanceof Element)) continue;
					Hashtable<String, String> itemsProps = new Hashtable<String, String>();
					
					if(((Element)recipient_fields.item(i_recipient_field)).getTagName()!=null 
							&& ((Element)recipient_fields.item(i_recipient_field)).getTagName().equalsIgnoreCase(products_details_key)){

						NodeList item_fields = ((Element)recipient_fields.item(i_recipient_field)).getChildNodes();
						itemsProps = new Hashtable<String, String>();
						for (int i_item_field = ZERO; i_item_field < item_fields.getLength(); i_item_field++) {

							if(!(item_fields.item(i_item_field) instanceof Element)) continue;
							Element dta = ((Element)item_fields.item(i_item_field));
							if(dta==null) continue;
							logger.debug(" product data: key={} | value={} ", formatKey(dta.getTagName(), i_recipient, itm_index), dta.getTextContent().replaceAll(removeCharList, ""));
							itemsProps.put(formatKey(dta.getNodeName(), i_recipient, itm_index), dta.getTextContent().replaceAll(removeCharList, ""));
						}
						
						itemsProps.putAll(transformKey(itemsProps, String::toLowerCase));
						logger.debug(" trigger properties (item level) for to add to recipient: {}",printMap(itemsProps));
						
						recipientProps.putAll(itemsProps);
						
						row.setFields(fields);
						row.setTrig_props(recipientProps);

						itm_index++;
					}
				}
				
				String priceRef = getResourceProperty(String.format(DATA_RECORD_DESCRIPTOR_PRICE_ID, LABEL_KEY_ORDER, LABEL_PRICE));
				double total = recipientProps.entrySet().stream()
						.map(d -> d.getKey().contains(priceRef)? Float.parseFloat(d.getValue()):0F).reduce(0F, (x, y) -> x + y);		
			
				logger.info("calculated order total: recepient: {} - amt: ${}", i_recipient, String.format("%.2f", total));

				fields.put(LABEL_CALCULATED_ORDER_TOTAL, String.format("%.2f", total));
				fields.put(LABEL_TOTAL_RECIPIENTS, String.valueOf(i_recipient));

				fields.putAll(transformKey(fields, String::toLowerCase));
				logger.debug(" trigger properties (recipient level) for to add to recipient: {}", printMap(fields));
				
				row.setFields(fields);				
				row.setTrig_props(recipientProps);
				itegrp.add(row);				
				
				data = data==null? new ArrayList<Set<DataTransferObject>>() : data;
				data.add(itegrp);	
				
				recipientProps = new Hashtable<String, String>();
				logger.debug("\n#######\n receiver push data added: {}, records: {} \n#######\n", itegrp, records);
				itegrp = null;
				itegrp = new HashSet<DataTransferObject>();
			}
			
		}

		logger.debug("\n#######\n exit parser data dump: {}, records: {} \n#######\n", data, data.size());
		return data;
	}
}
