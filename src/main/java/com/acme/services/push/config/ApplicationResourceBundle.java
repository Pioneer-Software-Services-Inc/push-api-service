/**
 * 
 */
package com.acme.services.push.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.PropertyResourceBundle;

import javax.validation.constraints.NotNull;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.exception.Handler;
import static com.acme.services.push.file.FileUtil.*;

/**
 * @author cgordon
 * @created 08/02/2017
 * @version 1.0
 *
 * Singleton Pattern Enum implementation for property file access.
 */
public enum ApplicationResourceBundle {
	
    INSTANCE; /** Enum Singleton design pattern */
    
    private static PropertyResourceBundle props = null;

    public final void configure(@NotNull String path){
    	clearCache();
    	props = Resource.bundle(path);
    }
    
    public final void clearCache(){
    	PropertyResourceBundle.clearCache();
    }
    
    public final String getProperty(String key){
		
    	Optional.of(props).orElseGet(Handler.unchecked(() -> Resource.bundle(getCurrentDirectory().concat(File.separator)
    			.concat("resources").concat(File.separator).concat(Constants.PROPERTIES_FILE_NAME))));
    	
    	if(!Optional.of(props).isPresent()) return Constants._BLANK;   

    	return props.getString(key);
    }

    public static ApplicationResourceBundle instance(){
		
    	return INSTANCE;
    }

    public final List<String> getPropertyList(String key){
		
    	Optional.of(props).orElseGet(Handler.unchecked(() -> Resource.bundle(getCurrentDirectory().concat(File.separator)
    			.concat("resources").concat(File.separator).concat(Constants.PROPERTIES_FILE_NAME))));
    	
    	if(!Optional.of(props).isPresent()) return new ArrayList<String>();   
    	
		return Arrays.asList((props.getString(key)==null ? Constants._BLANK : props.getString(key)).split("\\s*,\\s*"));
	}
	

	
}