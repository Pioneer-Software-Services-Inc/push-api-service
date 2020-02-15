/**
 * 
 */
package com.acme.services.push.thread;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.logger.Status.trace;
import static com.acme.services.push.config.PropertyKeys.*;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.ServiceException;
import static com.acme.services.push.facade.ParserFacade.*;
import com.acme.services.push.network.CustomHttpAsyncClient;
import com.acme.services.push.response.Status;
import com.acme.services.push.thread.tasks.Task;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 08/17/2017
 * @version 1.0
 * 
 * Worker pool class for customized thread pool processing of Push file feed.
 */
public class WorkerThreadPool {

	private static transient final Logger logger = LoggerFactory.getLogger(WorkerThreadPool.class);
	private volatile StringBuffer message= new StringBuffer(Constants._BLANK);
	private volatile boolean success= false;

	/**
	 * This method is tasked with processing the pending fileUtil hat have been placed on the queue.
	 * 
	 * @param Queue <code>Queue<File></code> of pending fileUtil to be processed
	 * @throws <code>IOException</code>
	 * @throws <code>InterruptedException</code> 
	 * @throws <code>InstantiationException</code>
	 * @throws <code>IllegalAccessException</code>
	 * @throws <code>ServiceException</code> 
	 * @throws <code>SAXException</code> 
	 * @throws <code>ParserConfigurationException</code> 
	 * @throws <code>IOException</code>
	 */	
	public void processFileQueue(@NotNull Queue<File> files) throws IOException, InterruptedException, InstantiationException, IllegalAccessException, ServiceException{
		logger.debug("process pending fileUtil");

		Optional.of(files).orElseGet(() -> new LinkedList<File>());
		final long start = System.nanoTime();

		final int corePoolSize = Integer.parseInt(getResourceProperty(SERVICE_THREADS_MIN));
		final int maximumPoolSize = Integer.parseInt(getResourceProperty(SERVICE_THREADS_MAX));
		final long keepAliveTime = Integer.parseInt(getResourceProperty(SERVICE_THREADS_KEEP_ALIVE));
		final int delay = Integer.parseInt(getResourceProperty(SERVICE_THREADS_POOL_DELAY));
		final int poolSleep = Integer.parseInt(getResourceProperty(SERVICE_THREADS_POOL_SLEEP));
		final int monitorSleep = Integer.parseInt(getResourceProperty(SERVICE_THREADS_MONITOR_SLEEP));
		final boolean fair = true;

		CustomHttpAsyncClient.start();

		RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
		ThreadFactory threadPool = Executors.defaultThreadFactory();

		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>(fair), threadPool, rejectionHandler);		

		/** start the monitoring thread **/
		CustomThreadMonitor monitor = new CustomThreadMonitor(executor, delay);
		Thread monitorThread = new Thread(monitor);
		monitorThread.start();

		try {
			for(File file : files){
				StringBuffer buf = new StringBuffer("thread-process-");
				Task task = null; /** Inversion of Control (IoC) design pattern */
				
				synchronized(file){
					/** minimize the size of the critical section to avoid bottlenecks accessing shared resource (file)*/
					file = lockFile(file);
				}
	
				buf = buf.append(file);
				task = getDocumentProcesser(file);
				
				Callable<Status> worker = new WorkerThread(task, buf.toString());		
				message.append(String.valueOf(worker));
				logger.debug(" executing worker callable..\n");
	
				Future<?> future = executor.submit(worker);			
				if(future!=null){
					
					boolean hasError=false;
					try {
						
						logger.debug("The worker thread operation: returned: {}, and the [done] status is: {}\n", future.get(), future.isDone());
					} catch (InterruptedException | ExecutionException e) {		
						hasError=true;
						logger.error(" execution exception occured processing future response: exception: {} \n", 
								e.getMessage());
						message.append(String.format(" processing exception encountered processing file:, %s", e.getMessage()));
					}
					
					synchronized(file){
						
						file = unlockFile(file);
					}
	
					file = hasError? moveToFailed(file) : moveToProcessed(file);
					success = !hasError;
					logger.info(" file processed: {}\n", file);
					logger.info(" task completed: {} success: {}\n", String.valueOf(worker), success);
				}
			}
		}catch(Exception e) {
			
			success=false;
			logger.error(" general execution exception occured processing file. exception: {} \n",e); 
			message.append(String.format(" general execution exception occured processing file. exception: %s", e.getMessage()));
		}finally {
			
			trace(String.format(" process task completed: %s success:%s\n", message, success));
			/** shut down the executor pool */
			Thread.sleep(poolSleep);		
			executor.shutdown();
			/** shut down the monitor thread */
			Thread.sleep(monitorSleep);		
			monitor.shutdown();
			/** shut down the asynchronous http client */		
			CustomHttpAsyncClient.close();
		}

		logger.info("Total thread pool elapse time: {} (milliseconds)", 
				TimeUnit.MILLISECONDS.convert((System.nanoTime() - start), TimeUnit.NANOSECONDS) );
		logger.info("Finished all threads\n");
	}
	
	/**
	 * House keeping. clean up any stragglers that were not processed
	 */
	@Override
	public void finalize() {
		
		cleanupFiles(getResourceProperty(PROCESSING_FOLDER));
	}
	
}