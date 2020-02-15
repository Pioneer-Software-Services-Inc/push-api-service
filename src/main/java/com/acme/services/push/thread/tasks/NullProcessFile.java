/**
 * 
 */
package com.acme.services.push.thread.tasks;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cgordon
 * @created 10/18/2017
 * @version 1.0
 *
 * Null Object pattern for alternative to null checking edge case code
 */
public class NullProcessFile extends AbstractProcessFile{

	private static transient final Logger logger = LoggerFactory.getLogger(NullProcessFile.class);
	
	/**
	 * @param file
	 */
	public NullProcessFile(File file) {
		super(file);
	}
	
	@Override
	public Boolean execute() {
		
		logger.debug(" xml loading and parse encountered errors and was not successful: {}", file);				
		return Boolean.TRUE;
	}

}
