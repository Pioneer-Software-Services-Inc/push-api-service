/**
 * 
 */
package com.acme.services.push.thread.tasks;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;

import com.acme.services.push.config.PropertyKeys;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.constants.FeedType;
import com.acme.services.push.exception.ServiceException;
import static com.acme.services.push.facade.FunctionFacade.*;
import static com.acme.services.push.facade.ParserFacade.*;
import com.acme.services.push.file.DataTransferObject;
import static com.acme.services.push.logger.Status.*;
import com.acme.services.push.model.beans.Audience;
import com.acme.services.push.model.beans.Recipient;
import com.acme.services.push.model.beans.Schedule;
import com.acme.services.push.model.beans.UserAlias;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.MessageScheduleCreate;
import com.acme.services.push.model.beans.activity.NullActivity;
import com.acme.services.push.model.beans.activity.ScheduleCreate;
import com.acme.services.push.model.beans.activity.TriggerSend;
import com.acme.services.push.model.beans.push.Android;
import com.acme.services.push.model.beans.push.Apple;
import com.acme.services.push.model.beans.push.Email;
import com.acme.services.push.model.beans.push.NullPushObject;
import com.acme.services.push.model.beans.push.Push;
import com.acme.services.push.model.beans.push.Web;
import com.acme.services.push.model.beans.push.Windows8;
import com.acme.services.push.service.Producer;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 08/016/2017
 * @version 1.0
 *
 * ProcessFile task implementation to be included ion the <code>WorkerThread</code> which gets 
 * executed by the Executer Service java 
 * 
 */
public class ProcessOrderFile extends AbstractProcessFile{

	private static transient final Logger logger = LoggerFactory.getLogger(ProcessOrderFile.class);

	/** default no-arg constructor */
	public ProcessOrderFile() {

	}
	
	/**
	 * @param file
	 */
	public ProcessOrderFile(File file) {
		super(file);
	}
	
	/**
	 * This method is tasked with processing the pending file that has been removed from the front of the queue.
	 * 
	 * The file is first locked by renaming it to a [filename.xml].lck to prevent any issues associated with multiple 
	 * instance()s of the job running or any type of overlap etc.
	 * 
	 * The file is [xml] validated and then parsed to extract and organize its contents. 
	 * 
	 * Upon completion the file is moved to the processed folder and then the lock removed via renaming.
	 * A time stamp is appended to the file name to prevent file naming collision problems. 
	 * 
	 * 
	 * @param <File> pending file to be processed
	 * @param enum <code>FeedType</code> specifying the type of feed to be processed  
	 */	
	@Override
	public Boolean execute(){
		logger.debug("process individual file");
		final long start = System.nanoTime();
		
		List<Set<DataTransferObject>> transferObjectList = new ArrayList<Set<DataTransferObject>>();
		logger.debug(" input file is being processed: {}", file);

		try{
			if(file.exists()){

				if(!validateXmlFile(file)){
					logger.debug("Parse xml input file FAILED : {}", file);
					trace(String.format("Parse xml input file FAILED : %s", file.getName()));
					return Boolean.FALSE;
				}
				logger.debug(" xml validation successful: {}", file);
				transferObjectList = getDocumentParser(FeedType.ORDER).parseXml(file, getResourceProperty(PropertyKeys.DATA_MAP_ROOT_KEY));
			}else{
				logger.debug(" input file does not exist: {}", file);
				return Boolean.FALSE;
			}
		}catch(IOException | SAXException | ParserConfigurationException | InstantiationException | IllegalAccessException | ServiceException e){
			logger.error(" processing exception encountered processing file:, {}", e);
			trace(String.format(" processing exception encountered processing file:, %s", e));
			return Boolean.FALSE;			
		}

		logger.debug("PROCESS data: {}, size: {}", transferObjectList, transferObjectList.size());
		
		transferObjectList.stream().forEach( e -> {
			
			logger.debug("iterating over order xmlfile data");
			e.parallelStream().forEach( s -> {
				
				Hashtable<String, String> fields = s.getFields();
				
				String action = fields.get(Constants.LABEL_ACTION);
				String device = fields.get(Constants.LABEL_DEVICE);			
				String campaign = fields.get(Constants.LABEL_CAMPAIGN);

				logger.info("action, {}", action);
	
				try {
					logger.debug("populate Push object - device, {}", device);					
					Push pushObject = buildPushObject(device, campaign);
					
					Hashtable<String, Push> messages = new Hashtable<String, Push>();
					logger.info("json root name: {} \n", getJsonRootValue(pushObject));
					messages.put(getJsonRootValue(pushObject), pushObject);
					
					logger.debug(" build and populate activity object");
					Activity activity = buildActivity(action, s, messages);
					
					logger.info(" calculated activity object: {}", activity);
					if(!(activity instanceof NullActivity)) {
						
						logger.debug(" producer calling produce method");
						Producer.produce(buildRequest(activity));
					}
				} catch (IllegalAccessException | InstantiationException | ParseException  e1) {
					logger.error(" failed to create instance():, {}", e1);
				}
			});
		});

		trace(String.format("Total: %s records processed", transferObjectList!=null? transferObjectList.size() : 0));
		logger.info(" \nprocess file elapse time: {} (milliseconds) for input file: {}", 
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS), file.getAbsolutePath());
		logger.debug("process individual file ended");
		return Boolean.TRUE;
	}

	/**
	 * Convenience method to organize into smaller method with more specific purpose
	 * 
	 * @param push object <code>Push</code> object to be populated
	 * @param device description
	 * @param campaign id <code>String</code> value
	 * @return <code>Push</code>
	 * @throws <code>ParseException</code>
	 * @throws <code>IllegalAccessException</code> 
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalArgumentException</code>
	 */
	public Push buildPushObject(@NotNull String device, @NotNull String campaign) throws ParseException, IllegalArgumentException, InstantiationException, IllegalAccessException{
		
		logger.debug("build and populating the Push object");
		if(!Optional.of(device).isPresent()) return new NullPushObject();
		
		logger.debug(" calculate and build Push object");
		Push pushObject = buildPushObjectInstance(device);
		
		final String company_id = getResourceProperty((COMPANY_IDENTIFIER_PREFIX.concat(device)));
		final String campaign_msg_variation_id = getResourceProperty(String.format(CAMPAIGN_DEFINE_MSG_VARIATION_ID, campaign.toUpperCase(),device));
		
		if(pushObject instanceof Apple) {
			((Apple) pushObject).setAlert("The quick brown fox jumps over the lazy dog.");
			((Apple) pushObject).setMessage_variation_id(campaign_msg_variation_id);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, Integer.parseInt(getResourceProperty(MESSAGE_EXPIRE)));

			((Apple) pushObject).setExpiry(formatDateAsIso8601(calendar.getTime()));
		}
		if(pushObject instanceof Android) {
			((Android) pushObject).setAlert("The quick brown fox jumps over the lazy dog.");
			((Android) pushObject).setTitle("Default subject line - android");
			((Android) pushObject).setMessage_variation_id(campaign_msg_variation_id);
		}
		if(pushObject instanceof Email) {
			((Email) pushObject).setMessage_variation_id(campaign_msg_variation_id);
			((Email) pushObject).setBody("The quick brown fox jumps over the lazy dog yada yada");
			((Email) pushObject).setSubject("Default subject line - email");
			((Email) pushObject).setApp_id(company_id);
		}
		if(pushObject instanceof Web) {
			((Web) pushObject).setAlert("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do yada yada");
			((Web) pushObject).setMessage_variation_id(campaign_msg_variation_id);
			((Web) pushObject).setTitle("Default subject line for web push object message");
		}
		if(pushObject instanceof Windows8) {
			((Windows8) pushObject).setToast_title("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do yada yada");
			((Windows8) pushObject).setMessage_variation_id(campaign_msg_variation_id);
			((Windows8) pushObject).setToast_title("Default subject line for windows 8 push message");
			((Windows8) pushObject).setToast_content("Sed ut perspiciatis, unde omnis iste natus error sit voluptatem " +
			"aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt,");
		}

		logger.debug("build and populating the Push object complete");
		return pushObject;
	}
	
	/**
	 * Convenience method to organize into smaller method with more specific purpose. Populates <code>Activity</code> object
	 * 
	 * @param action <code>String</code> which is used to determine the type of Activity instance to build
	 * @param transferObject <code>DataTransferObject</code>
	 * @param messages <code>Hashtable<String, Push></code> complex type with list of <code>Push</code> objects to be sent to Push 
	 * @return <code>Activity</code>
	 * @throws <code>ParseException</code>
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 */
	public Activity buildActivity(@NotNull String action, DataTransferObject transferObject, Hashtable<String, Push> messages) throws ParseException, IllegalAccessException, InstantiationException{

		logger.debug("building the Activity object");
		if(!Optional.of(action).isPresent()) return new NullActivity();
		
		final Class<?> klass = (Class<?>)getActivityClass(action);
		
		logger.debug("instantiate activity");
		Activity activity = (Activity)klass.newInstance();

		logger.debug("endpoint ref: {}", (activity.getPropertyKeyRef().concat(ENDPOINT)).toLowerCase());
		
		Hashtable<String, String> fields = transferObject.getFields();
		final String device = fields.get(Constants.LABEL_DEVICE);			
		final String campaign = fields.get(Constants.LABEL_CAMPAIGN);
		final String external_user_id = fields.get(Constants.LABEL_EXTERNAL_USER_ID);
		final String user_email = fields.get(Constants.LABEL_USER_EMAIL);
		
		final String appgroup_id = getResourceProperty((APPLICATION_GROUP_PREFIX.concat(device)));
		final String campaign_device_id = getResourceProperty(String.format(CAMPAIGN_DEFINE_DEVICE_ID, campaign.toUpperCase(), device));
		
		if(activity instanceof MessageScheduleCreate){

			List<String> external_user_ids = new ArrayList<String>();
			external_user_ids.add(external_user_id);

			((MessageScheduleCreate) activity).setApp_group_id(appgroup_id);
			((MessageScheduleCreate) activity).setCampaign_id(campaign_device_id);
			((MessageScheduleCreate) activity).setExternal_user_ids(external_user_ids);
			((MessageScheduleCreate) activity).setMessages(messages);
			((MessageScheduleCreate) activity).setAudience(new Audience());

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, Integer.parseInt(getResourceProperty(MESSAGE_SEND_DELAY)));

			Schedule schedule = new Schedule(formatDateAsIso8601(calendar.getTime()), true, false);
			((MessageScheduleCreate) activity).setSchedule(schedule);
		}

		if(activity instanceof ScheduleCreate){

			((ScheduleCreate) activity).setApp_group_id(appgroup_id);
			((ScheduleCreate) activity).setMessages(messages);
		}

		if(activity instanceof TriggerSend){

			Hashtable<String, String> trigger_properties = new Hashtable<String, String>();
			trigger_properties.putAll(fields);
			
			Recipient recipient = new Recipient();
			recipient.setTrigger_properties(transferObject.getTrig_props());
			recipient.setUser_alias(user_email==null||user_email.isEmpty()? new UserAlias() : new UserAlias(user_email));
			List<Recipient> recipients = new ArrayList<Recipient>();
			recipients.add(recipient);
			
			((TriggerSend) activity).setApp_group_id(appgroup_id);
			((TriggerSend) activity).setCampaign_id(campaign_device_id);
			((TriggerSend) activity).setTrigger_properties(trigger_properties);
			((TriggerSend) activity).setRecipients(recipients);
		}		
	
		return activity;
	}
}
