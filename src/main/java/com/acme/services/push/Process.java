/**
 * 
 */
package com.acme.services.push;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;
import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.Handler;
import com.acme.services.push.exception.ServiceException;
import com.acme.services.push.facade.FunctionFacade;

import static com.acme.services.push.logger.Status.*;

import static com.acme.services.push.facade.FunctionFacade.*;
import static com.acme.services.push.file.FileUtil.*;
import com.acme.services.push.thread.WorkerThreadPool;

/**
 * @author cgordon
 * @created 08/02/2017 
 * @version 1.0
 * 
 *  <p>
 *  
 *  This Java class contains the main method for running the push push web service job. It accepts one [optional] parameter that specifies 
 *  the location of the configuration properties file.
 *  If the configuration file is absent then the application defaults to a hard coded preset location value.
 * 
 *  <p>
 *  
 *  Details about the implementation is discussed below so that modification to the code is simplified.
 *  The implementation was designed and complied using java 1.8.
 *  <p>
 */
public class Process {

	private static transient final Logger logger = LoggerFactory.getLogger(Process.class);
	private final int ONE = 1;

	/**
	 * Main method that is executed in order for the job to run.
	 * 
	 * @param String <code>String</code> literal to be validated.
	 * @throws <code>IOException</code>
	 */	
	public static void main(String[] args) throws IOException{

		if(args.length < 1){
			logger.error("push parameter underflow. required: location of config.properties");			
			System.exit(Constants.EXCEPTION_CODE_ARG_UNDERFLOW);
		}
		
		System.out.printf("command line argument(s): %s\n", printList(Arrays.asList(args)));
		
		/** Tooling: resource bundle initialize*/
		initConfig(args[0]);
		clearConfigCache();
		
		/** Tooling:  log4j initialize using setting in resource bundle configuration file */
		String log4jFolder = getResourceProperty(LOG_FOLDER);
		System.setProperty("log.dir", log4jFolder);
		String log4jConfigFile = getResourceProperty(LOG_CONFIG);
		System.out.printf(" log4J config property \nfile: %s\n", log4jConfigFile);
		
	    if (log4jConfigFile != null && new File(log4jConfigFile).canRead()) {
	        LogManager.resetConfiguration();
	        if (log4jConfigFile.endsWith(Constants.PROPERTIES_EXT)) {
	            PropertyConfigurator.configure(log4jConfigFile); // .properties file
	        }
	        else {
	            DOMConfigurator.configure(log4jConfigFile); // .xml file
	        }
	    }		
		
		/** Programming business logic section starts here */	    
		logger.info("push push api service started:, {}", Arrays.asList(args));
		trace(String.format("push push api service started:, %s", FunctionFacade.printList(Arrays.asList(args))));

		final long start = System.nanoTime();
		int num_files = 0;
		Process p = new Process();
		try {
			p.initialize();
		} catch (ServiceException e) {
			logger.error("Push service initialise failure. message: {}", e);
			trace(String.format("Push service initialise failure. message: %s", e));
		}

		num_files = Handler.unchecked(() -> p.processDirectory()).get();
		
		logger.info("Processed {} files(s). Total application elapse time: {} (milliseconds)", num_files,
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS) );
		logger.info("push push api service ended.");
		trace(String.format("Processed %s files(s). Total application elapse time: %s (milliseconds)", num_files,
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS) ));
	}
	
	/**
	 * This method scans the configured directory for any available fileUtil for processing. 
	 * Any fileUtil found are placed in a (FIFO) queue for subsequent processing.
	 * @throws <code>InterruptedException</code> 
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 * @throws <code>ServiceException</code> 
	 */	
	public int processDirectory() throws InterruptedException, InstantiationException, IllegalAccessException, ServiceException{
		logger.info("process directory started");

		int num_files = Stream.of(ONE).collect(Collectors.summingInt(y -> {
			int count = Handler.unchecked(()-> {
				final Queue<File> queue = getPendingFiles();
				final int size = queue!=null? queue.size() : Constants.ZERO;
				logger.debug("processing queue. size: {}\n", size );
				processPendingFiles(queue); 
				return size;
			}).get();

			return count;
		}));		
		
		logger.info("process directory complete. fileUtil processed: {}", num_files);		
		return num_files;
	}

	/**
	 * Initializes the process file.
	 * 
	 * purges the usage folders (processed/failed) folders to avoid running out of available nodes (linux) issue
	 * when there are too many fileUtil on these folders. 
	 * @throws ServiceException 
	 * @throws IOException 
	 * 
	 */
	private void initialize() throws IOException, ServiceException{
		logger.debug(" service init method called. ");
		
		/** ensure that the processed and failed folders don't have too many fileUtil*/
		purgeTooManyFiles(getResourceProperty(PROCESSED_FOLDER), Constants.PROCESSED_ARCHIVE);
		purgeTooManyFiles(getResourceProperty(FAILED_FOLDER), Constants.FAILED_ARCHIVE);
		initialiseFiles(getResourceProperty(PENDING_FOLDER), getResourceProperty(PROCESSING_FOLDER));
	}
	
	/**
	 * This method is tasked with processing the pending fileUtil that have been placed on the queue.
	 * 
	 * @param Queue <code>Queue<File></code> of pending fileUtil to be processed
	 * @throws <code>InterruptedException</code> 
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 * @throws <code>ServiceException</code> 
	 * @throws <code>IOException</code>
	 */	
	private void processPendingFiles(@NotNull Queue<File> files) throws IOException, InterruptedException, InstantiationException, IllegalAccessException, ServiceException{
		logger.debug("process pending fileUtil");

		if(!Optional.of(files).isPresent()) return;
		
		WorkerThreadPool pool = new WorkerThreadPool();
		pool.processFileQueue(files);

		logger.debug("process pending fileUtil ended");		
	}
}