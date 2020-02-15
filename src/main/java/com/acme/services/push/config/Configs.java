/**
 * 
 */
package com.acme.services.push.config;

import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * @author cgordon
 * @created 10/18/2017
 * @version 1.0
 *
 * wrapper class for Application resource bundle singleton implementation
 */
public class Configs {
   
    public static final void configure(@NotNull String path){
    	ApplicationResourceBundle.instance().configure(path);
    }
    
    public static final void clearCache(){
    	ApplicationResourceBundle.instance().clearCache();
    }
    
    public static final String getProperty(String key){
		
    	return ApplicationResourceBundle.instance().getProperty(key);
    }

    public static final List<String> getPropertyList(String key){
		
    	return ApplicationResourceBundle.instance().getPropertyList(key);
	}
	
}