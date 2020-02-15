/**
 * 
 */
package com.acme.services.push.facade;

import java.util.List;

import com.acme.services.push.config.Configs;

/**
 * @author cgordon
 * @created 10/26/2017
 * @version 1.0
 *
 * This facade class contains a collection of static methods that organizes statically accessed methods 
 * in a way that their invocation is more uniform and the code is more readable.   
 * 
 */
public class StaticFacade {

	/**
	 * Wrapper method for Configs.configure(path);
	 * @param path
	 */
	public static void initConfig(String path) {
		
		Configs.configure(path);
	}
	
	/**
	 * Wrapper method for Configs.clearCache();
	 */
	public static void clearConfigCache() {
		
		Configs.clearCache();
	}

	/**
	 * Wrapper method for Configs.getProperty(key);
	 * @param key 
	 */
	public static String getResourceProperty(String key) {
		
		return Configs.getProperty(key);		
	}

	/**
	 * Wrapper method for Configs.getPropertyList(key);
	 * @param key
	 */
	public static List<String> getResourcePropertyList(String key) {
		
		return Configs.getPropertyList(key);		
	}

}
