/**
 * 
 */
package com.acme.services.push.thread;

import java.util.concurrent.ThreadPoolExecutor;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.acme.services.push.facade.StaticFacade.*;
import static com.acme.services.push.config.PropertyKeys.*;

/**
 * @author cgordon
 * @created 08/17/2017
 * @version 1.0
 * 
 * Thread monitor service that provides status of the current threads in execution.
 *
 */
public class CustomThreadMonitor implements Runnable{

	private static transient final Logger logger = LoggerFactory.getLogger(CustomThreadMonitor.class);

	@NotNull private ThreadPoolExecutor executor;
	@NotNull private int seconds;
	private boolean run=true;
	private final int INTERVAL = Integer.parseInt(getResourceProperty(SERVICE_THREADS_MONITOR_INTERVAL));
	
	/**
	 * <code>CustomThreadMonitor</code> Constructor 
	 * 
	 * @param <code>ThreadPoolExecutor</code> type executor class 
	 * @param delay int primitive (seconds)
	 */
	public CustomThreadMonitor(@NotNull ThreadPoolExecutor executor, int delay)
	{
		this.executor = executor;
		this.seconds=delay;
	}
	
	public void shutdown(){
		this.run=false;
	}

	public void run()
	{
		while(run){
			logger.debug(
					String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
							this.executor.getPoolSize(),
							this.executor.getCorePoolSize(),
							this.executor.getActiveCount(),
							this.executor.getCompletedTaskCount(),
							this.executor.getTaskCount(),
							this.executor.isShutdown(),
							this.executor.isTerminated()));
			try {
				Thread.sleep(seconds*INTERVAL);
			} catch (InterruptedException e) {
				logger.error("exception thrown by thread monitor: {}", e);
			}
		}
	}
}
