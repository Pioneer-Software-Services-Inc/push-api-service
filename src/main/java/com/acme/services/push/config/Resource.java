/**
 * 
 */
package com.acme.services.push.config;

import java.io.FileInputStream;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Class purpose is to maintain property file resources and make available to application
 */
public class Resource {

	private static transient final Logger logger = LoggerFactory.getLogger(Resource.class);
	
	public static PropertyResourceBundle bundle(@NotNull String path) {

		PropertyResourceBundle props = null;
		
		try(FileInputStream fis = new FileInputStream(path)) {
			props = new PropertyResourceBundle(fis);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		if (props == null)
	        throw new MissingResourceException("Property file not found!", path, "");
		
		return props;
	}
	
}
