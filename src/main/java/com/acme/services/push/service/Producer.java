/**
 * 
 */
package com.acme.services.push.service;

import java.util.Hashtable;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.services.push.command.Command;
import com.acme.services.push.command.CommandInvoker;
import com.acme.services.push.command.SendPostCommand;
import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import static com.acme.services.push.facade.FunctionFacade.*;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.TriggerSend;
import com.acme.services.push.model.beans.activity.UserTrackCreate;
import com.acme.services.push.request.Request;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * This class is a party of the Command Invoker design pattern that performs the  
 * produce operation.
 */
public class Producer{
	private static transient final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static enum TYPE {SEND_APP_PUSH_ONLY, SEND_PUSH_BOTH, SEND_NOTHING; }

	/**
	 * Produce method call of the Producer class type paradigm.
	 * 
	 * @param request <code>Request</code> object that functions as a Data Transfer Object
	 * for information to be passed to the Push REST endpoint
	 */
	public static final void produce(@NotNull final Request request)    {

		Optional.of(request).orElseGet(() -> new Request());
		TYPE instruction = TYPE.SEND_NOTHING;
		
		final String payload = getJsonModeltoString(request.getPayload()); 
		logger.debug("produce:payload, {}", payload);

		final int payloadMax = Integer.parseInt(getResourceProperty(PAYLOAD_THRESHHOLD));
		if (payload.length() > payloadMax){
			logger.error("Payload data provided exceeds maximum of {} and will not be sent.", payloadMax);
			return;
		}	

		Activity activity = (Activity)request.getPayload();
		logger.debug(" activity class type: {}", activity.getClass().getName());
		
		if( activity instanceof TriggerSend ){

			Hashtable<String, String> data = ((TriggerSend) activity).getTrigger_properties();
			String smsvalue = ((String)data.get(Constants.LABEL_SMS_OPTIN));
			
			instruction =  smsvalue.matches(Constants.REGEXP_TRUE)? TYPE.SEND_PUSH_BOTH : TYPE.SEND_APP_PUSH_ONLY;

			if(instruction == TYPE.SEND_APP_PUSH_ONLY) {
				/** set campaign to default configured campaign w/o sms */
				((TriggerSend) activity).setCampaign_id(getResourceProperty(SERVICE_DEFAULT_NO_SMS_CAMPAIGN));
				((TriggerSend) activity).setTrigger_properties(data);			
				request.setPayload(activity);
			}
		}
		if( activity instanceof UserTrackCreate ){
			instruction = TYPE.SEND_APP_PUSH_ONLY;
			request.setPayload(activity);
		}
		
		CommandInvoker invoker = new CommandInvoker();
		Command triggerDeliveryCmd = new SendPostCommand(request);

		switch(instruction){

			case SEND_NOTHING:
				
				logger.info(" nothing will be pushed. sms only push with sms feature disabled.\n");
	
				break;
		
			case SEND_APP_PUSH_ONLY:
				
				logger.info(" pushing to device w/o sms\n");
				//invoke cmd (triggered email delivery)
				invoker.setCommand(triggerDeliveryCmd);
				invoker.executeCommand();
	
				break;
				
			case SEND_PUSH_BOTH:
	
				logger.info(" general push to push (campaign)\n");
				//invoke cmd (triggered email delivery)
				invoker.setCommand(triggerDeliveryCmd);
				invoker.executeCommand();
	
				break;
				
			default:
				
				logger.info(" default case/scenario reached. no action. \n");
				
				break;
		}

		logger.info("execute cmd, request: {}", request.toString());
	}	
}
