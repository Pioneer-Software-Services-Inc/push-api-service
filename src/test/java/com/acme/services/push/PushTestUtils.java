/**
 * 
 */
package com.acme.services.push;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.apache.commons.io.FileUtils;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.file.CharacterSet;
import com.acme.services.push.model.beans.Recipient;
import com.acme.services.push.model.beans.UserAlias;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.TriggerSend;
import com.acme.services.push.network.ContentType;
import com.acme.services.push.network.HttpMethod;
import com.acme.services.push.request.Request;

/**
 * @author cgordon
 * @created 10/24/2017 
 * @version 1.0
 *
 */
public class PushTestUtils {

	private static final File destDir = new File(TestConstants.appRoot+"/indata/pending");
	private static final File srcDir = new File(TestConstants.appRoot+"/indata/batch");
	
	public static boolean createTestData() {


		if(srcDir.exists() && srcDir.isDirectory()) {

			try {
				FileUtils.copyDirectory(srcDir, destDir);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		}

		return false;
	}

	public static Request buildRequest(@NotNull Activity activity, String mthd){

		if(!Optional.of(activity).isPresent()) return new Request();

		String endpoint = "https://api.push.com/campaigns/trigger/send";
		String method = mthd;
		String contenttype = "application/json";

		endpoint = endpoint!=null && endpoint.matches(Constants.REGEXP_VALID_URL)? endpoint : Constants.DEFAULT_ENDPOINT;
		method = method!=null && HttpMethod.validMethodTypes.contains(method)? method : HttpMethod.DEFAULT_METHOD;
		contenttype = contenttype!=null && ContentType.validContentTypes.contains(contenttype)? contenttype : ContentType.DEFAULT_CONTENTTYPE;

		return new Request(endpoint, method, contenttype, activity);
	}


	public static Activity buildActivity(){	

		Hashtable<String, String> trigger_properties = new Hashtable<String, String>();
		trigger_properties.put(Constants.LABEL_ORDER_NUMBER, "W001234567890");
		trigger_properties.put(Constants.LABEL_ORDER_DATE, "08-24-2017");
		trigger_properties.put(Constants.LABEL_BROADCAST, new Boolean(false).toString());
		trigger_properties.put(Constants.LABEL_STORE_BRAND, "1001");
		trigger_properties.put(Constants.LABEL_SMS_OPTIN, "y");
		trigger_properties.put(Constants.LABEL_DEVICE, "apple");
		trigger_properties.put(Constants.LABEL_TOTAL_ITEMS, "2");
		trigger_properties.put(Constants.LABEL_USER_EMAIL, "casmong@yahoo.com");

		Recipient recipient = new Recipient();
		recipient.setTrigger_properties(trigger_properties);
		recipient.setUser_alias( new UserAlias("casmong@yahoo.com"));
		List<Recipient> recipients = new ArrayList<Recipient>();
		recipients.add(recipient);

		Activity activity = new TriggerSend();
		((TriggerSend) activity).setApp_group_id("ajohgf890q3204fjw-i3tj23if");
		((TriggerSend) activity).setCampaign_id("28903frhenw-weufcjwpcf");
		((TriggerSend) activity).setTrigger_properties(trigger_properties);
		((TriggerSend) activity).setRecipients(recipients);

		return activity;
	}	

	
	public static void createTestEmailOptInData(int i) {
		
		while(i-->0) {
			createTestEmailOptInData();
		}
	}
	
	public static void createOptOrderProcessData(int i) {
		
		while(i-->0) {
			createOptOrderProcessData();
		}
	}
	
	public static void createTestEmailOptInData() {
		
		String filename = TestConstants.appRoot+"/indata/pending/test_email_opt_in";
				
		File file = new File(filename);
		int index=0;
		while(file.exists()) {
			file = new File(filename.concat("_0").concat(String.valueOf(++index)).concat(".xml"));
		}
			
		try {
			FileUtils.write(file, optEmailOpInData(), CharacterSet.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createOptOrderProcessData() {
		
		String filename = TestConstants.appRoot+"/indata/pending/test_order_process";
		
		File file = new File(filename);
		int index=0;
		while(file.exists()) {
			file = new File(filename.concat("_0").concat(String.valueOf(++index)).concat(".xml"));
		}
			
		try {
			FileUtils.write(file, optOrderProcessData(), CharacterSet.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String optEmailOpInData() {
		String data = "<TRACK>\r\n" + 
				"	<USER>\r\n" + 
				"		<EMAIL>cgordon@mail.com</EMAIL>\r\n" + 
				"		<SMSOPTIN>Y</SMSOPTIN>\r\n" + 
				"		<PHONE>908-213-1234</PHONE>\r\n" + 
				"		<SOURCE>T</SOURCE>\r\n" + 
				"		<FIRSTNAME>Nanny</FIRSTNAME>\r\n" + 
				"		<LASTNAME>Browser</LASTNAME>		\r\n" + 
				"	</USER>\r\n" + 
				"	<USER>\r\n" + 
				"		<EMAIL>cgordon1@mail.com</EMAIL>\r\n" + 
				"		<SMSOPTIN>Y</SMSOPTIN>\r\n" + 
				"		<PHONE>908-213-1231</PHONE>\r\n" + 
				"		<SOURCE>M</SOURCE>\r\n" + 
				"		<FIRSTNAME>Kenny</FIRSTNAME>\r\n" + 
				"		<LASTNAME>Edmonds</LASTNAME>	\r\n" + 
				"	</USER>\r\n" + 
				"	<USER>\r\n" + 
				"		<EMAIL>cgordon2@mail.com</EMAIL>\r\n" + 
				"		<SMSOPTIN>Y</SMSOPTIN>\r\n" + 
				"		<PHONE>908-213-1232</PHONE>\r\n" + 
				"		<SOURCE>M</SOURCE>\r\n" + 
				"		<FIRSTNAME>Sarah</FIRSTNAME>\r\n" + 
				"		<LASTNAME>Parker</LASTNAME>	\r\n" + 
				"	</USER>\r\n" + 
				"</TRACK>";

		return data;
	}

	public static String optOrderProcessData() {
		String data = "<ORDER>\r\n" + 
				"	<ORDER_NUM>W00995422264837</ORDER_NUM>\r\n" + 
				"	<ORDER_DATE>08-30-2017</ORDER_DATE>\r\n" + 
				"	<CPY_CODE>081</CPY_CODE><!--DEVICE TYPE -->\r\n" + 
				"	<EXTERNAL_USER_ID>awjkhbvfawlvhsldvhavldv</EXTERNAL_USER_ID>\r\n" + 
				"	<CUST_EMAIL>casmong@yahoo.com</CUST_EMAIL>\r\n" + 
				"	<SMSOPTIN>Y</SMSOPTIN>\r\n" + 
				"	<EMAIL_TYPE>DELIVERY_CONFIRM</EMAIL_TYPE><!--ORDER_CONFIRM ,SHIPPING_STATUS, DELIVERY_CONFIRM -->\r\n" + 
				"	<RECIPIENT>\r\n" + 
				"		<REC_FNAME>Test</REC_FNAME>\r\n" + 
				"		<REC_LNAME>Test</REC_LNAME>\r\n" + 
				"		<REC_ADD1>175 Westbury Ave</REC_ADD1>\r\n" + 
				"		<REC_ADD2>Suite 250</REC_ADD2>\r\n" + 
				"		<REC_CITY>Carle Place</REC_CITY>\r\n" + 
				"		<REC_STATE>NY</REC_STATE>\r\n" + 
				"		<REC_ZIP>11514</REC_ZIP>\r\n" + 
				"		<REC_COUNTRY>USA</REC_COUNTRY>\r\n" + 
				"		<REC_ATTN_TEXT/>\r\n" + 
				"		<ITEM>\r\n" + 
				"					<PRODUCT_NAME>Congrats Sausage &amp; Cheese Basket Orchard Congratulations</PRODUCT_NAME>\r\n" + 
				"					<PRODUCT_CODE>141014</PRODUCT_CODE>\r\n" + 
				"					<PRODUCT_TYPE>GPT</PRODUCT_TYPE>\r\n" + 
				"					<ALIAS_NAME>Meat and Cheese Hamper-CONGRTULATE</ALIAS_NAME>\r\n" + 
				"					<DELIVERY_DATE>04/05/2017</DELIVERY_DATE>\r\n" + 
				"					<FLEX_DATE/>\r\n" + 
				"					<FLEX_TEXT/>\r\n" + 
				"					<QUANTITY>1</QUANTITY>\r\n" + 
				"					<PRICE>49.99</PRICE>\r\n" + 
				"					<SHIPPING_CHARGE>49.99</SHIPPING_CHARGE>\r\n" + 
				"					<TAX/>\r\n" + 
				"					<CARD_MESSAGE>No Card Message</CARD_MESSAGE>\r\n" + 
				"					<ITEM_BRAND>1009</ITEM_BRAND>\r\n" + 
				"					<ATTMPT_DATE/>\r\n" + 
				"					<DEL_ON/>\r\n" + 
				"					<SHIPPER_TYPE/>\r\n" + 
				"					<TRK_NUM/>\r\n" + 
				"					<PRODUCT_IMAGE_CAT>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014c.jpg</PRODUCT_IMAGE_CAT>\r\n" + 
				"					<PRODUCT_IMAGE_TMN>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014t.jpg</PRODUCT_IMAGE_TMN>\r\n" + 
				"		</ITEM>\r\n" + 
				"		<ITEM>\r\n" + 
				"					<PRODUCT_NAME>Congrats Sausage &amp; Cheese Basket Orchard Congratszzzzz</PRODUCT_NAME>\r\n" + 
				"					<PRODUCT_CODE>141014</PRODUCT_CODE>\r\n" + 
				"					<PRODUCT_TYPE>GPT</PRODUCT_TYPE>\r\n" + 
				"					<ALIAS_NAME>Meat and Cheese Hamper-CONGRTZZZZZ</ALIAS_NAME>\r\n" + 
				"					<DELIVERY_DATE>04/05/2017</DELIVERY_DATE>\r\n" + 
				"					<FLEX_DATE/>\r\n" + 
				"					<FLEX_TEXT/>\r\n" + 
				"					<QUANTITY>1</QUANTITY>\r\n" + 
				"					<PRICE>49.99</PRICE>\r\n" + 
				"					<SHIPPING_CHARGE>49.99</SHIPPING_CHARGE>\r\n" + 
				"					<TAX/>\r\n" + 
				"					<CARD_MESSAGE>No Card Message</CARD_MESSAGE>\r\n" + 
				"					<ITEM_BRAND>1009</ITEM_BRAND>\r\n" + 
				"					<ATTMPT_DATE/>\r\n" + 
				"					<DEL_ON/>\r\n" + 
				"					<SHIPPER_TYPE/>\r\n" + 
				"					<TRK_NUM/>\r\n" + 
				"					<PRODUCT_IMAGE_CAT>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014c.jpg</PRODUCT_IMAGE_CAT>\r\n" + 
				"					<PRODUCT_IMAGE_TMN>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014t.jpg</PRODUCT_IMAGE_TMN>\r\n" + 
				"		</ITEM>\r\n" + 
				"	</RECIPIENT>\r\n" + 
				"	<RECIPIENT>\r\n" + 
				"		<REC_FNAME>Test Recepti</REC_FNAME>\r\n" + 
				"		<REC_LNAME>Test Recepti</REC_LNAME>\r\n" + 
				"		<REC_ADD1>175 Westbury Ave</REC_ADD1>\r\n" + 
				"		<REC_ADD2>Suite 250</REC_ADD2>\r\n" + 
				"		<REC_CITY>Carle Place</REC_CITY>\r\n" + 
				"		<REC_STATE>NY</REC_STATE>\r\n" + 
				"		<REC_ZIP>11514</REC_ZIP>\r\n" + 
				"		<REC_COUNTRY>USA</REC_COUNTRY>\r\n" + 
				"		<REC_ATTN_TEXT/>\r\n" + 
				"		<ITEM>\r\n" + 
				"					<PRODUCT_NAME>Congrats Sausage &amp; Cheese Basket Orchard Congratulations ZZ</PRODUCT_NAME>\r\n" + 
				"					<PRODUCT_CODE>141014</PRODUCT_CODE>\r\n" + 
				"					<PRODUCT_TYPE>GPT</PRODUCT_TYPE>\r\n" + 
				"					<ALIAS_NAME>Meat and Cheese Hamper-CONGRTULATE ZZ</ALIAS_NAME>\r\n" + 
				"					<DELIVERY_DATE>04/05/2017</DELIVERY_DATE>\r\n" + 
				"					<FLEX_DATE/>\r\n" + 
				"					<FLEX_TEXT/>\r\n" + 
				"					<QUANTITY>1</QUANTITY>\r\n" + 
				"					<PRICE>49.99</PRICE>\r\n" + 
				"					<SHIPPING_CHARGE>49.99</SHIPPING_CHARGE>\r\n" + 
				"					<TAX/>\r\n" + 
				"					<CARD_MESSAGE>No Card Message</CARD_MESSAGE>\r\n" + 
				"					<ITEM_BRAND>1009</ITEM_BRAND>\r\n" + 
				"					<ATTMPT_DATE/>\r\n" + 
				"					<DEL_ON/>\r\n" + 
				"					<SHIPPER_TYPE/>\r\n" + 
				"					<TRK_NUM/>\r\n" + 
				"					<PRODUCT_IMAGE_CAT>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014c.jpg</PRODUCT_IMAGE_CAT>\r\n" + 
				"					<PRODUCT_IMAGE_TMN>http://cdn1.1800baskets-uat.net/wcsstore/Baskets/images/catalog/141014t.jpg</PRODUCT_IMAGE_TMN>\r\n" + 
				"		</ITEM>\r\n" + 
				"	</RECIPIENT>\r\n" + 
				"</ORDER>";

		return data;
	}
	
}
