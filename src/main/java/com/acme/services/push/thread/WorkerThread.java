/**
 * 
 */
package com.acme.services.push.thread;

import java.util.concurrent.Callable;

import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.Handler;
import com.acme.services.push.response.Status;
import com.acme.services.push.thread.tasks.Task;

/**
 * @author cgordon
 * @created 08/016/2017
 * @version 1.0
 * 
 * Worker thread class to executed bt the ExecuterService standard Apache java library implementation 
 *
 */
public class WorkerThread implements Callable<Status> {

	private static transient final Logger logger = LoggerFactory.getLogger(WorkerThread.class);
	
	@NotNull private Task process;
	@NotNull private String delay = getResourceProperty(THREAD_DELAY);
	private String label;
	private Boolean success=false;

	public WorkerThread(Task process, String label){
		this.process=process;
		this.label=label;
	}

	@Override
	public Status call() throws Exception {
		logger.debug("START: thread label={}\n", label);
		
		success = Handler.unchecked(() -> processCommand()).get();
		delay();/** industry recommended best practice optional delay */
		
		logger.debug("END: thread label={}, success={}\n", label, success);
		return success? Status.SUCCESS : Status.FAIL;
	}

	private Boolean processCommand() {
		return process.execute();
	}

	@Override
	public String toString(){
		return this.label;
	}

	/**
	 * This is a industry recommended best practice:
	 * the method's purpose is to artificially place a configurable delay between batched 
	 * http GET/POST operations so as to prevent the REST recipient service from 
	 * misinterpreting multiple requests as a DDos attack.
	 * 
	 * @param Delay <code>Integer</code> between batched requests
	 */	
	private void delay(@NotNull int delay){

		try        
		{
			logger.debug("thread sleep:, {}", delay);
			Thread.sleep(delay);
		} 
		catch(InterruptedException ex) 
		{
			Thread.currentThread().interrupt();
		}		
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * This is a industry recommended best practice:
	 * the method's purpose is to artificially place a configurable delay between batched 
	 * http GET/POST operations so as to prevent the REST recipient service from 
	 * misinterpreting multiple requests as a DDos attack.
	 */	
	private void delay(){

		delay(StringUtils.isNotEmpty(delay)? Integer.parseInt(delay) : Constants.DEFAULT_THREAD_SLEEP);
	}

	@Override
	public void finalize(){

	}
}