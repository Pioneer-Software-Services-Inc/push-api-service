/**
 * 
 */
package com.acme.services.push.thread.tasks;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.Handler;
import com.acme.services.push.file.locator.ResourceInterface;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.NullActivity;
import com.acme.services.push.model.beans.push.NullPushObject;
import com.acme.services.push.model.beans.push.Push;
import com.acme.services.push.network.ContentType;
import com.acme.services.push.network.HttpMethod;
import com.acme.services.push.request.Request;

/**
 * @author cgordon
 * @created 08/016/2017
 * @version 1.0
 *
 * AbstractProcessFile parent task implementation to be included in the <code>WorkerThread</code> which gets 
 * executed by the Executer Service java 
 * 
 */
public abstract class AbstractProcessFile implements Task, ResourceInterface{

	private static transient final Logger logger = LoggerFactory.getLogger(AbstractProcessFile.class);
	protected volatile File file; 
	
	/** Default no-arg constructor for factory instantiation */
	public AbstractProcessFile() {}
	
	/**
	 *  Conctructor that accepts a <code>File</code> parameter
	 *  
	 * @param file
	 */
	public AbstractProcessFile(@NotNull File file){
		this.file=file; 
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
	public abstract Boolean execute();
	
	
	/**
	 * This method returns a type (Class) of the type of activity to be performed
	 * 
	 * these include: Trigger_send | Message_Create etc
	 * 
	 * @param <code>String</code> specify the type of activity
	 * @return <code>Class<? extends Activity></code> sub class of abstract Activity class type 
	 */	
	protected Class<? extends Activity> getActivityClass(@NotNull String action){

		logger.info("action getClasses, {}", action );
		
		if(!Optional.of(action).isPresent()) return NullActivity.class;  
		if(Constants.actionMap.containsKey(action)) return Constants.actionMap.get(action);

		return NullActivity.class; 
	}

	/**
	 * Convenience method to organize into smaller method with more specific purpose. 
	 * This method is tasked with building the <code>Request</code> Object
	 * 
	 * @param Activity <code>Activity</code> object with the data needed to construct <code>HttpRequestbase</code>
	 * Object 
	 * @returns <code>Request</code>
	 */	
	protected Request buildRequest(@NotNull Activity activity){
		
		logger.debug(" building request object: {}", activity );
		if(!Optional.of(activity).isPresent()) return new Request();
		
		String endpoint = getResourceProperty((activity.getPropertyKeyRef().concat(ENDPOINT)).toLowerCase());
		String method = getResourceProperty((activity.getPropertyKeyRef().concat(METHOD)).toLowerCase());
		String contenttype = getResourceProperty((activity.getPropertyKeyRef().concat(CONTENTTYPE)).toLowerCase());

		endpoint = endpoint!=null && endpoint.matches(Constants.REGEXP_VALID_URL)? endpoint : Constants.DEFAULT_ENDPOINT;
		method = method!=null && HttpMethod.validMethodTypes.contains(method)? method : HttpMethod.DEFAULT_METHOD;
		contenttype = contenttype!=null && ContentType.validContentTypes.contains(contenttype)? contenttype : ContentType.DEFAULT_CONTENTTYPE;

		return new Request(endpoint, method, contenttype, activity);
	}

	/**
	 * Convenience method to organize into smaller method with more specific purpose. build Push Object
	 * 
	 * @param device description
	 * @return <code>Push</code>
	 * @throws <code>IllegalArgumentException</code>
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 */
	protected Push buildPushObjectInstance(@NotNull String device) throws IllegalArgumentException, InstantiationException, IllegalAccessException{
		
		logger.debug("building the Push object");
		if(!Optional.of(device).isPresent()) return new NullPushObject();		
		
		/**
		 * The type of push operation is determined by what is available to the service.
		 * java reflection is used to instantiate the objects and populate them with the appropriate data 
		 */
		StringBuffer buf = new StringBuffer(Constants.STRING_PUSH_OBJECTS_PACKAGE);
		buf.append(String.format("%s%s", device.substring(0,1).toUpperCase(), device.substring(1)));

		Push pushObject = Handler.unchecked(() -> {

			Push push=new NullPushObject(); 
			Class<?> clazz = Class.forName(buf.toString());
			Constructor<?> ctor = clazz.getConstructor();
			push = (Push)ctor.newInstance(new Object[] { });
			return push;
		}).get();

		logger.debug("building the Push object complete");
		return pushObject;
	}
	
	/**
	 * Abstract method for subclasses, specifically for use in the Factory pattern for creating and setting the file member variable.
	 *   
	 * @param file the file to set
	 */
	public void setFile(File file) {
		
		this.file = file;
	}

}
