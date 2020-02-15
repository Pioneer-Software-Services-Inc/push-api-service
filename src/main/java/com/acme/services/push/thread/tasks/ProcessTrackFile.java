/**
 * 
 */
package com.acme.services.push.thread.tasks;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.constants.FeedType;
import com.acme.services.push.exception.Handler;
import com.acme.services.push.exception.ServiceException;
import static com.acme.services.push.facade.ParserFacade.*;
import com.acme.services.push.file.DataTransferObject;
import static com.acme.services.push.logger.Status.*;
import com.acme.services.push.model.beans.activity.Activity;
import com.acme.services.push.model.beans.activity.NullActivity;
import com.acme.services.push.model.beans.activity.UserTrackCreate;
import com.acme.services.push.service.Producer;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 10/13/2017
 * @version 1.0
 *
 * ProcessFile task implementation to be included ion the <code>WorkerThread</code> which gets 
 * executed by the Executer Service java 
 * 
 */
public class ProcessTrackFile extends AbstractProcessFile{

	private static transient final Logger logger = LoggerFactory.getLogger(ProcessTrackFile.class);

	/**
	 * @param file
	 */
	public ProcessTrackFile(File file) {
		super(file);
	}

	/** default no-arg constructor */
	public ProcessTrackFile() {

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
					trace(String.format("parse xml input file FAILED : %s", file.getName()));
					return Boolean.FALSE;
				}

				logger.debug(" xml validation successful: {}", file);
				transferObjectList = getDocumentParser(FeedType.TRACK).parseXml(file, Constants.LABEL_TRACK_TYPE);
			}else{
				logger.debug(" input file does not exist: {}", file);
				return Boolean.FALSE;
			}
		}catch(IOException | SAXException | ParserConfigurationException | InstantiationException | IllegalAccessException | ServiceException e){
			logger.error(" processing exception encountered processing file:, {}", e);
			trace(String.format("parse xml input file (%s) FAILED w/ exception: %s", file.getName(), e));
			return Boolean.FALSE;			
		}

		String action = Constants._BLANK;

		transferObjectList.stream().forEach( e -> {
			
			logger.debug("iterating over track xmlfile data");

			e.stream().forEach(s -> {

				logger.debug(" build and populate activity object");
				Activity activity = Handler.unchecked(()-> buildActivity(action, s)).get();

				logger.info(" calculated activity object: {}", activity.getPropertyKeyRef());
				if(!(activity instanceof NullActivity)) {

					logger.debug(" producer calling produce method");
					Producer.produce(buildRequest(activity));
				}
			});
		});
		
		trace(String.format("Total: %s records processed",transferObjectList!=null? transferObjectList.size() : 0));
		logger.info("\n");
		logger.info(" process file elapse time: {} (milliseconds) for input file: {}", 
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS), file.getName());
		logger.debug("process individual file ended");

		return Boolean.TRUE;
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
	public Activity buildActivity(@NotNull String action, @NotNull DataTransferObject transferObject) throws ParseException, IllegalAccessException, InstantiationException{

		logger.debug("building the Activity object");
		if(!Optional.of(action).isPresent()) return new NullActivity();

		logger.debug("instantiate activity");
		UserTrackCreate activity = new UserTrackCreate();
		activity.setAppGroupId(transferObject.getFields().get(Constants.LABEL_APP_GROUP_ID));
		activity.setAttributes(transferObject.getAttributes());
		
		return activity;
	}
}