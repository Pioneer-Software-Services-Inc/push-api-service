package com.acme.services.push.file.locator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.ServiceException;

/**
 * @author cgordon
 * @created 10/17/2017
 * @version 1.0
 * 
 * Custom implementation of the service locator design pattern which provides factories which create ResourceInterface implementations.
 * 
 * Used primarily for:
 * 	i) File Processor factory resources
 *  ii) File parser factory resources 
 * 
 */
public class ServiceLocator {
	   private static Cache cache;
	   private static transient final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

	   /**
	    * Static initializer loads Service locator Cache into memory when the JVM starts up.
	    * Improves speed and efficiency of cache-ing mechanism.  
	    */
	   static {
	      cache = new Cache();		
	   }

	   public static ResourceInterface getResource(String jndiName) throws ServiceException{

		  if (!Constants.factoryTypes.contains(jndiName)) throw new ServiceException(Constants.ERROR_SERVICE_MESSAGE);
		   
		  ResourceInterface service = cache.getResource(jndiName);

		  logger.debug("Service Located: jndi: {}, service: {} \n", jndiName, service.getClass() );
          return service;
	   }
	   
	}