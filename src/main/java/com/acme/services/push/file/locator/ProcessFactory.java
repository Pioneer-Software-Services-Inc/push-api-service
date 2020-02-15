package com.acme.services.push.file.locator;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Set;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.services.push.constants.Constants;
import com.acme.services.push.constants.FeedType;
import static com.acme.services.push.facade.ParserFacade.*;
import com.acme.services.push.thread.tasks.AbstractProcessFile;
import com.acme.services.push.thread.tasks.NullProcessFile;

/**
 * @author cgordon
 * @created 10/17/2017
 * @version 1.0
 * 
 * ResourceInterface implementation (Abstract Factory design Pattern) that produces resource for processing various (XML) file feeds. 
 * 
 */
public enum ProcessFactory implements ResourceInterface{

	/** modern method for implementing singleton design pattern */
	INSTANCE;
    
    /**Cache-ing implemented using SoftReference so if cache not needed or being used then data structure 
     * would be available for garbage collection by the JVM.
     * 
	 *  @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/ref/SoftReference.html">Java SE 1.8 Document SoftReference Class</a>
     *  
     */
    private @NotNull SoftReference<Set<Class<? extends AbstractProcessFile>>> cache;    
	public static final transient Logger logger = LoggerFactory.getLogger(ProcessFactory.class);

	/** do not allow no-arg object instantiation */
    private ProcessFactory(){
    	
    }

    public static ProcessFactory getInstance(){
    	return INSTANCE;
    }
    
	public AbstractProcessFile getFileProcessor(FeedType type, @NotNull File file) throws InstantiationException, IllegalAccessException{
	
		if(cache==null) cache = fetchCache();
		
		/** parser type descriptor may not match file name search for like name key */
		AbstractProcessFile process = cache.get().stream()
				.filter(t -> t.getName().toLowerCase().contains(String.valueOf(type).toLowerCase())).findFirst().orElse(NullProcessFile.class).newInstance();
		
		process.setFile(file);
		logger.debug("PROCESS:  Factory: type: {}, class: {} \n", type.toString(), process);
		return process;
	}	
	
	
	private @NotNull SoftReference<Set<Class<? extends AbstractProcessFile>>> fetchCache() {

		return new SoftReference<Set<Class<? extends AbstractProcessFile>>>((Set<Class<? extends AbstractProcessFile>>)getSetOfSubClasses(AbstractProcessFile.class, Constants.PROCESS_RESOURCE_PACKAGE));
	}
	
}