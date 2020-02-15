/**
 * 
 */
package com.acme.services.push.thread;

/**
 * @author cgordon
 * @created 08/17/2017
 * @version 1.0
 * 
 * handler implementation class for thread rejected event
 */
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
	
	private static transient final Logger logger = LoggerFactory.getLogger(RejectedExecutionHandler.class);
	
    public void rejectedExecution(@NotNull Runnable r, ThreadPoolExecutor executor) {
        logger.error(" error starting thread. Runnable: %s | was rejected \n", r.toString());
    }
}
